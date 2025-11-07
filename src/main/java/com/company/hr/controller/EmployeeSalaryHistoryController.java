package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.EmployeeSalaryHistory;
import com.company.hr.service.EmployeeSalaryHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/employee-salary-history")
public class EmployeeSalaryHistoryController extends BaseController {

    @Autowired
    private EmployeeSalaryHistoryService employeeSalaryHistoryService;

    // 获取所有员工薪资历史记录
    @GetMapping
    public Result<List<EmployeeSalaryHistory>> getEmployeeSalaryHistoryList() {
        List<EmployeeSalaryHistory> histories = employeeSalaryHistoryService.findAll();
        return Result.success(histories);
    }

    // 根据员工ID获取薪资历史记录
    @GetMapping("/employee/{empId}")
    public Result<List<EmployeeSalaryHistory>> getSalaryHistoriesByEmployeeId(@PathVariable String empId) {
        List<EmployeeSalaryHistory> histories = employeeSalaryHistoryService.findByEmpId(empId);
        return Result.success(histories);
    }

    // 根据员工ID和日期获取当前薪资记录
    @GetMapping("/current")
    public Result<EmployeeSalaryHistory> getCurrentSalaryByEmpIdAndDate(
            @RequestParam String empId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        EmployeeSalaryHistory history = employeeSalaryHistoryService.findCurrentByEmpIdAndDate(empId, date);
        return Result.success(history);
    }

    // 获取薪资历史记录详情
    @GetMapping("/{historyId}")
    public Result<EmployeeSalaryHistory> getEmployeeSalaryHistoryDetail(@PathVariable Long historyId) {
        EmployeeSalaryHistory history = employeeSalaryHistoryService.findById(historyId);
        return Result.success(history);
    }

    // 创建薪资历史记录
    @PostMapping
    public Result<String> createEmployeeSalaryHistory(@RequestBody EmployeeSalaryHistory history) {
        employeeSalaryHistoryService.save(history);
        return Result.success("薪资历史记录创建成功");
    }

    // 记录薪资变更
    @PostMapping("/record")
    public Result<String> recordSalaryChange(
            @RequestParam String empId,
            @RequestParam Long salaryStandardId,
            @RequestParam String salaryItems,
            @RequestParam Double totalAmount,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate effectiveDate,
            @RequestParam String reason,
            @RequestParam Long operatorId) {
        employeeSalaryHistoryService.recordSalaryChange(empId, salaryStandardId, salaryItems, totalAmount,
                effectiveDate, reason, operatorId);
        return Result.success("薪资变更记录成功");
    }
}