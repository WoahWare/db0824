package com.woahware.demo.service;

import com.woahware.demo.dto.HolidayDTO;
import com.woahware.demo.models.Holiday;
import com.woahware.demo.repository.HolidayRepository;
import com.woahware.demo.utils.HolidayAdjustments;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository holidayRepository;
    private final ModelMapper modelMapper;
    
    @Value("${exception.INVALID_HOLIDAY.message}")
     private String INVALID_HOLIDAY;

    @Autowired
    public HolidayServiceImpl(HolidayRepository holidayRepository, ModelMapper modelMapper) {
        this.holidayRepository = holidayRepository;
        this.modelMapper = modelMapper;
    }
    
    @Override
    public Holiday getById(long id) {
        Holiday holiday = holidayRepository.findById(id).orElseThrow(() -> new EntityNotFoundException( INVALID_HOLIDAY));
        return holiday;
    }

    @Override
    public List<Holiday> getAll() {
        List<Holiday> holidays = (List<Holiday>) holidayRepository.findAll();
        return holidays;
    }

    // returns Holiday DTO version of a Holiday
    @Override
    public HolidayDTO getHolidayDTO(long id) {
        Holiday holiday = getById(id);
        return convertToDto(holiday);
    }
    
     // returns Holiday DTO version of a Holiday with associated realDate if given a checkoutDate
    @Override
    public HolidayDTO getHolidayDTO(long id, LocalDate checkoutDate) {
        Holiday holiday = getById(id);
        return convertToDto( holiday,  checkoutDate);
    }

    // returns all holidays as a list of HolidayDTO objects
    @Override
    public List<HolidayDTO> getAllHolidays() {
        List<Holiday> holidays = getAll();
        return holidays.stream().
                               map(this::convertToDto)
                              .collect(Collectors.toList());
    }
    
     // returns a list of HolidayDTO objects that only occur on weekends
    @Override
    public List<HolidayDTO> getAllWeekendHolidays(List<HolidayDTO> holidays) {
        return holidays.stream()
                              .filter(date -> isWeekendHoliday(date.getRealDate()))
                              .collect(Collectors.toList());
    }
    
     // returns a list of HolidayDTO objects that only occur on weekdays
    @Override
    public List<HolidayDTO> getAllWeekdayHolidays(List<HolidayDTO> holidays) {
        return holidays.stream()
                              .filter(date -> !isWeekendHoliday(date.getRealDate()))
                              .collect(Collectors.toList());
    }



    /*  Gets all holidays and their adjusted real dates over the range of (possibly) multiple years, given a checkout Date and rental days
        -->  In case we have a situation where let's say checkout Date was on Dec 22nd, rental days was say 20, 
                and christmas eve and new years day were added to the database as holidays 
            --> This would mean we would need to consider holidays across multiple years
            OR, let's say that we had large companies who wanted to rent out tools for many months or even several years
    
        -- Note:  Should consider adding a maximum to the number of rental days for this reason....
    */
    @Override
    public List<HolidayDTO> getAllHolidaysForCheckout(LocalDate checkoutDate, int rentalDayCount) {
        List<Holiday> holidays = (List<Holiday>) holidayRepository.findAll();
        // we need to make sure we get something back from the repo; otherwise, something is wrong, so just return null:
        if(holidays == null) {
            return null;
        }
        LocalDate endDate = checkoutDate.plusDays(rentalDayCount);
        
        List<HolidayDTO> allAdjustedHolidays = new ArrayList<>();
        int startYear = checkoutDate.getYear();
        int endYear = endDate.getYear();
        // loop through each year, and add all holidays corresponding to that year to the allAdjustHolidays list
        for (int year = startYear; year <= endYear; year++) {
            LocalDate tempCheckoutDate = checkoutDate.withYear(year);       // grab the checkout date associated with the given year
            allAdjustedHolidays.addAll(getAllHolidaysForCheckoutYear(holidays, tempCheckoutDate, endDate));
        }
        // return all adjusted holidays that are within the actual range of checkout date and the end/ due date
        return allAdjustedHolidays;
    }
    
    // gets the set of holidays and their associated "real" dates for a given checkout date's year
    @Override
    public List<HolidayDTO> getAllHolidaysForCheckoutYear( List<Holiday> holidays, LocalDate checkoutDate,  LocalDate endDate) {
        return holidays.stream()
                              .map(date -> convertToDto(date, checkoutDate))
                              .filter(date -> checkHolidayInRange(date.getRealDate(), checkoutDate, endDate))
                              .collect(Collectors.toList());
    }

    // Converts holiday object into Holiday DTO object
    @Override
    public HolidayDTO convertToDto(Holiday holiday) {
        return modelMapper.map(holiday, HolidayDTO.class);
    }
    
    // Converts holiday object into Holiday DTO object, and with given checkout date, adjusts the real date to correspond to the same year as it
    @Override
    public HolidayDTO convertToDto(Holiday holiday, LocalDate checkoutDate) {
        HolidayDTO holidayDTO = modelMapper.map(holiday, HolidayDTO.class);
        holidayDTO.setRealDate(calculateRealDate(holiday, checkoutDate));
        return holidayDTO;
    }

    // Helper methods 
    
    // calculates a holiday's real date as it is seen by/ registered by business rules (for a given checkout date)
    private LocalDate calculateRealDate(Holiday holiday, LocalDate checkoutDate) {
        DayOfWeek weekday = null;
        if (holiday.getAdjustWeekday() != null) {
            weekday = DayOfWeek.valueOf(holiday.getAdjustWeekday().toUpperCase());
        }
        // set occurence date year to checkout date's year
        LocalDate correspondingHoliday = holiday.getOccurrenceDate().withYear(checkoutDate.getYear());      
        return HolidayAdjustments.applyPotentialHolidayAdjustment(holiday.getHolidayAdjustType(), correspondingHoliday, weekday);
    }
    
    // check if holiday is in correct range (inclusive)
    private Boolean checkHolidayInRange(LocalDate holiday, LocalDate startDate, LocalDate endDate) {
        return !(holiday.isBefore(startDate) || holiday.isAfter(endDate));
    }
    
    // check to see if the holiday occurs on a weekend
    private Boolean isWeekendHoliday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;    
    }


}
