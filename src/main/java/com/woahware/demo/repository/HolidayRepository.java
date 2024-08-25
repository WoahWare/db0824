package com.woahware.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.woahware.demo.models.Holiday;


@Repository
public interface HolidayRepository extends CrudRepository<Holiday, Long>{
    
}
