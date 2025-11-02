package com.company.hr.mapper;

import com.company.hr.entity.Employee;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface EmployeeMapper {

        // 基础CRUD方法
        @Select("SELECT * FROM employee")
        List<Employee> findAll();

        @Select("SELECT * FROM employee WHERE emp_id = #{empId}")
        Employee findById(String empId);

        @Insert("INSERT INTO employee(emp_id, employee_no, name, gender, id_card, email, phone, mobile, org_id, position_id, title_id, salary_standard_id, status, created_by) "
                        +
                        "VALUES(#{empId}, #{employeeNo}, #{name}, #{gender}, #{idCard}, #{email}, #{phone}, #{mobile}, #{orgId}, #{positionId}, #{titleId}, #{salaryStandardId}, #{status}, #{createdBy})")
        void insert(Employee employee);

        @Update("UPDATE employee SET name=#{name}, gender=#{gender}, email=#{email}, phone=#{phone}, mobile=#{mobile}, org_id=#{orgId}, position_id=#{positionId}, title_id=#{titleId}, salary_standard_id=#{salaryStandardId}, status=#{status} WHERE emp_id=#{empId}")
        void update(Employee employee);

        // 业务方法
        @Select("SELECT e.*, o.org_name, p.position_name, t.title_name " +
                        "FROM employee e " +
                        "LEFT JOIN organization o ON e.org_id = o.org_id " +
                        "LEFT JOIN position p ON e.position_id = p.position_id " +
                        "LEFT JOIN professional_title t ON e.title_id = t.title_id " +
                        "WHERE e.emp_id = #{empId}")
        Employee findDetailById(String empId);

        @Select("SELECT e.*, o.org_name, p.position_name " +
                        "FROM employee e " +
                        "JOIN organization o ON e.org_id = o.org_id " +
                        "JOIN position p ON e.position_id = p.position_id " +
                        "WHERE e.org_id = #{orgId} AND e.status = 'active'")
        List<Employee> findByOrgId(Long orgId);

        @Select("SELECT COUNT(*) FROM employee WHERE org_id = #{orgId} AND status = 'active'")
        int countActiveByOrgId(Long orgId);

        @Select("SELECT * FROM employee WHERE status = #{status}")
        List<Employee> findByStatus(String status);
}