package com.woahware.demo.models;

import java.time.LocalDate;
import javax.persistence.Column;
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
@Table(name = "tool")
@Where(clause = "deprecated_on IS NULL")
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String toolCode;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tool_type_id")
    private ToolType toolType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private LocalDate deprecatedOn;

    public long getId() {
        return id;
    }

    public String getToolCode() {
        return toolCode;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public Brand getBrand() {
        return brand;
    }

    public LocalDate getDeprecatedOn() {
        return deprecatedOn;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void setDeprecatedOn(LocalDate deprecatedOn) {
        this.deprecatedOn = deprecatedOn;
    }

    @Override
    public String toString() {
        return "Tool{" + "id=" + id + ", toolCode=" + toolCode + ", toolType=" + toolType + ", brand=" + brand + ", deprecatedOn=" + deprecatedOn + '}';
    }
    
    
}
