package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.EmployeeChangeLog;
import com.company.hr.service.EmployeeChangeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee-change-logs")
public class EmployeeChangeLogController extends BaseController {

    @Autowired
    private EmployeeChangeLogService employeeChangeLogService;

    // 获取所有员工变更记录
    @GetMapping
    public Result<List<EmployeeChangeLog>> getEmployeeChangeLogList() {
        List<EmployeeChangeLog> logs = employeeChangeLogService.findAll();
        return Result.success(logs);
    }

    // 获取包含详细信息的所有员工变更记录
    @GetMapping("/details")
    public Result<List<EmployeeChangeLog>> getEmployeeChangeLogsWithDetails() {
        List<EmployeeChangeLog> logs = employeeChangeLogService.findAllWithDetails();
        return Result.success(logs);
    }

    // 根据员工ID获取变更记录
    @GetMapping("/employee/{empId}")
    public Result<List<EmployeeChangeLog>> getChangeLogsByEmployeeId(@PathVariable String empId) {
        List<EmployeeChangeLog> logs = employeeChangeLogService.findByEmpId(empId);
        return Result.success(logs);
    }

    // 获取员工变更记录详情
    @GetMapping("/{logId}")
    public Result<EmployeeChangeLog> getEmployeeChangeLogDetail(@PathVariable Long logId) {
        EmployeeChangeLog log = employeeChangeLogService.findById(logId);
        return Result.success(log);
    }

    // 创建员工变更记录
    @PostMapping
    public Result<String> createEmployeeChangeLog(@RequestBody EmployeeChangeLog log) {
        employeeChangeLogService.save(log);
        return Result.success("员工变更记录创建成功");
    }

    // 记录员工变更
    @PostMapping("/record")
    public Result<String> recordEmployeeChange(
            @RequestParam String empId,
            @RequestParam String changeType,
            @RequestParam String changedField,
            @RequestParam String oldValue,
            @RequestParam String newValue,
            @RequestParam String reason,
            @RequestParam Long operatorId) {
        employeeChangeLogService.recordEmployeeChange(empId, changeType, changedField, oldValue, newValue, reason, operatorId);
        return Result.success("员工变更记录成功");
    }
}