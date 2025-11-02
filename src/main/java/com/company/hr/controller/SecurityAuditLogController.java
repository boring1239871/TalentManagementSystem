package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.SecurityAuditLog;
import com.company.hr.service.SecurityAuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security-audit-logs")
public class SecurityAuditLogController extends BaseController {

    @Autowired
    private SecurityAuditLogService securityAuditLogService;

    // 获取所有安全审计日志
    @GetMapping
    public Result<List<SecurityAuditLog>> getSecurityAuditLogList() {
        List<SecurityAuditLog> logs = securityAuditLogService.findAll();
        return Result.success(logs);
    }

    // 获取包含用户信息的所有安全审计日志
    @GetMapping("/with-user")
    public Result<List<SecurityAuditLog>> getSecurityAuditLogsWithUser() {
        List<SecurityAuditLog> logs = securityAuditLogService.findAllWithUser();
        return Result.success(logs);
    }

    // 获取安全审计日志详情
    @GetMapping("/{auditId}")
    public Result<SecurityAuditLog> getSecurityAuditLogDetail(@PathVariable Long auditId) {
        SecurityAuditLog log = securityAuditLogService.findById(auditId);
        return Result.success(log);
    }

    // 创建安全审计日志
    @PostMapping
    public Result<String> createSecurityAuditLog(@RequestBody SecurityAuditLog log) {
        securityAuditLogService.save(log);
        return Result.success("安全审计日志创建成功");
    }

    // 记录安全审计
    @PostMapping("/record")
    public Result<String> recordSecurityAudit(
            @RequestParam Long userId,
            @RequestParam String targetEmpId,
            @RequestParam String operationType,
            @RequestParam String tableName,
            @RequestParam String recordId,
            @RequestParam(required = false) String oldValues,
            @RequestParam(required = false) String newValues,
            @RequestParam String ipAddress,
            @RequestParam String userAgent,
            @RequestParam boolean isSelfOperation,
            @RequestParam boolean securityCheckPassed,
            @RequestParam(required = false) String checkFailedReason) {
        securityAuditLogService.recordSecurityAudit(userId, targetEmpId, operationType, tableName, recordId,
                oldValues, newValues, ipAddress, userAgent, isSelfOperation, securityCheckPassed, checkFailedReason);
        return Result.success("安全审计记录成功");
    }
}