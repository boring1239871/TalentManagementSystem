package com.company.hr.entity;

import java.time.LocalDateTime;



import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SecurityRule {
    
    private Long ruleId;
    
    private String ruleName;
    private String ruleType; // self_operation, department_scope, hierarchy_control
    private String ruleCondition;
    private String errorMessage;
    private String status;
    
    private LocalDateTime createdTime;
    
    private Long createdBy;
}