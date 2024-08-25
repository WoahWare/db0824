package com.woahware.demo.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woahware.demo.models.ToolTypeCharge;
import com.woahware.demo.service.ToolTypeChargeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.persistence.EntityNotFoundException;


@Controller
@RequestMapping("/toolTypeCharges")
public class ToolTypeChargeController {

    @Autowired
    private ToolTypeChargeService toolTypeChargeService;

    @GetMapping
    public String listToolTypeCharges(Model model) {
        List<ToolTypeCharge> toolTypeCharges = toolTypeChargeService.getAll();
        model.addAttribute("toolTypeCharges", toolTypeCharges);
        return "tool-charge-list";
    }

    @GetMapping("/{id}")
    public String getToolTypeChargeById(@PathVariable int id, Model model) {
        ToolTypeCharge toolTypeCharge = toolTypeChargeService.getById(id);
        model.addAttribute("toolTypeCharge", toolTypeCharge);
        return "tool-charge-detail";
    }
 
    
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleCustomException(EntityNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "errorPage";
    }
    
    
}



