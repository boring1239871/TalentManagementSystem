package com.company.hr.entity;

import java.time.LocalDateTime;



import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SalaryIssueAdjustment {
    
    private Long adjustmentId;
    
    private Long detailId;
    private String adjustType; // bonus, deduction
    private Double oldAmount;
    private Double newAmount;
    private String adjustReason;
    
    private LocalDateTime adjustedTime;
    
    private Long adjustedBy;
    
    private String adjustedByName;
    
    private String employeeName;
}