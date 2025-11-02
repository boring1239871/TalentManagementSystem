package com.company.hr.service;

import com.company.hr.entity.SecurityAuditLog;
import java.util.List;

public interface SecurityAuditLogService {

    // 基础CRUD方法
    List<SecurityAuditLog> findAll();

    SecurityAuditLog findById(Long auditId);

    void save(SecurityAuditLog log);

    // 业务方法
    List<SecurityAuditLog> findAllWithUser();

    void recordSecurityAudit(Long userId, String targetEmpId, String operationType, String tableName, String recordId,
            String oldValues, String newValues, String ipAddress, String userAgent, boolean isSelfOperation,
            boolean securityCheckPassed, String checkFailedReason);
}