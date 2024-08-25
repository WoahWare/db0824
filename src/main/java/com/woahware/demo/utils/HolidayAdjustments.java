
package com.woahware.demo.utils;

import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HolidayAdjustments {
    
    private static final Map<String, BiFunction<LocalDate, DayOfWeek, LocalDate>> HOLIDAY_FUNCTION_MAP = new HashMap<>();
    private static final Logger logger = Logger.getAnonymousLogger();
    
    static {
        // Populate the holiday functions map with functions depending on the type of holiday rules that a holiday follows
        HOLIDAY_FUNCTION_MAP.put("weekend", (date, weekday) -> adjustWeekendHoliday(date));
        HOLIDAY_FUNCTION_MAP.put("first_weekday", (date, weekday) -> adjustFirstWeekdayInMonthHoliday(date,weekday));    
    }
    
    
    // Based on the holiday adjust type, get the associated function (from holiday_function_map) that adjusts the holiday's date (based on business rules)
    public static LocalDate applyPotentialHolidayAdjustment(String holidayAdjustType, LocalDate date, DayOfWeek weekday) {
        LocalDate newHolidayDate = date;
        if (HOLIDAY_FUNCTION_MAP.containsKey(holidayAdjustType)) {
            newHolidayDate =  HOLIDAY_FUNCTION_MAP.get(holidayAdjustType).apply(date, weekday);
            logger.log(Level.INFO, MessageFormat.format("New Adjusted Holiday date: {0} was created for original date: {1}", 
                                                                                                newHolidayDate, date) );
        } else {
            logger.log(Level.INFO,"Holiday adjust type not found");
        }
        return newHolidayDate;
    }
    
    
    // For example, Independence Day is on July 4th, but if it falls on Saturday -> then the adjusted day is Friday;
    //  If on Sunday, then the adjusted day is Monday.
    public static LocalDate adjustWeekendHoliday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == null) {
            return date;    // Otherwise, no adjustment
        } else switch (dayOfWeek) {
            case SUNDAY:
                return date.plusDays(1);     // If Sunday, move to Monday
            case SATURDAY:
                return date.plusDays(-1);   // If Saturday, move to Friday
            default:
                return date;    // Otherwise, no adjustment
        }
    }
    
    
    // Labor Day is the first Monday of September for example --> DayOfWeek.MONDAY
    public static LocalDate adjustFirstWeekdayInMonthHoliday(LocalDate date, DayOfWeek weekday) {
        return date.with(TemporalAdjusters.firstInMonth(weekday));
    }
    
    
    // calculates the number of weekdays given a start date and an end date
    public static long calcWeekDays(LocalDate start, LocalDate end) {
        DayOfWeek startW = start.getDayOfWeek();
        DayOfWeek endW = end.getDayOfWeek();

        long days = ChronoUnit.DAYS.between(start, end);
        long daysWithoutWeekends = days - 2 * ((days + startW.getValue())/7);

        // adjust for starting and ending on a Sunday:
        return daysWithoutWeekends + (startW == DayOfWeek.SUNDAY ? 1 : 0) + (endW == DayOfWeek.SUNDAY ? 1 : 0);
    }
    
    
    
}
