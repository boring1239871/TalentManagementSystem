package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.SalaryStandard;
import com.company.hr.service.SalaryStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary-standards")
public class SalaryStandardController extends BaseController {

    @Autowired
    private SalaryStandardService salaryStandardService;

    // 获取所有薪资标准
    @GetMapping
    public Result<List<SalaryStandard>> getSalaryStandardList() {
        List<SalaryStandard> standards = salaryStandardService.findAll();
        return Result.success(standards);
    }

    // 获取所有已审核的薪资标准
    @GetMapping("/approved")
    public Result<List<SalaryStandard>> getApprovedSalaryStandards() {
        List<SalaryStandard> standards = salaryStandardService.findAllApproved();
        return Result.success(standards);
    }

    // 根据职位获取已审核的薪资标准
    @GetMapping("/approved/position/{positionId}")
    public Result<List<SalaryStandard>> getApprovedByPosition(@PathVariable Long positionId) {
        List<SalaryStandard> standards = salaryStandardService.findApprovedByPositionId(positionId);
        return Result.success(standards);
    }

    // 获取薪资标准详情
    @GetMapping("/{stdId}")
    public Result<SalaryStandard> getSalaryStandardDetail(@PathVariable Long stdId) {
        SalaryStandard standard = salaryStandardService.findById(stdId);
        return Result.success(standard);
    }

    // 获取薪资标准详细信息（包含关联数据）
    @GetMapping("/{stdId}/detail")
    public Result<SalaryStandard> getSalaryStandardWithDetail(@PathVariable Long stdId) {
        SalaryStandard standard = salaryStandardService.findDetailById(stdId);
        return Result.success(standard);
    }

    // 创建薪资标准
    @PostMapping
    public Result<String> createSalaryStandard(@RequestBody SalaryStandard standard) {
        salaryStandardService.save(standard);
        return Result.success("薪资标准创建成功");
    }

    // 更新薪资标准
    @PutMapping("/{stdId}")
    public Result<String> updateSalaryStandard(@PathVariable Long stdId, @RequestBody SalaryStandard standard) {
        standard.setStdId(stdId);
        salaryStandardService.update(standard);
        return Result.success("薪资标准更新成功");
    }

    // 审核薪资标准
    @PostMapping("/{stdId}/review")
    public Result<String> reviewSalaryStandard(
            @PathVariable Long stdId, 
            @RequestParam Long reviewerId, 
            @RequestParam(required = false) String comment) {
        salaryStandardService.reviewSalaryStandard(stdId, reviewerId, comment);
        return Result.success("薪资标准审核成功");
    }

    // 禁用薪资标准
    @DeleteMapping("/{stdId}")
    public Result<String> disableSalaryStandard(@PathVariable Long stdId) {
        salaryStandardService.disable(stdId);
        return Result.success("薪资标准已禁用");
    }
}