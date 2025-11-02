package com.company.hr.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmployeeSequence {
    
    private Long seqId;
    
    private Long orgId;
    private Integer sequenceYear;
    private Integer currentValue;
    
    private LocalDateTime createdTime;
    
    private LocalDateTime updatedTime;
    
    private String orgName;
}