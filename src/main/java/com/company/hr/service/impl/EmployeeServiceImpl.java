package com.company.hr.service.impl;

import com.company.hr.entity.Employee;
import com.company.hr.mapper.EmployeeMapper;
import com.company.hr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<Employee> findAll() {
        return employeeMapper.findAll();
    }

    @Override
    public Employee findById(String empId) {
        return employeeMapper.findById(empId);
    }

    @Override
    public void save(Employee employee) {
        if (employee.getEmpId() == null) {
            String empId = "EMP" + System.currentTimeMillis();
            employee.setEmpId(empId);
        }
        employee.setStatus("pending_review");
        employee.setCreatedTime(LocalDateTime.now());
        employeeMapper.insert(employee);
    }

    @Override
    public void update(Employee employee) {
        employeeMapper.update(employee);
    }

    @Override
    public Employee findDetailById(String empId) {
        return employeeMapper.findDetailById(empId);
    }

    @Override
    public List<Employee> findByOrgId(Long orgId) {
        return employeeMapper.findByOrgId(orgId);
    }

    @Override
    public List<Employee> findByStatus(String status) {
        return employeeMapper.findByStatus(status);
    }

    @Override
    public int countActiveByOrgId(Long orgId) {
        return employeeMapper.countActiveByOrgId(orgId);
    }

    @Override
    public void reviewEmployee(String empId, Long reviewerId) {
        Employee employee = employeeMapper.findById(empId);
        if (employee != null && "pending_review".equals(employee.getStatus())) {
            employee.setStatus("active");
            employee.setReviewedBy(reviewerId);
            employee.setReviewedTime(LocalDateTime.now());
            employeeMapper.update(employee);
        }
    }

    @Override
    public void delete(String empId) {
        Employee employee = employeeMapper.findById(empId);
        if (employee != null) {
            employee.setStatus("deleted");
            employeeMapper.update(employee);
        }
    }

    @Override
    public void restore(String empId) {
        Employee employee = employeeMapper.findById(empId);
        if (employee != null && "deleted".equals(employee.getStatus())) {
            employee.setStatus("active");
            employeeMapper.update(employee);
        }
    }
}