package com.company.hr.entity;

import java.time.LocalDateTime;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PositionTitleMapping {
    
    private Long mappingId;
    
    private Long positionId;
    private Long titleId;
    private Boolean isRequired;
    private Integer displayOrder;
    private String status;
    
    private LocalDateTime createdTime;
    
    private Long createdBy;
    
    private String positionName;
    
    private String titleName;
}