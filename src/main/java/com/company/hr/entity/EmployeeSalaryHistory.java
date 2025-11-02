package com.company.hr.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmployeeSalaryHistory {
    
    private Long historyId;
    
    private String empId;
    private Long salaryStandardId;
    private String salaryItems; // JSON格式
    private Double totalAmount;
    private LocalDate effectiveDate;
    private LocalDate expirationDate;
    private String changeReason;
    
    private LocalDateTime changedTime;
    
    private Long changedBy;
    
    private String employeeName;
    
    private String stdName;
}