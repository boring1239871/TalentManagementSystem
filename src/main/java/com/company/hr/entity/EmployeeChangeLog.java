package com.company.hr.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmployeeChangeLog {
    
    private Long logId;
    
    private String empId;
    private String changeType; // create, update, delete, restore, review
    private String changedField;
    private String oldValue;
    private String newValue;
    private String changeReason;
    
    private LocalDateTime changedTime;
    
    private Long changedBy;
    
    private String changedByName;
    
    private String employeeName;
}