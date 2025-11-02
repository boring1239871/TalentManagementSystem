package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.EmployeeSequence;
import com.company.hr.service.EmployeeSequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee-sequences")
public class EmployeeSequenceController extends BaseController {

    @Autowired
    private EmployeeSequenceService employeeSequenceService;

    // 根据机构ID和年份获取员工序列
    @GetMapping("/org/{orgId}/year/{year}")
    public Result<EmployeeSequence> getEmployeeSequenceByOrgAndYear(
            @PathVariable Long orgId,
            @PathVariable Integer year) {
        EmployeeSequence sequence = employeeSequenceService.findByOrgAndYear(orgId, year);
        return Result.success(sequence);
    }

    // 创建员工序列
    @PostMapping
    public Result<String> createEmployeeSequence(@RequestBody EmployeeSequence sequence) {
        employeeSequenceService.save(sequence);
        return Result.success("员工序列创建成功");
    }

    // 更新员工序列
    @PutMapping
    public Result<String> updateEmployeeSequence(@RequestBody EmployeeSequence sequence) {
        employeeSequenceService.update(sequence);
        return Result.success("员工序列更新成功");
    }

    // 生成员工ID
    @GetMapping("/generate-id/org/{orgId}")
    public Result<String> generateEmployeeId(@PathVariable Long orgId) {
        String employeeId = employeeSequenceService.generateEmployeeId(orgId);
        return Result.success(employeeId);
    }

    // 获取下一个序列值
    @GetMapping("/next-sequence")
    public Result<Integer> getNextSequence(
            @RequestParam Long orgId,
            @RequestParam Integer year) {
        int nextSequence = employeeSequenceService.getNextSequence(orgId, year);
        return Result.success(nextSequence);
    }
}