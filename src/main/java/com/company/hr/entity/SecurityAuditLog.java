package com.company.hr.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SecurityAuditLog {

    private Long auditId;

    private Long userId;
    private String targetEmpId;
    private String operationType;
    private String tableName;
    private String recordId;
    private String oldValues; // JSON格式
    private String newValues; // JSON格式
    private String ipAddress;
    private String userAgent;
    private Boolean selfOperation;
    private Boolean securityCheckPassed;
    private String checkFailedReason;

    private LocalDateTime operationTime;
}