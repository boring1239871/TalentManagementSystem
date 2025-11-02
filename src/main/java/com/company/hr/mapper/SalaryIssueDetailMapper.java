package com.company.hr.mapper;

import com.company.hr.entity.SalaryIssueDetail;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface SalaryIssueDetailMapper {

        // 基础CRUD方法
        @Select("SELECT * FROM salary_issue_detail")
        List<SalaryIssueDetail> findAll();

        @Select("SELECT * FROM salary_issue_detail WHERE detail_id = #{detailId}")
        SalaryIssueDetail findById(Long detailId);

        @Insert("INSERT INTO salary_issue_detail(issue_id, emp_id, salary_standard_id, standard_items, base_salary_amount, bonus_amount, deduction_amount, adjustment_reason, subtotal_amount, total_amount, status) "
                        +
                        "VALUES(#{issueId}, #{empId}, #{salaryStandardId}, #{standardItems}, #{baseSalaryAmount}, #{bonusAmount}, #{deductionAmount}, #{adjustmentReason}, #{subtotalAmount}, #{totalAmount}, #{status})")
        void insert(SalaryIssueDetail detail);

        @Update("UPDATE salary_issue_detail SET base_salary_amount=#{baseSalaryAmount}, bonus_amount=#{bonusAmount}, deduction_amount=#{deductionAmount}, adjustment_reason=#{adjustmentReason}, subtotal_amount=#{subtotalAmount}, total_amount=#{totalAmount}, status=#{status} WHERE detail_id=#{detailId}")
        void update(SalaryIssueDetail detail);

        // 业务方法
        @Select("SELECT sid.*, e.name as employee_name, e.employee_no, o.org_name, si.issue_code " +
                        "FROM salary_issue_detail sid " +
                        "LEFT JOIN employee e ON sid.emp_id = e.emp_id " +
                        "LEFT JOIN organization o ON e.org_id = o.org_id " +
                        "LEFT JOIN salary_issue si ON sid.issue_id = si.issue_id " +
                        "WHERE sid.issue_id = #{issueId} " +
                        "ORDER BY e.name")
        List<SalaryIssueDetail> findByIssueId(Long issueId);

        @Select("SELECT sid.*, e.name as employee_name, e.employee_no, o.org_name " +
                        "FROM salary_issue_detail sid " +
                        "LEFT JOIN employee e ON sid.emp_id = e.emp_id " +
                        "LEFT JOIN organization o ON e.org_id = o.org_id " +
                        "WHERE sid.detail_id = #{detailId}")
        SalaryIssueDetail findDetailById(Long detailId);

        @Select("SELECT COUNT(*) FROM salary_issue_detail WHERE issue_id = #{issueId} AND emp_id = #{empId}")
        int existsByIssueAndEmp(@Param("issueId") Long issueId, @Param("empId") String empId);
}