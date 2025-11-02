package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.SecurityRule;
import com.company.hr.service.SecurityRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security-rules")
public class SecurityRuleController extends BaseController {

    @Autowired
    private SecurityRuleService securityRuleService;

    // 获取所有安全规则
    @GetMapping
    public Result<List<SecurityRule>> getSecurityRuleList() {
        List<SecurityRule> rules = securityRuleService.findAll();
        return Result.success(rules);
    }

    // 获取所有活跃的安全规则
    @GetMapping("/active")
    public Result<List<SecurityRule>> getActiveSecurityRules() {
        List<SecurityRule> rules = securityRuleService.findAllActive();
        return Result.success(rules);
    }

    // 根据规则类型获取安全规则
    @GetMapping("/type/{ruleType}")
    public Result<List<SecurityRule>> getSecurityRulesByType(@PathVariable String ruleType) {
        List<SecurityRule> rules = securityRuleService.findByRuleType(ruleType);
        return Result.success(rules);
    }

    // 获取安全规则详情
    @GetMapping("/{ruleId}")
    public Result<SecurityRule> getSecurityRuleDetail(@PathVariable Long ruleId) {
        SecurityRule rule = securityRuleService.findById(ruleId);
        return Result.success(rule);
    }

    // 创建安全规则
    @PostMapping
    public Result<String> createSecurityRule(@RequestBody SecurityRule rule) {
        securityRuleService.save(rule);
        return Result.success("安全规则创建成功");
    }

    // 更新安全规则
    @PutMapping("/{ruleId}")
    public Result<String> updateSecurityRule(@PathVariable Long ruleId, @RequestBody SecurityRule rule) {
        rule.setRuleId(ruleId);
        securityRuleService.update(rule);
        return Result.success("安全规则更新成功");
    }

    // 禁用安全规则
    @DeleteMapping("/{ruleId}")
    public Result<String> disableSecurityRule(@PathVariable Long ruleId) {
        securityRuleService.disable(ruleId);
        return Result.success("安全规则已禁用");
    }

    // 检查安全规则
    @PostMapping("/check")
    public Result<Boolean> checkSecurityRule(
            @RequestParam String ruleType,
            @RequestParam(required = false) Object[] params) {
        boolean passed = securityRuleService.checkSecurityRule(ruleType, params);
        return Result.success(passed);
    }
}