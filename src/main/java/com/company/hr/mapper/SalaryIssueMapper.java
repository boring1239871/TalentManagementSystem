package com.company.hr.mapper;

import com.company.hr.entity.SalaryIssue;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface SalaryIssueMapper {

        // 基础CRUD方法
        @Select("SELECT * FROM salary_issue")
        List<SalaryIssue> findAll();

        @Select("SELECT * FROM salary_issue WHERE issue_id = #{issueId}")
        SalaryIssue findById(Long issueId);

        @Insert("INSERT INTO salary_issue(issue_code, issue_year, issue_month, issue_date, org_id, org_level, total_base_amount, total_bonus_amount, total_deduction_amount, total_amount, total_people, status, created_by) "
                        +
                        "VALUES(#{issueCode}, #{issueYear}, #{issueMonth}, #{issueDate}, #{orgId}, #{orgLevel}, #{totalBaseAmount}, #{totalBonusAmount}, #{totalDeductionAmount}, #{totalAmount}, #{totalPeople}, #{status}, #{createdBy})")
        void insert(SalaryIssue issue);

        @Update("UPDATE salary_issue SET total_base_amount=#{totalBaseAmount}, total_bonus_amount=#{totalBonusAmount}, total_deduction_amount=#{totalDeductionAmount}, total_amount=#{totalAmount}, total_people=#{totalPeople}, status=#{status} WHERE issue_id=#{issueId}")
        void update(SalaryIssue issue);

        // 业务方法
        @Select("SELECT si.*, o.org_name, creator.real_name as created_by_name, reviewer.real_name as reviewed_by_name "
                        +
                        "FROM salary_issue si " +
                        "LEFT JOIN organization o ON si.org_id = o.org_id " +
                        "LEFT JOIN system_user creator ON si.created_by = creator.user_id " +
                        "LEFT JOIN system_user reviewer ON si.reviewed_by = reviewer.user_id " +
                        "WHERE si.issue_id = #{issueId}")
        SalaryIssue findDetailById(Long issueId);

        @Select("SELECT si.*, o.org_name FROM salary_issue si " +
                        "LEFT JOIN organization o ON si.org_id = o.org_id " +
                        "WHERE si.status = #{status}")
        List<SalaryIssue> findByStatus(String status);

        @Select("SELECT si.*, o.org_name, creator.real_name as created_by_name " +
                        "FROM salary_issue si " +
                        "LEFT JOIN organization o ON si.org_id = o.org_id " +
                        "LEFT JOIN system_user creator ON si.created_by = creator.user_id " +
                        "WHERE si.org_id = #{orgId} AND si.issue_year = #{year} AND si.issue_month = #{month} " +
                        "AND si.status != 'rejected'")
        SalaryIssue findByOrgAndPeriod(@Param("orgId") Long orgId, @Param("year") Integer year,
                        @Param("month") Integer month);
}