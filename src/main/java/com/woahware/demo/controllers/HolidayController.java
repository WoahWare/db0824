package com.woahware.demo.controllers;

import com.woahware.demo.dto.HolidayDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.woahware.demo.service.HolidayService;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.persistence.EntityNotFoundException;


@Controller
@RequestMapping("/holidays")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @GetMapping
    public String listHolidays(Model model) {
        List<HolidayDTO> holidays = holidayService.getAllHolidays();
        model.addAttribute("holidays", holidays);
        return "holiday-list";
    }

    @GetMapping("/{id}")
    public String getHolidayById(@PathVariable int id, Model model) {
        HolidayDTO holiday = holidayService.getHolidayDTO(id, LocalDate.now());
        model.addAttribute("holiday", holiday);
        return "holiday-detail";
    }
 
    
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleCustomException(EntityNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "errorPage";
    }
    
}


