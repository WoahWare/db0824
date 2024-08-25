package com.woahware.demo.dto;

public class RentalAgreementForm {
    private String toolCode;
    private String checkOutDate;
    private int rentalDayCount;
    private int discountPercent;

    // Getters and setters
    public String getToolCode() { return toolCode; }
    public String getCheckOutDate() { return checkOutDate; }
    public int getRentalDayCount() { return rentalDayCount; }
    public int getDiscountPercent() { return discountPercent; }
    
    public void setToolCode(String toolCode) { this.toolCode = toolCode; }
    public void setCheckOutDate(String checkOutDate) { this.checkOutDate = checkOutDate; }
    public void setRentalDayCount(int rentalDayCount) { this.rentalDayCount = rentalDayCount; }
    public void setDiscountPercent(int discountPercent) { this.discountPercent = discountPercent; }
}