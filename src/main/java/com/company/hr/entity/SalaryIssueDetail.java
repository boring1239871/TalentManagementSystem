package com.company.hr.entity;

import java.time.LocalDateTime;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SalaryIssueDetail {
    
    private Long detailId;
    
    private Long issueId;
    private String empId;
    private Long salaryStandardId;
    private String standardItems; // JSON格式
    private Double baseSalaryAmount;
    private Double bonusAmount;
    private Double deductionAmount;
    private String adjustmentReason;
    private Double subtotalAmount;
    private Double totalAmount;
    private String status; // active, adjusted, cancelled
    
    private LocalDateTime createdTime;
    
    private LocalDateTime updatedTime;
    
    private String employeeName;
    
    private String employeeNo;
    
    private String orgName;

    private String issueCode;
}