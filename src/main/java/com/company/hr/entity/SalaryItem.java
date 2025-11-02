package com.company.hr.entity;

import java.time.LocalDateTime;



import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SalaryItem {
    
    private Long itemId;
    
    private String itemCode;
    private String itemName;
    private String itemType; // income, deduction
    private String calculationType; // fixed, formula, auto_calculation
    private String formulaExpression;
    private Boolean isSystemItem;
    private Integer displayOrder;
    private String status;
    
    private LocalDateTime createdTime;
    
    private LocalDateTime updatedTime;
    
    private Long createdBy;
}