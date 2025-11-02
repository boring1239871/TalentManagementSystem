package com.company.hr.service.impl;

import com.company.hr.entity.SecurityRule;
import com.company.hr.mapper.SecurityRuleMapper;
import com.company.hr.service.SecurityRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SecurityRuleServiceImpl implements SecurityRuleService {

    @Autowired
    private SecurityRuleMapper securityRuleMapper;

    @Override
    public List<SecurityRule> findAll() {
        return securityRuleMapper.findAll();
    }

    @Override
    public SecurityRule findById(Long ruleId) {
        return securityRuleMapper.findById(ruleId);
    }

    @Override
    public void save(SecurityRule rule) {
        securityRuleMapper.insert(rule);
    }

    @Override
    public void update(SecurityRule rule) {
        securityRuleMapper.update(rule);
    }

    @Override
    public List<SecurityRule> findAllActive() {
        return securityRuleMapper.findAllActive();
    }

    @Override
    public List<SecurityRule> findByRuleType(String ruleType) {
        return securityRuleMapper.findByRuleType(ruleType);
    }

    @Override
    public void disable(Long ruleId) {
        SecurityRule rule = securityRuleMapper.findById(ruleId);
        if (rule != null) {
            rule.setStatus("inactive");
            securityRuleMapper.update(rule);
        }
    }

    @Override
    public boolean checkSecurityRule(String ruleType, Object... params) {
        // 简化实现，实际应该解析rule_condition并验证
        List<SecurityRule> rules = securityRuleMapper.findByRuleType(ruleType);
        return !rules.isEmpty(); // 如果有对应规则就返回true
    }
}