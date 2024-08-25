package com.woahware.demo.service;

import com.woahware.demo.dto.HolidayDTO;
import com.woahware.demo.dto.RentalAgreement;
import java.util.List;
import org.springframework.stereotype.Service;
import com.woahware.demo.models.Tool;
import com.woahware.demo.models.ToolTypeCharge;
import com.woahware.demo.repository.ToolRepository;
import java.time.LocalDate;
import com.woahware.demo.repository.ToolChargeProjection;
import com.woahware.demo.utils.HolidayAdjustments;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


@Service
public class ToolServiceImpl implements ToolService {

    private static final Logger logger = Logger.getAnonymousLogger();

    private final ToolRepository toolRepository;
    private final HolidayService holidayService;

    @Value("${exception.INVALID_RENTAL_DAYS.message}")
    private String INVALID_RENTAL_DAYS;

    @Value("${exception.INVALID_DISCOUNT.message}")
    private String INVALID_DISCOUNT;

    @Value("${exception.INVALID_TOOL.message}")
    private String INVALID_TOOL;

    @Value("${exception.GENERATE_RENTAL_AGREEMENT_ERROR.message}")
    private String GENERATE_RENTAL_AGREEMENT_ERROR;

    @Autowired
    public ToolServiceImpl(ToolRepository toolRepository, HolidayService holidayService) {
        this.toolRepository = toolRepository;
        this.holidayService = holidayService;
    }

    @Override
    public List<Tool> getAll() {
        return (List<Tool>) toolRepository.findAll();
    }

    @Override
    public Tool getById(long id) {
        return toolRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(INVALID_TOOL));
    }

    @Override
    public ToolChargeProjection findByToolCode(String toolCode) {
        return toolRepository.getChargeInfoByToolCode(toolCode);
    }

    @Override
    public RentalAgreement generateRentalAgreement(String toolCode, LocalDate checkoutDate, int rentalDayCount, int discountPercent) throws RuntimeException {
        validateParams(rentalDayCount, discountPercent);    // try to validate some of the parameters first
        // get all associated tool charge information based on toolCode
        ToolChargeProjection toolCharges = toolRepository.getChargeInfoByToolCode(toolCode);
        // now query to get Holidays in range, given the rentral days and checkoutDate...
        List<HolidayDTO> holidayDTOs = holidayService.getAllHolidaysForCheckout(checkoutDate, rentalDayCount);
        // Check to make sure that the above data we get from the database is not null; otherwise if it is, then do something...
        checkNullData(toolCode, toolCharges, holidayDTOs);
        // calculate charge days based on business logic
        int chargeDays = calcChargeDays(toolCharges.getToolTypeCharge(), checkoutDate, rentalDayCount, holidayDTOs);
        // create rental agreement
        RentalAgreement rentalAgreement = new RentalAgreement.Builder()
                .setToolCode(toolCharges.getTool().getToolCode())
                .setToolType(toolCharges.getToolType().getName())
                .setToolBrand(toolCharges.getTool().getBrand().getName())
                .setRentalDays(rentalDayCount)
                .setCheckOutDate(checkoutDate)
                .setDailyRentalCharge(toolCharges.getToolTypeCharge().getDailyCharge())
                .setDiscountPercent(discountPercent)
                .setChargeDays(chargeDays)
                .build();
        // print fancy version of rental agreement to console and return the rentalAgreement object
        rentalAgreement.printDetailsWithReflection();
        return rentalAgreement;
    }
    
    
    // Helper methods

    // Checks to see if any of the data we recieve from the database regarding tools and holidays is null; if it is, then throw exception...
    // Need to get clarification of business rules for this case... 
    // -- do we want to return a potentially null rentalAgreement object or should we be throwing an exception (like below)?
    private void checkNullData(String toolCode, ToolChargeProjection toolCharges, List<HolidayDTO> holidayDTOs) {
        if (toolCharges == null) {       // if no tool data is found/ returned by our repo
            logger.log(Level.INFO, "Could not find Tool related data for tool code: {0}.", toolCode);
            throw new NoSuchElementException(GENERATE_RENTAL_AGREEMENT_ERROR);
        } else if (holidayDTOs == null) {       // if no tool data is found/ returned by our repo
            logger.log(Level.INFO, "Could not find Holiday related data.");
            throw new NoSuchElementException(GENERATE_RENTAL_AGREEMENT_ERROR);
        }

    }

    // validate some of the parameters  based on business logic
    private void validateParams(int rentalDayCount, int discountPercent) throws RuntimeException {
        if (rentalDayCount < 1) {
            logger.log(Level.INFO, INVALID_RENTAL_DAYS);
            throw new IllegalArgumentException(INVALID_RENTAL_DAYS);
        } else if (discountPercent < 0 || discountPercent > 100) {
            logger.log(Level.INFO, INVALID_DISCOUNT);
            throw new IllegalArgumentException(INVALID_DISCOUNT);
        }
    }

    // logic for charge days based on number of holidays and weekends...
    private int calcChargeDays(ToolTypeCharge toolTypeCharge, LocalDate checkoutDate, int rentalDayCount, List<HolidayDTO> holidayDTOs) {
        LocalDate endDate = checkoutDate.plusDays(rentalDayCount);
        int totalWeekDayCount = (int) HolidayAdjustments.calcWeekDays(checkoutDate, endDate);

        if (toolTypeCharge.getHolidayCharge() && toolTypeCharge.getWeekEndCharge()) {
            return rentalDayCount;
        }
        else if (!toolTypeCharge.getHolidayCharge() && toolTypeCharge.getWeekEndCharge()) {
            return rentalDayCount - holidayDTOs.size();
        }
        else if (toolTypeCharge.getHolidayCharge()) {           // at this point, this is also the case:  if (! toolTypeCharge.getWeekEndCharge())
            return totalWeekDayCount;
        }
        // at this point, this is the case: if(! toolTypeCharge.getHolidayCharge() && ! toolTypeCharge.getWeekEndCharge())
        return totalWeekDayCount - holidayService.getAllWeekdayHolidays(holidayDTOs).size();
    }

}
