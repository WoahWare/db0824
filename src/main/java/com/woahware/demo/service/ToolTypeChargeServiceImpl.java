
package com.woahware.demo.service;

import com.woahware.demo.models.ToolTypeCharge;
import com.woahware.demo.repository.ToolTypeChargeRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ToolTypeChargeServiceImpl implements ToolTypeChargeService {
    
    private final ToolTypeChargeRepository toolTypeChargeRepository;
    
    @Value("${exception.INVALID_TOOL_TYPE.message}")
    private String  INVALID_TOOL_TYPE;
     
    @Autowired
    public ToolTypeChargeServiceImpl(ToolTypeChargeRepository toolTypeChargeRepository) {
        this.toolTypeChargeRepository = toolTypeChargeRepository;
    }
    
    @Override
    public List<ToolTypeCharge> getAll() {
        return (List<ToolTypeCharge>) toolTypeChargeRepository.findAll();
    }

    @Override
    public ToolTypeCharge getById(long id) {
        return toolTypeChargeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException( INVALID_TOOL_TYPE));
    }
    
}

