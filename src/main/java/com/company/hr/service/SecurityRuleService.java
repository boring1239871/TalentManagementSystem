package com.company.hr.service;

import com.company.hr.entity.SecurityRule;
import java.util.List;

public interface SecurityRuleService {

    // 基础CRUD方法
    List<SecurityRule> findAll();

    SecurityRule findById(Long ruleId);

    void save(SecurityRule rule);

    void update(SecurityRule rule);

    // 业务方法
    List<SecurityRule> findAllActive();

    List<SecurityRule> findByRuleType(String ruleType);

    void disable(Long ruleId);

    boolean checkSecurityRule(String ruleType, Object... params);
}