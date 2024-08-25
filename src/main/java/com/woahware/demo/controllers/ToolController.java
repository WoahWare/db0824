package com.woahware.demo.controllers;

import com.woahware.demo.dto.RentalAgreement;
import com.woahware.demo.dto.RentalAgreementForm;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woahware.demo.models.Tool;
import com.woahware.demo.service.ToolService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import javax.persistence.EntityNotFoundException;


@Controller
@RequestMapping("/tools")
public class ToolController {

    @Autowired
    private ToolService toolService;

    
    @GetMapping
    public String listTools(Model model) {
        List<Tool> tools = toolService.getAll();
        model.addAttribute("tools", tools);
        return "tool-list";
    }


    @GetMapping("/{id}")
    public String getToolById(@PathVariable int id, Model model) {
        Tool tool = toolService.getById(id);
        model.addAttribute("tool", tool);
        return "tool-detail";
    }


    @GetMapping("/rent")
    public String showRentalAgreementForm(@RequestParam(value = "toolCode", required = false) String toolCode, Model model) {
        RentalAgreementForm form = new RentalAgreementForm();
        if (toolCode != null && !toolCode.isEmpty()) {
            form.setToolCode(toolCode);     // Pre-fill the toolCode field
        }
        model.addAttribute("rentalAgreement", form);
        return "rental-agreement-form";
    }

    
    @PostMapping("/rent")
    public String generateRentalAgreement(@ModelAttribute RentalAgreementForm form, Model model) {
        
        LocalDate checkOutDate;
        try {
            // Attempt to parse the date from the string input
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            checkOutDate = LocalDate.parse(form.getCheckOutDate(), formatter);
        } catch (DateTimeParseException e) {
            // Handle the error if the date format is incorrect
            model.addAttribute("error", "Invalid date format. Please try using:  yyyy-MM-dd.");
            return "rental-agreement-form";
        }
        
        RentalAgreement agreement = toolService.generateRentalAgreement(
            form.getToolCode(), 
            checkOutDate, 
            form.getRentalDayCount(), 
            form.getDiscountPercent()
        );
        model.addAttribute("rentalAgreement", agreement);
        return "rental-agreement-result";
    }
    
   
    
    @ExceptionHandler(NoSuchElementException.class)
    public String handleCustomException(NoSuchElementException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "errorPage";
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleCustomException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "errorPage";
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleCustomException(EntityNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "errorPage";
    }
    
    
}


