package com.company.hr.service;

import com.company.hr.entity.EmployeeChangeLog;
import java.util.List;

public interface EmployeeChangeLogService {

    // 基础CRUD方法
    List<EmployeeChangeLog> findAll();

    EmployeeChangeLog findById(Long logId);

    void save(EmployeeChangeLog log);

    // 业务方法
    List<EmployeeChangeLog> findByEmpId(String empId);

    List<EmployeeChangeLog> findAllWithDetails();

    void recordEmployeeChange(String empId, String changeType, String changedField, String oldValue, String newValue,
            String reason, Long operatorId);
}