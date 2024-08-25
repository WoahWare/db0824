package com.woahware.demo.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woahware.demo.models.Brand;
import com.woahware.demo.service.BrandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.persistence.EntityNotFoundException;


@Controller
@RequestMapping("/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public String listBrands(Model model) {
        List<Brand> brands = brandService.getAll();
        model.addAttribute("brands", brands);
        return "brand-list";
    }

    @GetMapping("/{id}")
    public String getBrandById(@PathVariable int id, Model model) {
        Brand brand = brandService.getById(id);
        model.addAttribute("brand", brand);
        return "brand-detail";
    }
 
    
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleCustomException(EntityNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "errorPage";
    }
    
}


