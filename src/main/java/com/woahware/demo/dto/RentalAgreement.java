package com.woahware.demo.dto;

import com.woahware.demo.utils.FieldFormatter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

// The rental agreement object that will be passed around between layers or services.
// -- Currently, we only generate this at the service level and do not persist anything into the database.
// -- If we wanted to persist it into the database, we would need to create a model and mapper that correspond to the Rental Agreement.
public class RentalAgreement {

    private String toolCode;
    private String toolType;
    private String toolBrand;
    private int rentalDays;
    private LocalDate checkOutDate;
    private LocalDate dueDate;
    private BigDecimal dailyRentalCharge;
    private int chargeDays;
    private BigDecimal preDiscountCharge;
    private int discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;

    // private constructor for the purpose of following the builder pattern
    private RentalAgreement(Builder builder) {
        this.toolCode = builder.toolCode;
        this.toolType = builder.toolType;
        this.toolBrand = builder.toolBrand;
        this.rentalDays = builder.rentalDays;
        this.checkOutDate = builder.checkOutDate;
        this.dailyRentalCharge = builder.dailyRentalCharge;
        this.discountPercent = builder.discountPercent;
        this.chargeDays = builder.chargeDays;

        this.dueDate = calculateDueDate();
        this.preDiscountCharge = calculatePreDiscountCharge();
        this.discountAmount = calculateDiscountAmount();
        this.finalCharge = calculateFinalCharge();
    }

    // Builder for rental agreements
    public static class Builder {

        private String toolCode;
        private String toolType;
        private String toolBrand;
        private int rentalDays;
        private LocalDate checkOutDate;
        private BigDecimal dailyRentalCharge;
        private int discountPercent;
        private int chargeDays;
        
        
        // Setters (could also rename them to Withs, but I prefer it this way as it keeps things standardized with other classes)
        
        public Builder setToolCode(String toolCode) {
            this.toolCode = toolCode;
            return this;
        }

        public Builder setToolType(String toolType) {
            this.toolType = toolType;
            return this;
        }

        public Builder setToolBrand(String toolBrand) {
            this.toolBrand = toolBrand;
            return this;
        }

        public Builder setRentalDays(int rentalDays) {
            this.rentalDays = rentalDays;
            return this;
        }

        public Builder setCheckOutDate(LocalDate checkOutDate) {
            this.checkOutDate = checkOutDate;
            return this;
        }

        public Builder setDailyRentalCharge(BigDecimal dailyRentalCharge) {
            this.dailyRentalCharge = dailyRentalCharge.setScale(2, RoundingMode.HALF_UP);
            return this;
        }

        public Builder setDiscountPercent(int discountPercent) {
            this.discountPercent = discountPercent;
            return this;
        }
        
        public Builder setChargeDays(int chargeDays) {
            this.chargeDays = chargeDays;
            return this;
        }

        public RentalAgreement build() {
            return new RentalAgreement(this);
        }
    }

    // Calculate the due date
    private LocalDate calculateDueDate() {
        return checkOutDate.plusDays(rentalDays);
    }

    // Calculate the pre-discount charge
    private BigDecimal calculatePreDiscountCharge() {
        return dailyRentalCharge
                .multiply(BigDecimal.valueOf(chargeDays))
                .setScale(2, RoundingMode.HALF_UP);
    }

    // Calculate the discount amount
    private BigDecimal calculateDiscountAmount() {
        return preDiscountCharge
                    .multiply(BigDecimal.valueOf(discountPercent))
                    .divide(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);
    }

    // Calculate the final charge
    private BigDecimal calculateFinalCharge() {
        return preDiscountCharge
                .subtract(discountAmount)
                .setScale(2, RoundingMode.HALF_UP);
    }

    // Getters
    
    public String getToolCode() {
        return toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public String getToolBrand() {
        return toolBrand;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BigDecimal getDailyRentalCharge() {
        return dailyRentalCharge;
    }

    public int getChargeDays() {
        return chargeDays;
    }

    public BigDecimal getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getFinalCharge() {
        return finalCharge;
    }

    
    // String related methods:
 
    @Override
    public String toString() {
        return "RentalAgreement{" + "toolCode=" + toolCode + ", toolType=" + toolType + ", toolBrand=" + toolBrand + ", rentalDays=" + rentalDays + ", checkOutDate=" + checkOutDate + ", dueDate=" + dueDate + ", dailyRentalCharge=" + dailyRentalCharge + ", chargeDays=" + chargeDays + ", preDiscountCharge=" + preDiscountCharge + ", discountPercent=" + discountPercent + ", discountAmount=" + discountAmount + ", finalCharge=" + finalCharge + '}';
    }

    
    //  prints out fields formatted, utilizing reflection
    public void printDetailsWithReflection() {
        System.out.println();
        System.out.println("Rental Agreement Details: ");
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String fieldName = FieldFormatter.convertFieldName(field.getName());
                String fieldValue = FieldFormatter.formatFieldValue(field, field.get(this));
                System.out.println(fieldName + ": " + fieldValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }
    
    
}
