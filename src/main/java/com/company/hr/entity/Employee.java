package com.company.hr.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Employee {

    private String empId;
    
    private String employeeNo;
    private String name;
    private String gender;
    private String idCard;
    private String email;
    private String phone;
    private String mobile;
    
    private Long orgId;
    private Long positionId;
    private Long titleId;
    private Long salaryStandardId;
    
    private Boolean isSensitiveRecord = false;
    private Boolean requireDoubleApproval = false;
    private String status;
    
    private LocalDateTime createdTime;
    
    private LocalDateTime reviewedTime;
    
    private Long createdBy;
    
    private Long reviewedBy;
    
    private String orgName;
    
    private String positionName;
    
    private String titleName;
}