
package com.woahware.demo.service;

import com.woahware.demo.models.Brand;
import com.woahware.demo.repository.BrandRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class BrandServiceImpl implements BrandService {
    
    private final BrandRepository brandRepository;
    
    @Value("${exception.INVALID_BRAND.message}")
    private String  INVALID_BRAND;
     
    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    
    @Override
    public List<Brand> getAll() {
        return (List<Brand>) brandRepository.findAll();
    }

    @Override
    public Brand getById(long id) {
        return brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException( INVALID_BRAND));
    }
    
}

