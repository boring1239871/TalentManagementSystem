package com.company.hr.entity;

import java.time.LocalDateTime;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProfessionalTitle {
    
    private Long titleId;
    
    private String titleCode;
    private String titleName;
    private String titleLevel; // junior, intermediate, senior, expert
    private String description;
    private Integer displayOrder;
    private String status;
    
    private LocalDateTime createdTime;
    
    private LocalDateTime updatedTime;
    
    private Long createdBy;
}