package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.SalaryItem;
import com.company.hr.service.SalaryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary-items")
public class SalaryItemController extends BaseController {

    @Autowired
    private SalaryItemService salaryItemService;

    // 获取所有薪资项目
    @GetMapping
    public Result<List<SalaryItem>> getSalaryItemList() {
        List<SalaryItem> items = salaryItemService.findAll();
        return Result.success(items);
    }

    // 获取所有活跃的薪资项目
    @GetMapping("/active")
    public Result<List<SalaryItem>> getActiveSalaryItems() {
        List<SalaryItem> items = salaryItemService.findAllActive();
        return Result.success(items);
    }

    // 根据项目类型获取薪资项目
    @GetMapping("/type/{itemType}")
    public Result<List<SalaryItem>> getSalaryItemsByType(@PathVariable String itemType) {
        List<SalaryItem> items = salaryItemService.findByItemType(itemType);
        return Result.success(items);
    }

    // 根据是否系统项目获取薪资项目
    @GetMapping("/system/{isSystemItem}")
    public Result<List<SalaryItem>> getSalaryItemsBySystem(@PathVariable Boolean isSystemItem) {
        List<SalaryItem> items = salaryItemService.findBySystemItem(isSystemItem);
        return Result.success(items);
    }

    // 获取薪资项目详情
    @GetMapping("/{itemId}")
    public Result<SalaryItem> getSalaryItemDetail(@PathVariable Long itemId) {
        SalaryItem item = salaryItemService.findById(itemId);
        return Result.success(item);
    }

    // 创建薪资项目
    @PostMapping
    public Result<String> createSalaryItem(@RequestBody SalaryItem item) {
        salaryItemService.save(item);
        return Result.success("薪资项目创建成功");
    }

    // 更新薪资项目
    @PutMapping("/{itemId}")
    public Result<String> updateSalaryItem(@PathVariable Long itemId, @RequestBody SalaryItem item) {
        item.setItemId(itemId);
        salaryItemService.update(item);
        return Result.success("薪资项目更新成功");
    }

    // 禁用薪资项目
    @DeleteMapping("/{itemId}")
    public Result<String> disableSalaryItem(@PathVariable Long itemId) {
        salaryItemService.disable(itemId);
        return Result.success("薪资项目已禁用");
    }
}