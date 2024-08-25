package com.woahware.demo.models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "holiday")
@Where(clause = "deprecated_on IS NULL")
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    private LocalDate occurrenceDate;
    
    private String holidayAdjustType;
    
    private String adjustWeekday;

    private LocalDate deprecatedOn;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getOccurrenceDate() {
        return occurrenceDate;
    }

    public LocalDate getDeprecatedOn() {
        return deprecatedOn;
    }

    public String getHolidayAdjustType() {
        return holidayAdjustType;
    }

    public String getAdjustWeekday() {
        return adjustWeekday;
    }
    

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOccurrenceDate(LocalDate occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    public void setDeprecatedOn(LocalDate deprecatedOn) {
        this.deprecatedOn = deprecatedOn;
    }

    public void setHolidayAdjustType(String holidayAdjustType) {
        this.holidayAdjustType = holidayAdjustType;
    }

    public void setAdjustWeekday(String adjustWeekday) {
        this.adjustWeekday = adjustWeekday;
    }

    @Override
    public String toString() {
        return "Holiday{" + "id=" + id + ", name=" + name + ", occurrenceDate=" + occurrenceDate + ", holidayAdjustType=" + holidayAdjustType + ", adjustWeekday=" + adjustWeekday + ", deprecatedOn=" + deprecatedOn + '}';
    }
    

}
