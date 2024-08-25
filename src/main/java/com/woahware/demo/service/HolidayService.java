package com.woahware.demo.service;

import com.woahware.demo.dto.HolidayDTO;
import com.woahware.demo.models.Holiday;
import java.time.LocalDate;
import java.util.List;

public interface HolidayService extends GenericService<Holiday> {
    HolidayDTO getHolidayDTO(long id);
    HolidayDTO getHolidayDTO(long id, LocalDate checkoutDate);
    List<HolidayDTO> getAllHolidays();
    List<HolidayDTO> getAllWeekendHolidays(List<HolidayDTO> holidays);
    List<HolidayDTO> getAllWeekdayHolidays(List<HolidayDTO> holidays); 
    HolidayDTO convertToDto(Holiday holiday);
    HolidayDTO convertToDto(Holiday holiday, LocalDate checkoutDate);
    List<HolidayDTO> getAllHolidaysForCheckout(LocalDate checkoutDate, int rentalDays);
    List<HolidayDTO> getAllHolidaysForCheckoutYear( List<Holiday> holidays, LocalDate checkoutDate,  LocalDate endDate);
    
}
