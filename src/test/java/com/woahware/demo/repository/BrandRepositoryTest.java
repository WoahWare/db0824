package com.woahware.demo.repository;


import com.woahware.demo.models.Brand;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
public class BrandRepositoryTest extends GenericRepositoryTest<Brand, BrandRepository> {


}
