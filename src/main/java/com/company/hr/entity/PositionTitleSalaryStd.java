package com.company.hr.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;



import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PositionTitleSalaryStd {
    
    private Long mapId;
    
    private Long positionId;
    private Long titleId;
    private Long salaryStandardId;
    private LocalDate effectiveDate;
    private LocalDate expirationDate;
    private Boolean isCurrent;
    private String status;
    
    private LocalDateTime createdTime;
    
    private Long createdBy;
    
    private String positionName;
    
    private String titleName;
    
    private String stdName;
}