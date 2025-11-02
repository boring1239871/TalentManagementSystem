package com.company.hr.service;

import com.company.hr.entity.Employee;
import java.util.List;

public interface EmployeeService {

    // 基础CRUD方法
    List<Employee> findAll();

    Employee findById(String empId);

    void save(Employee employee);

    void update(Employee employee);

    // 业务方法
    Employee findDetailById(String empId);

    List<Employee> findByOrgId(Long orgId);

    List<Employee> findByStatus(String status);

    int countActiveByOrgId(Long orgId);

    void reviewEmployee(String empId, Long reviewerId);

    void delete(String empId);

    void restore(String empId);
}