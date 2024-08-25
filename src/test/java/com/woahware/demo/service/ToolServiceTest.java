package com.woahware.demo.service;

import com.woahware.demo.dto.RentalAgreement;
import com.woahware.demo.models.Tool;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;



@ActiveProfiles("test")
public class ToolServiceTest extends GenericServiceTest<Tool, ToolService> {

    @Autowired
    private ToolService toolService;

    @Value("${exception.INVALID_RENTAL_DAYS.message}")
    private String INVALID_RENTAL_DAYS;
    
    @Value("${exception.INVALID_DISCOUNT.message}")
     private String INVALID_DISCOUNT;
    
    
    /**
     * Test of generateRentalAgreement method, of class ToolServiceImpl.
     */
    @Test
    public void testGenerateRentalAgreement1() {
        String toolCode = "JAKR";
        int rentalDayCount = 5;
        int discountPercent = 101;
        LocalDate checkoutDate = LocalDate.parse("2015-09-03");
        
        // Should fail because of 101% discount... so, assert/ expect that we did indeed fail.
        Exception exception = assertThrows(RuntimeException.class, () -> {
            RentalAgreement rentalAgreement = toolService.generateRentalAgreement(toolCode, checkoutDate, rentalDayCount, discountPercent);
        });

        String expectedMessage = INVALID_DISCOUNT;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGenerateRentalAgreement2() throws Exception {
        String toolCode = "LADW";
        int rentalDayCount = 3;
        int discountPercent = 10;
        LocalDate checkoutDate = LocalDate.parse("2020-07-02");
        RentalAgreement rentalAgreement = toolService.generateRentalAgreement(toolCode, checkoutDate, rentalDayCount, discountPercent);
        
        // expected final charge for comparison.
        BigDecimal expectedFinalCharge = BigDecimal.valueOf(3.58).setScale(2);
        int expectedChargeDays = 2;
        assertEquals(rentalAgreement.getFinalCharge(), expectedFinalCharge); 
        assertEquals(rentalAgreement.getChargeDays(), expectedChargeDays); 
    }

    @Test
    public void testGenerateRentalAgreement3() throws Exception {
        String toolCode = "CHNS";
        int rentalDayCount = 5;
        int discountPercent = 25;
        LocalDate checkoutDate = LocalDate.parse("2015-07-02");
        RentalAgreement rentalAgreement = toolService.generateRentalAgreement(toolCode, checkoutDate, rentalDayCount, discountPercent);
        
        // expected final charge for comparison.
        BigDecimal expectedFinalCharge = BigDecimal.valueOf(3.35).setScale(2);
        int expectedChargeDays = 3;
        assertEquals(rentalAgreement.getFinalCharge(), expectedFinalCharge); 
        assertEquals(rentalAgreement.getChargeDays(), expectedChargeDays); 
    }

    @Test
    public void testGenerateRentalAgreement4() throws Exception {
        String toolCode = "JAKD";
        int rentalDayCount = 6;
        int discountPercent = 0;
        LocalDate checkoutDate = LocalDate.parse("2015-09-03");
        RentalAgreement rentalAgreement = toolService.generateRentalAgreement(toolCode, checkoutDate, rentalDayCount, discountPercent);
        
        // expected final charge for comparison.
        BigDecimal expectedFinalCharge = BigDecimal.valueOf(8.97).setScale(2);
        int expectedChargeDays = 3;
        assertEquals(rentalAgreement.getFinalCharge(), expectedFinalCharge); 
        assertEquals(rentalAgreement.getChargeDays(), expectedChargeDays); 
    }

    @Test
    public void testGenerateRentalAgreement5() throws Exception {
        String toolCode = "JAKR";
        int rentalDayCount = 9;
        int discountPercent = 0;
        LocalDate checkoutDate = LocalDate.parse("2015-07-02");
        RentalAgreement rentalAgreement = toolService.generateRentalAgreement(toolCode, checkoutDate, rentalDayCount, discountPercent);
        
        // expected final charge for comparison.
        BigDecimal expectedFinalCharge = BigDecimal.valueOf(17.94).setScale(2);
        int expectedChargeDays = 6;
        assertEquals(rentalAgreement.getFinalCharge(), expectedFinalCharge); 
        assertEquals(rentalAgreement.getChargeDays(), expectedChargeDays); 

    }

    @Test
    public void testGenerateRentalAgreement6() throws Exception {
        String toolCode = "JAKR";
        int rentalDayCount = 4;
        int discountPercent = 50;
        LocalDate checkoutDate = LocalDate.parse("2020-07-02");
        RentalAgreement rentalAgreement = toolService.generateRentalAgreement(toolCode, checkoutDate, rentalDayCount, discountPercent);
       
        // expected final charge for comparison.
        BigDecimal expectedFinalCharge = BigDecimal.valueOf(1.49).setScale(2);
        int expectedChargeDays = 1;
        assertEquals(rentalAgreement.getFinalCharge(), expectedFinalCharge); 
        assertEquals(rentalAgreement.getChargeDays(), expectedChargeDays); 
    }
    
    @Test
    public void testGenerateRentalAgreementRentalDaysInvalid() {
        String toolCode = "CHNS";
        int rentalDayCount = 0;
        int discountPercent = 5;
        LocalDate checkoutDate = LocalDate.parse("2015-09-03");
        
        // Should fail because of 101% discount... so, assert/ expect that we did indeed fail.
        Exception exception = assertThrows(RuntimeException.class, () -> {
            RentalAgreement rentalAgreement = toolService.generateRentalAgreement(toolCode, checkoutDate, rentalDayCount, discountPercent);
        });

        String expectedMessage = INVALID_RENTAL_DAYS;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void testGenerateRentalAgreementRentalDays365() throws Exception {
        String toolCode = "JAKR";
        int rentalDayCount = 365;
        int discountPercent = 10;
        LocalDate checkoutDate = LocalDate.parse("2020-07-02");
        RentalAgreement rentalAgreement = toolService.generateRentalAgreement(toolCode, checkoutDate, rentalDayCount, discountPercent);
       
        // expected final charge for comparison.
        BigDecimal expectedFinalCharge = BigDecimal.valueOf(696.97).setScale(2);
        int expectedChargeDays = 259;
        assertEquals(rentalAgreement.getFinalCharge(), expectedFinalCharge); 
        assertEquals(rentalAgreement.getChargeDays(), expectedChargeDays); 
    }
    
    
    // This one is probably over-kill, but hey, what's the point of being an engineer if you don't get to push things to the limit?
    @Test
    public void testGenerateRentalAgreementRentalDays3650() throws Exception {
        String toolCode = "LADW";
        int rentalDayCount = 3650;      // 10 years
        int discountPercent = 20;
        LocalDate checkoutDate = LocalDate.parse("2020-07-02");
        RentalAgreement rentalAgreement = toolService.generateRentalAgreement(toolCode, checkoutDate, rentalDayCount, discountPercent);
       
        // expected final charge for comparison.
        BigDecimal expectedFinalCharge = BigDecimal.valueOf(5778.96).setScale(2);
        int expectedChargeDays = 3630;
        assertEquals(rentalAgreement.getFinalCharge(), expectedFinalCharge); 
        assertEquals(rentalAgreement.getChargeDays(), expectedChargeDays); 
    }

}
