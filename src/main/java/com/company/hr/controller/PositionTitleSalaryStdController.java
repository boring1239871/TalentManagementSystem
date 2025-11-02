package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.PositionTitleSalaryStd;
import com.company.hr.service.PositionTitleSalaryStdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/position-title-salary-stds")
public class PositionTitleSalaryStdController extends BaseController {

    @Autowired
    private PositionTitleSalaryStdService positionTitleSalaryStdService;

    // 获取所有职位职称薪资标准
    @GetMapping
    public Result<List<PositionTitleSalaryStd>> getSalaryStdList() {
        List<PositionTitleSalaryStd> stds = positionTitleSalaryStdService.findAll();
        return Result.success(stds);
    }

    // 获取薪资标准详情
    @GetMapping("/{mapId}")
    public Result<PositionTitleSalaryStd> getSalaryStdDetail(@PathVariable Long mapId) {
        PositionTitleSalaryStd std = positionTitleSalaryStdService.findById(mapId);
        return Result.success(std);
    }

    // 获取当前职位职称对应的薪资标准
    @GetMapping("/current/{positionId}/{titleId}")
    public Result<PositionTitleSalaryStd> getCurrentSalaryStd(
            @PathVariable Long positionId, @PathVariable Long titleId) {
        PositionTitleSalaryStd std = positionTitleSalaryStdService.findCurrentByPositionAndTitle(positionId, titleId);
        return Result.success(std);
    }

    // 根据日期获取生效的薪资标准
    @GetMapping("/effective/{date}")
    public Result<List<PositionTitleSalaryStd>> getEffectiveSalaryStds(@PathVariable String date) {
        LocalDate effectiveDate = LocalDate.parse(date);
        List<PositionTitleSalaryStd> stds = positionTitleSalaryStdService.findEffectiveByDate(effectiveDate);
        return Result.success(stds);
    }

    // 创建职位职称薪资标准
    @PostMapping
    public Result<String> createSalaryStd(@RequestBody PositionTitleSalaryStd std) {
        positionTitleSalaryStdService.save(std);
        return Result.success("职位职称薪资标准创建成功");
    }

    // 更新职位职称薪资标准
    @PutMapping("/{mapId}")
    public Result<String> updateSalaryStd(@PathVariable Long mapId, @RequestBody PositionTitleSalaryStd std) {
        std.setMapId(mapId);
        positionTitleSalaryStdService.update(std);
        return Result.success("职位职称薪资标准更新成功");
    }

    // 禁用职位职称薪资标准
    @DeleteMapping("/{mapId}")
    public Result<String> disableSalaryStd(@PathVariable Long mapId) {
        positionTitleSalaryStdService.disable(mapId);
        return Result.success("职位职称薪资标准已禁用");
    }
}