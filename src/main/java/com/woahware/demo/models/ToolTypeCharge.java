package com.woahware.demo.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "tool_type_charge")
@Where(clause = "deprecated_on IS NULL")
public class ToolTypeCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tool_type_id")
    private ToolType toolType;

    private BigDecimal dailyCharge;

    private Boolean weekDayCharge;

    private Boolean weekEndCharge;

    private Boolean holidayCharge;

    private LocalDate deprecatedOn;

    public long getId() {
        return id;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public Boolean getWeekDayCharge() {
        return weekDayCharge;
    }

    public Boolean getWeekEndCharge() {
        return weekEndCharge;
    }

    public Boolean getHolidayCharge() {
        return holidayCharge;
    }

    public LocalDate getDeprecatedOn() {
        return deprecatedOn;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public void setDailyCharge(BigDecimal dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public void setWeekDayCharge(Boolean weekDayCharge) {
        this.weekDayCharge = weekDayCharge;
    }

    public void setWeekEndCharge(Boolean weekEndCharge) {
        this.weekEndCharge = weekEndCharge;
    }

    public void setHolidayCharge(Boolean holidayCharge) {
        this.holidayCharge = holidayCharge;
    }

    public void setDeprecatedOn(LocalDate deprecatedOn) {
        this.deprecatedOn = deprecatedOn;
    }

    @Override
    public String toString() {
        return "ToolTypeCharge{" + "id=" + id + ", toolType=" + toolType + ", dailyCharge=" + dailyCharge + ", weekDayCharge=" + weekDayCharge + ", weekEndCharge=" + weekEndCharge + ", holidayCharge=" + holidayCharge + ", deprecatedOn=" + deprecatedOn + '}';
    }
    


}
