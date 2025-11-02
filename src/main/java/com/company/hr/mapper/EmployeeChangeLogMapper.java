package com.company.hr.mapper;

import com.company.hr.entity.EmployeeChangeLog;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface EmployeeChangeLogMapper {

        // 基础CRUD方法
        @Select("SELECT * FROM employee_change_log")
        List<EmployeeChangeLog> findAll();

        @Select("SELECT * FROM employee_change_log WHERE log_id = #{logId}")
        EmployeeChangeLog findById(Long logId);

        @Insert("INSERT INTO employee_change_log(emp_id, change_type, changed_field, old_value, new_value, change_reason, changed_by) "
                        +
                        "VALUES(#{empId}, #{changeType}, #{changedField}, #{oldValue}, #{newValue}, #{changeReason}, #{changedBy})")
        void insert(EmployeeChangeLog log);

        // 业务方法
        @Select("SELECT ecl.*, e.name as employee_name, u.real_name as changed_by_name " +
                        "FROM employee_change_log ecl " +
                        "LEFT JOIN employee e ON ecl.emp_id = e.emp_id " +
                        "LEFT JOIN system_user u ON ecl.changed_by = u.user_id " +
                        "WHERE ecl.emp_id = #{empId} " +
                        "ORDER BY ecl.changed_time DESC")
        List<EmployeeChangeLog> findByEmpId(String empId);

        @Select("SELECT ecl.*, e.name as employee_name, u.real_name as changed_by_name " +
                        "FROM employee_change_log ecl " +
                        "LEFT JOIN employee e ON ecl.emp_id = e.emp_id " +
                        "LEFT JOIN system_user u ON ecl.changed_by = u.user_id " +
                        "ORDER BY ecl.changed_time DESC")
        List<EmployeeChangeLog> findAllWithDetails();
}