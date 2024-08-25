package com.woahware.demo.service;

import com.woahware.demo.dto.HolidayDTO;
import com.woahware.demo.models.Holiday;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;



@ActiveProfiles("test")
public class HolidayServiceTest extends GenericServiceTest<Holiday, HolidayService> {

    @Autowired
    private HolidayService holidayService;

    @Value("${exception.INVALID_HOLIDAY.message}")
    private String INVALID_HOLIDAY;

    /**
     * Test of getHolidayDTO method, of class HolidayService.
     */
    @Test
    public void testGetHolidayDTO() {
        long id = -1;
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            HolidayDTO result = holidayService.getHolidayDTO(id);
        });
        String expectedMessage = INVALID_HOLIDAY;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of getAllHolidays method, of class HolidayService.
     */
    @Test
    public void testGetAllHolidays() {
        List<HolidayDTO> result = holidayService.getAllHolidays();
        System.out.println("RESULTS: " + result.toString());
        assertNotEquals(null, result);
    }

    /**
     * Test of getAllHolidaysForCheckout method, of class HolidayService.
     */
    @Test
    public void testGetAllHolidaysForCheckout() {
        LocalDate checkoutDate = LocalDate.parse("2015-09-03");
        int rentalDays = 10;
        List<HolidayDTO> result = holidayService.getAllHolidaysForCheckout(checkoutDate, rentalDays);
        System.out.println("RESULTS: " + result.toString());
        assertEquals(1, result.size());
    }

    @Test
    public void testGetAllHolidaysForCheckoutMonths() {
        LocalDate checkoutDate = LocalDate.parse("2015-07-02");
        int rentalDays = 90;
        List<HolidayDTO> result = holidayService.getAllHolidaysForCheckout(checkoutDate, rentalDays);
        System.out.println("RESULTS: " + result.toString());
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllHolidaysForCheckoutYear() {
        LocalDate checkoutDate = LocalDate.parse("2015-07-02");
        int rentalDays = 365;
        List<HolidayDTO> result = holidayService.getAllHolidaysForCheckout(checkoutDate, rentalDays);
        System.out.println("RESULTS: " + result.toString());
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllHolidaysForCheckoutYearPlus100() {
        LocalDate checkoutDate = LocalDate.parse("2015-07-02");
        int rentalDays = 465;
        List<HolidayDTO> result = holidayService.getAllHolidaysForCheckout(checkoutDate, rentalDays);
        System.out.println("RESULTS: " + result.toString());
        assertEquals(4, result.size());
    }

    @Test
    public void testGetAllHolidaysForCheckou10Years() {
        LocalDate checkoutDate = LocalDate.parse("2020-07-02");
        int rentalDays = 3650;
        List<HolidayDTO> result = holidayService.getAllHolidaysForCheckout(checkoutDate, rentalDays);
        System.out.println("RESULTS: " + result.toString());
        assertEquals(20, result.size());
    }

}
