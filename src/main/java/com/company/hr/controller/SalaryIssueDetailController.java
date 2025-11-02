package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.SalaryIssueDetail;
import com.company.hr.service.SalaryIssueDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary-issue-details")
public class SalaryIssueDetailController extends BaseController {

    @Autowired
    private SalaryIssueDetailService salaryIssueDetailService;

    // 获取所有薪资发放明细
    @GetMapping
    public Result<List<SalaryIssueDetail>> getSalaryIssueDetailList() {
        List<SalaryIssueDetail> details = salaryIssueDetailService.findAll();
        return Result.success(details);
    }

    // 根据发放记录ID获取薪资发放明细
    @GetMapping("/issue/{issueId}")
    public Result<List<SalaryIssueDetail>> getSalaryIssueDetailsByIssueId(@PathVariable Long issueId) {
        List<SalaryIssueDetail> details = salaryIssueDetailService.findByIssueId(issueId);
        return Result.success(details);
    }

    // 获取薪资发放明细详情
    @GetMapping("/{detailId}")
    public Result<SalaryIssueDetail> getSalaryIssueDetailById(@PathVariable Long detailId) {
        SalaryIssueDetail detail = salaryIssueDetailService.findById(detailId);
        return Result.success(detail);
    }

    // 获取薪资发放明细详细信息（包含关联数据）
    @GetMapping("/{detailId}/detail")
    public Result<SalaryIssueDetail> getSalaryIssueDetailWithDetail(@PathVariable Long detailId) {
        SalaryIssueDetail detail = salaryIssueDetailService.findDetailById(detailId);
        return Result.success(detail);
    }

    // 检查是否存在特定发放记录和员工的薪资明细
    @GetMapping("/exists")
    public Result<Boolean> existsByIssueAndEmp(
            @RequestParam Long issueId,
            @RequestParam String empId) {
        boolean exists = salaryIssueDetailService.existsByIssueAndEmp(issueId, empId);
        return Result.success(exists);
    }

    // 创建薪资发放明细
    @PostMapping
    public Result<String> createSalaryIssueDetail(@RequestBody SalaryIssueDetail detail) {
        salaryIssueDetailService.save(detail);
        return Result.success("薪资发放明细创建成功");
    }

    // 更新薪资发放明细
    @PutMapping("/{detailId}")
    public Result<String> updateSalaryIssueDetail(@PathVariable Long detailId, @RequestBody SalaryIssueDetail detail) {
        detail.setDetailId(detailId);
        salaryIssueDetailService.update(detail);
        return Result.success("薪资发放明细更新成功");
    }

    // 调整薪资发放明细
    @PostMapping("/{detailId}/adjust")
    public Result<String> adjustSalaryDetail(
            @PathVariable Long detailId,
            @RequestParam Double bonusAmount,
            @RequestParam Double deductionAmount,
            @RequestParam String reason,
            @RequestParam Long operatorId) {
        salaryIssueDetailService.adjustSalaryDetail(detailId, bonusAmount, deductionAmount, reason, operatorId);
        return Result.success("薪资发放明细调整成功");
    }
}