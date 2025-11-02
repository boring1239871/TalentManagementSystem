package com.company.hr.service.impl;

import com.company.hr.entity.SecurityAuditLog;
import com.company.hr.mapper.SecurityAuditLogMapper;
import com.company.hr.service.SecurityAuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SecurityAuditLogServiceImpl implements SecurityAuditLogService {

    @Autowired
    private SecurityAuditLogMapper securityAuditLogMapper;

    @Override
    public List<SecurityAuditLog> findAll() {
        return securityAuditLogMapper.findAll();
    }

    @Override
    public SecurityAuditLog findById(Long auditId) {
        return securityAuditLogMapper.findById(auditId);
    }

    @Override
    public void save(SecurityAuditLog log) {
        log.setOperationTime(LocalDateTime.now());
        securityAuditLogMapper.insert(log);
    }

    @Override
    public List<SecurityAuditLog> findAllWithUser() {
        return securityAuditLogMapper.findAllWithUser();
    }

    @Override
    public void recordSecurityAudit(Long userId, String targetEmpId, String operationType, String tableName,
            String recordId, String oldValues, String newValues, String ipAddress, String userAgent,
            boolean isSelfOperation, boolean securityCheckPassed, String checkFailedReason) {
        SecurityAuditLog log = new SecurityAuditLog();
        log.setUserId(userId);
        log.setTargetEmpId(targetEmpId);
        log.setOperationType(operationType);
        log.setTableName(tableName);
        log.setRecordId(recordId);
        log.setOldValues(oldValues);
        log.setNewValues(newValues);
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);
        log.setSelfOperation(isSelfOperation);
        log.setSecurityCheckPassed(securityCheckPassed);
        log.setCheckFailedReason(checkFailedReason);
        save(log);
    }
}