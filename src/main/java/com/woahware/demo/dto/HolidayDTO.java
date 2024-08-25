
package com.woahware.demo.dto;

import java.time.LocalDate;

// Simple DTO object for Holidays for the purpose of passing around between layers and/ or services
public class HolidayDTO {
    private long id;
    private String name;
    private LocalDate realDate;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getRealDate() {
        return realDate;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setRealDate(LocalDate realDate) {
        this.realDate = realDate;
    }

    
    @Override
    public String toString() {
        return "HolidayDTO{" + "name=" + name + ", realDate=" + realDate + '}';
    }
    
    
    
}

