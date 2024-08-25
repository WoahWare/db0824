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
@Table(name = "brand")
@Where(clause = "deprecated_on IS NULL")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    private LocalDate deprecatedOn;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDeprecatedOn() {
        return deprecatedOn;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeprecatedOn(LocalDate deprecatedOn) {
        this.deprecatedOn = deprecatedOn;
    }

    @Override
    public String toString() {
        return "Brand{" + "id=" + id + ", name=" + name + ", deprecatedOn=" + deprecatedOn + '}';
    }

    
}
