package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.SalaryIssue;
import com.company.hr.service.SalaryIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary-issues")
public class SalaryIssueController extends BaseController {

    @Autowired
    private SalaryIssueService salaryIssueService;

    // 获取所有薪资发放记录
    @GetMapping
    public Result<List<SalaryIssue>> getSalaryIssueList() {
        List<SalaryIssue> issues = salaryIssueService.findAll();
        return Result.success(issues);
    }

    // 根据状态获取薪资发放记录
    @GetMapping("/status/{status}")
    public Result<List<SalaryIssue>> getSalaryIssuesByStatus(@PathVariable String status) {
        List<SalaryIssue> issues = salaryIssueService.findByStatus(status);
        return Result.success(issues);
    }

    // 获取薪资发放详情
    @GetMapping("/{issueId}")
    public Result<SalaryIssue> getSalaryIssueDetail(@PathVariable Long issueId) {
        SalaryIssue issue = salaryIssueService.findById(issueId);
        return Result.success(issue);
    }

    // 获取薪资发放详细信息（包含关联数据）
    @GetMapping("/{issueId}/detail")
    public Result<SalaryIssue> getSalaryIssueWithDetail(@PathVariable Long issueId) {
        SalaryIssue issue = salaryIssueService.findDetailById(issueId);
        return Result.success(issue);
    }

    // 根据机构和期间获取薪资发放记录
    @GetMapping("/org/{orgId}/period")
    public Result<SalaryIssue> getSalaryIssueByPeriod(
            @PathVariable Long orgId,
            @RequestParam Integer year,
            @RequestParam Integer month) {
        SalaryIssue issue = salaryIssueService.findByOrgAndPeriod(orgId, year, month);
        return Result.success(issue);
    }

    // 创建薪资发放记录
    @PostMapping
    public Result<String> createSalaryIssue(@RequestBody SalaryIssue issue) {
        salaryIssueService.save(issue);
        return Result.success("薪资发放记录创建成功");
    }

    // 更新薪资发放记录
    @PutMapping("/{issueId}")
    public Result<String> updateSalaryIssue(@PathVariable Long issueId, @RequestBody SalaryIssue issue) {
        issue.setIssueId(issueId);
        salaryIssueService.update(issue);
        return Result.success("薪资发放记录更新成功");
    }

    // 审核薪资发放记录
    @PostMapping("/{issueId}/review")
    public Result<String> reviewSalaryIssue(
            @PathVariable Long issueId,
            @RequestParam Long reviewerId,
            @RequestParam(required = false) String comment) {
        salaryIssueService.reviewSalaryIssue(issueId, reviewerId, comment);
        return Result.success("薪资发放记录审核成功");
    }

    // 审批薪资发放记录
    @PostMapping("/{issueId}/approve")
    public Result<String> approveSalaryIssue(
            @PathVariable Long issueId,
            @RequestParam Long approverId) {
        salaryIssueService.approveSalaryIssue(issueId, approverId);
        return Result.success("薪资发放记录审批成功");
    }

    // 标记薪资发放为已支付
    @PostMapping("/{issueId}/paid")
    public Result<String> markSalaryIssueAsPaid(
            @PathVariable Long issueId,
            @RequestParam String paymentRef) {
        salaryIssueService.markAsPaid(issueId, paymentRef);
        return Result.success("薪资发放记录已标记为已支付");
    }
}