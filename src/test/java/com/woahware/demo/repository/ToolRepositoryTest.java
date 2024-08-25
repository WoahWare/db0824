package com.woahware.demo.repository;

import com.woahware.demo.models.Tool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
public class ToolRepositoryTest extends GenericRepositoryTest<Tool, ToolRepository>{
    
    @Autowired
    private ToolRepository toolRepository;

    @Test
    public void testGetByToolCode() {
        // Act: Fetch tool charge by tool_code
        ToolChargeProjection toolCharges = toolRepository.getChargeInfoByToolCode("JAKR");
        
        System.out.println("RESULTS: " + toolCharges.toString());
        System.out.println("RESULTS: " + toolCharges.getTool().toString());
        System.out.println("RESULTS: " + toolCharges.getToolType().toString());
        System.out.println("RESULTS: " + toolCharges.getToolTypeCharge().toString());
        // Assert: Validate the result is not null
        assertNotEquals(toolCharges, null);
        
    }
}
