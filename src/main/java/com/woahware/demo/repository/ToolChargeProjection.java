
package com.woahware.demo.repository;

import com.woahware.demo.models.Tool;
import com.woahware.demo.models.ToolType;
import com.woahware.demo.models.ToolTypeCharge;


public interface ToolChargeProjection {
    Tool getTool();
    ToolType getToolType();
    ToolTypeCharge getToolTypeCharge();
}