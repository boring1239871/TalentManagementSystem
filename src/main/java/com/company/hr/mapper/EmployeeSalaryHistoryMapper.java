package com.company.hr.mapper;

import com.company.hr.entity.EmployeeSalaryHistory;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface EmployeeSalaryHistoryMapper {

        // 基础CRUD方法
        @Select("SELECT * FROM employee_salary_history")
        List<EmployeeSalaryHistory> findAll();

        @Select("SELECT * FROM employee_salary_history WHERE history_id = #{historyId}")
        EmployeeSalaryHistory findById(Long historyId);

        @Insert("INSERT INTO employee_salary_history(emp_id, salary_standard_id, salary_items, total_amount, effective_date, expiration_date, change_reason, changed_by) "
                        +
                        "VALUES(#{empId}, #{salaryStandardId}, #{salaryItems}, #{totalAmount}, #{effectiveDate}, #{expirationDate}, #{changeReason}, #{changedBy})")
        void insert(EmployeeSalaryHistory history);

        // 业务方法
        @Select("SELECT esh.*, e.name as employee_name, ss.std_name " +
                        "FROM employee_salary_history esh " +
                        "LEFT JOIN employee e ON esh.emp_id = e.emp_id " +
                        "LEFT JOIN salary_standard ss ON esh.salary_standard_id = ss.std_id " +
                        "WHERE esh.emp_id = #{empId} " +
                        "ORDER BY esh.effective_date DESC, esh.changed_time DESC")
        List<EmployeeSalaryHistory> findByEmpId(String empId);

        @Select("SELECT * FROM employee_salary_history " +
                        "WHERE emp_id = #{empId} AND effective_date <= #{date} " +
                        "AND (expiration_date IS NULL OR expiration_date >= #{date}) " +
                        "ORDER BY effective_date DESC " +
                        "LIMIT 1")
        EmployeeSalaryHistory findCurrentByEmpIdAndDate(@Param("empId") String empId,
                        @Param("date") java.time.LocalDate date);
}