package com.company.hr.mapper;

import com.company.hr.entity.SecurityAuditLog;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface SecurityAuditLogMapper {

    // 基础CRUD方法
    @Select("SELECT * FROM security_audit_log")
    List<SecurityAuditLog> findAll();

    @Select("SELECT * FROM security_audit_log WHERE audit_id = #{auditId}")
    SecurityAuditLog findById(Long auditId);

    @Insert("INSERT INTO security_audit_log(user_id, target_emp_id, operation_type, table_name, record_id, old_values, new_values, ip_address, user_agent, is_self_operation, security_check_passed, check_failed_reason) "
            +
            "VALUES(#{userId}, #{targetEmpId}, #{operationType}, #{tableName}, #{recordId}, #{oldValues}, #{newValues}, #{ipAddress}, #{userAgent}, #{isSelfOperation}, #{securityCheckPassed}, #{checkFailedReason})")
    void insert(SecurityAuditLog log);

    // 业务方法
    @Select("SELECT sal.*, u.real_name as user_real_name " +
            "FROM security_audit_log sal " +
            "LEFT JOIN system_user u ON sal.user_id = u.user_id " +
            "ORDER BY sal.operation_time DESC")
    List<SecurityAuditLog> findAllWithUser();
}