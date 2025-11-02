package com.company.hr.mapper;

import com.company.hr.entity.SalaryIssueAdjustment;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface SalaryIssueAdjustmentMapper {

        // 基础CRUD方法
        @Select("SELECT * FROM salary_issue_adjustment")
        List<SalaryIssueAdjustment> findAll();

        @Select("SELECT * FROM salary_issue_adjustment WHERE adjustment_id = #{adjustmentId}")
        SalaryIssueAdjustment findById(Long adjustmentId);

        @Insert("INSERT INTO salary_issue_adjustment(detail_id, adjust_type, old_amount, new_amount, adjust_reason, adjusted_by) "
                        +
                        "VALUES(#{detailId}, #{adjustType}, #{oldAmount}, #{newAmount}, #{adjustReason}, #{adjustedBy})")
        void insert(SalaryIssueAdjustment adjustment);

        // 业务方法
        @Select("SELECT sia.*, u.real_name as adjusted_by_name, e.name as employee_name " +
                        "FROM salary_issue_adjustment sia " +
                        "LEFT JOIN system_user u ON sia.adjusted_by = u.user_id " +
                        "LEFT JOIN salary_issue_detail sid ON sia.detail_id = sid.detail_id " +
                        "LEFT JOIN employee e ON sid.emp_id = e.emp_id " +
                        "WHERE sia.detail_id = #{detailId} " +
                        "ORDER BY sia.adjusted_time DESC")
        List<SalaryIssueAdjustment> findByDetailId(Long detailId);

        @Select("SELECT sia.*, u.real_name as adjusted_by_name, e.name as employee_name, sid.issue_id " +
                        "FROM salary_issue_adjustment sia " +
                        "LEFT JOIN system_user u ON sia.adjusted_by = u.user_id " +
                        "LEFT JOIN salary_issue_detail sid ON sia.detail_id = sid.detail_id " +
                        "LEFT JOIN employee e ON sid.emp_id = e.emp_id " +
                        "WHERE sid.issue_id = #{issueId} " +
                        "ORDER BY sia.adjusted_time DESC")
        List<SalaryIssueAdjustment> findByIssueId(Long issueId);
}