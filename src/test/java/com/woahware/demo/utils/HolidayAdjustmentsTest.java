package com.woahware.demo.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;


public class HolidayAdjustmentsTest {
    
    public HolidayAdjustmentsTest() {}
 
    @Rule
    public TestRule watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            System.out.println("Starting Test: " + description.getMethodName());
        }
    };
    
    /**
     * Test of applyPotentialHolidayAdjustment method, of class HolidayAdjustments.
     */
    @Test
    public void testApplyPotentialHolidayAdjustmentJuly4th() {
        String holidayType = "weekend";
        LocalDate date =  LocalDate.parse("2015-07-04");
        DayOfWeek weekday = null;
        
        LocalDate expResult = LocalDate.parse("2015-07-03");
        LocalDate result = HolidayAdjustments.applyPotentialHolidayAdjustment(holidayType, date, weekday);
        assertEquals(expResult, result);
    }

    
    @Test
    public void testApplyPotentialHolidayAdjustmentLaborDay() {
        String inputDay = "Monday";
        String holidayType = "first_weekday";
        LocalDate date =  LocalDate.parse("2015-09-01");
        DayOfWeek weekday =  DayOfWeek.valueOf( inputDay.toUpperCase() ); 
        
        LocalDate expResult = LocalDate.parse("2015-09-07");
        LocalDate result = HolidayAdjustments.applyPotentialHolidayAdjustment(holidayType, date, weekday);
        assertEquals(expResult, result);
    }
    
    
    @Test
    public void testApplyPotentialHolidayAdjustmentNullHolidayType() {
        String holidayType = null;
        LocalDate date =  LocalDate.parse("2015-07-04");
        DayOfWeek weekday = null;
        
        LocalDate expResult = LocalDate.parse("2015-07-04");
        LocalDate result = HolidayAdjustments.applyPotentialHolidayAdjustment(holidayType, date, weekday);
        assertEquals(expResult, result);
    }


    
}
