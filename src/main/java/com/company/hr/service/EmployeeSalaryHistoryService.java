package com.company.hr.service;

import com.company.hr.entity.EmployeeSalaryHistory;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeSalaryHistoryService {

    // 基础CRUD方法
    List<EmployeeSalaryHistory> findAll();

    EmployeeSalaryHistory findById(Long historyId);

    void save(EmployeeSalaryHistory history);

    // 业务方法
    List<EmployeeSalaryHistory> findByEmpId(String empId);

    EmployeeSalaryHistory findCurrentByEmpIdAndDate(String empId, LocalDate date);

    void recordSalaryChange(String empId, Long salaryStandardId, String salaryItems, Double totalAmount,
            LocalDate effectiveDate, String reason, Long operatorId);
}