package com.company.hr.entity;

import java.time.LocalDateTime;



import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Position {
    
    private Long positionId;
    
    private String positionCode;
    private String positionName;
    private String positionDesc;
    private Long orgId;
    private String positionCategory;
    private String positionGrade;
    private String status;
    
    private LocalDateTime createdTime;
    
    private LocalDateTime updatedTime;
    
    private Long createdBy;
    
    private String orgName;
}