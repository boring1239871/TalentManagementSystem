package com.company.hr.mapper;

import com.company.hr.entity.SecurityRule;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface SecurityRuleMapper {

    // 基础CRUD方法
    @Select("SELECT * FROM security_rule")
    List<SecurityRule> findAll();

    @Select("SELECT * FROM security_rule WHERE rule_id = #{ruleId}")
    SecurityRule findById(Long ruleId);

    @Insert("INSERT INTO security_rule(rule_name, rule_type, rule_condition, error_message, status, created_by) " +
            "VALUES(#{ruleName}, #{ruleType}, #{ruleCondition}, #{errorMessage}, #{status}, #{createdBy})")
    void insert(SecurityRule rule);

    @Update("UPDATE security_rule SET rule_name=#{ruleName}, rule_type=#{ruleType}, rule_condition=#{ruleCondition}, error_message=#{errorMessage}, status=#{status} WHERE rule_id=#{ruleId}")
    void update(SecurityRule rule);

    // 业务方法
    @Select("SELECT * FROM security_rule WHERE status = 'active' ORDER BY rule_id")
    List<SecurityRule> findAllActive();

    @Select("SELECT * FROM security_rule WHERE rule_type = #{ruleType} AND status = 'active'")
    List<SecurityRule> findByRuleType(String ruleType);
}