package com.woahware.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.woahware.demo.models.Brand;

@Repository
public interface BrandRepository extends CrudRepository<Brand, Long>{

}
