package com.company.hr.entity;

import java.time.LocalDateTime;



import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Organization {
    
    private Long orgId;
    
    private String orgCode;
    private String orgName;
    private Long parentOrgId;
    private String orgPath;
    private String levelCode;
    private Integer orgLevel;
    private String fullCode;
    private String status;
    
    private LocalDateTime createdTime;
    
    private LocalDateTime updatedTime;
    
    private Long createdBy;
    
    private String parentOrgName;
}