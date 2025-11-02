package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.SalaryIssueAdjustment;
import com.company.hr.service.SalaryIssueAdjustmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary-adjustments")
public class SalaryIssueAdjustmentController extends BaseController {

    @Autowired
    private SalaryIssueAdjustmentService salaryIssueAdjustmentService;

    // 获取所有薪资调整记录
    @GetMapping
    public Result<List<SalaryIssueAdjustment>> getSalaryAdjustmentList() {
        List<SalaryIssueAdjustment> adjustments = salaryIssueAdjustmentService.findAll();
        return Result.success(adjustments);
    }

    // 根据薪资明细ID获取调整记录
    @GetMapping("/detail/{detailId}")
    public Result<List<SalaryIssueAdjustment>> getAdjustmentsByDetailId(@PathVariable Long detailId) {
        List<SalaryIssueAdjustment> adjustments = salaryIssueAdjustmentService.findByDetailId(detailId);
        return Result.success(adjustments);
    }

    // 根据发放记录ID获取调整记录
    @GetMapping("/issue/{issueId}")
    public Result<List<SalaryIssueAdjustment>> getAdjustmentsByIssueId(@PathVariable Long issueId) {
        List<SalaryIssueAdjustment> adjustments = salaryIssueAdjustmentService.findByIssueId(issueId);
        return Result.success(adjustments);
    }

    // 获取薪资调整记录详情
    @GetMapping("/{adjustmentId}")
    public Result<SalaryIssueAdjustment> getSalaryAdjustmentDetail(@PathVariable Long adjustmentId) {
        SalaryIssueAdjustment adjustment = salaryIssueAdjustmentService.findById(adjustmentId);
        return Result.success(adjustment);
    }

    // 创建薪资调整记录
    @PostMapping
    public Result<String> createSalaryAdjustment(@RequestBody SalaryIssueAdjustment adjustment) {
        salaryIssueAdjustmentService.save(adjustment);
        return Result.success("薪资调整记录创建成功");
    }

    // 记录薪资调整
    @PostMapping("/record")
    public Result<String> recordSalaryAdjustment(
            @RequestParam Long detailId,
            @RequestParam String adjustType,
            @RequestParam Double oldAmount,
            @RequestParam Double newAmount,
            @RequestParam String reason,
            @RequestParam Long operatorId) {
        salaryIssueAdjustmentService.recordAdjustment(detailId, adjustType, oldAmount, newAmount, reason, operatorId);
        return Result.success("薪资调整记录成功");
    }
}