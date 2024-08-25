package com.woahware.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.woahware.demo.models.ToolTypeCharge;

@Repository
public interface ToolTypeChargeRepository extends CrudRepository<ToolTypeCharge, Long>{

}
