package com.company.hr.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class User {
    
    private Long userId;
    
    private String username;
    private String password;
    private String realName;
    private String associatedEmpId;
    private String email;
    private String phone;
    private String role;
    private String status;
    private LocalDateTime lastLoginTime;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private Long createdBy;
}