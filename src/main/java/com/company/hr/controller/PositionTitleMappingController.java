package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.PositionTitleMapping;
import com.company.hr.service.PositionTitleMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position-title-mappings")
public class PositionTitleMappingController extends BaseController {

    @Autowired
    private PositionTitleMappingService positionTitleMappingService;

    // 获取所有职位职称映射关系
    @GetMapping
    public Result<List<PositionTitleMapping>> getMappingList() {
        List<PositionTitleMapping> mappings = positionTitleMappingService.findAll();
        return Result.success(mappings);
    }

    // 根据职位ID获取映射关系
    @GetMapping("/position/{positionId}")
    public Result<List<PositionTitleMapping>> getMappingsByPosition(@PathVariable Long positionId) {
        List<PositionTitleMapping> mappings = positionTitleMappingService.findByPositionId(positionId);
        return Result.success(mappings);
    }

    // 根据职称ID获取映射关系
    @GetMapping("/title/{titleId}")
    public Result<List<PositionTitleMapping>> getMappingsByTitle(@PathVariable Long titleId) {
        List<PositionTitleMapping> mappings = positionTitleMappingService.findByTitleId(titleId);
        return Result.success(mappings);
    }

    // 根据职位和职称获取特定映射
    @GetMapping("/position/{positionId}/title/{titleId}")
    public Result<PositionTitleMapping> getMappingByPositionAndTitle(
            @PathVariable Long positionId, @PathVariable Long titleId) {
        PositionTitleMapping mapping = positionTitleMappingService.findByPositionAndTitle(positionId, titleId);
        return Result.success(mapping);
    }

    // 获取映射详情
    @GetMapping("/{mappingId}")
    public Result<PositionTitleMapping> getMappingDetail(@PathVariable Long mappingId) {
        PositionTitleMapping mapping = positionTitleMappingService.findById(mappingId);
        return Result.success(mapping);
    }

    // 创建职位职称映射
    @PostMapping
    public Result<String> createMapping(@RequestBody PositionTitleMapping mapping) {
        positionTitleMappingService.save(mapping);
        return Result.success("职位职称映射创建成功");
    }

    // 更新职位职称映射
    @PutMapping("/{mappingId}")
    public Result<String> updateMapping(@PathVariable Long mappingId, @RequestBody PositionTitleMapping mapping) {
        mapping.setMappingId(mappingId);
        positionTitleMappingService.update(mapping);
        return Result.success("职位职称映射更新成功");
    }

    // 禁用职位职称映射
    @DeleteMapping("/{mappingId}")
    public Result<String> disableMapping(@PathVariable Long mappingId) {
        positionTitleMappingService.disable(mappingId);
        return Result.success("职位职称映射已禁用");
    }
}