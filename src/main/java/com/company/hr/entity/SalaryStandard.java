package com.company.hr.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SalaryStandard {
    
    private Long stdId;
    
    private String stdCode;
    private String stdName;
    private String creator;
    private Long createdBy;
    private Long applicablePositionId;
    private String status; // draft, pending_review, approved, rejected, inactive
    private Long reviewedBy;
    private LocalDateTime reviewedTime;
    private String reviewComment;
    private String salaryItems; // JSON格式
    private Double baseSalary;
    private Double totalAmount;
    private Long updatedBy;
    
    private LocalDateTime createdTime;
    
    private LocalDateTime updatedTime;
    
    private String positionName;
}