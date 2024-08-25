package com.woahware.demo.service;

import com.woahware.demo.dto.RentalAgreement;

import com.woahware.demo.models.Tool;
import com.woahware.demo.repository.ToolChargeProjection;
import java.time.LocalDate;

public interface ToolService extends GenericService<Tool> {

    ToolChargeProjection findByToolCode(String toolCode);
    RentalAgreement generateRentalAgreement(String toolCode, LocalDate checkoutDate, int rentalDayCount, int discountPercent);
    
}
