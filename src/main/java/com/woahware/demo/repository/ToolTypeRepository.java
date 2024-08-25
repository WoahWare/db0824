package com.woahware.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.woahware.demo.models.ToolType;

@Repository
public interface ToolTypeRepository extends CrudRepository<ToolType, Long>{

}
