package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.Employee;
import com.company.hr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController extends BaseController {

    @Autowired
    private EmployeeService employeeService;

    // 获取员工列表
    @GetMapping
    public Result<List<Employee>> getEmployeeList() {
        List<Employee> employees = employeeService.findAll();
        return Result.success(employees);
    }

    // 获取员工详情
    @GetMapping("/{empId}")
    public Result<Employee> getEmployeeDetail(@PathVariable String empId) {
        Employee employee = employeeService.findDetailById(empId);
        return Result.success(employee);
    }

    // 根据机构获取员工列表
    @GetMapping("/org/{orgId}")
    public Result<List<Employee>> getEmployeesByOrg(@PathVariable Long orgId) {
        List<Employee> employees = employeeService.findByOrgId(orgId);
        return Result.success(employees);
    }

    // 根据状态获取员工列表
    @GetMapping("/status/{status}")
    public Result<List<Employee>> getEmployeesByStatus(@PathVariable String status) {
        List<Employee> employees = employeeService.findByStatus(status);
        return Result.success(employees);
    }

    // 获取机构活跃员工数量
    @GetMapping("/org/{orgId}/count")
    public Result<Integer> countActiveByOrgId(@PathVariable Long orgId) {
        int count = employeeService.countActiveByOrgId(orgId);
        return Result.success(count);
    }

    // 创建员工
    @PostMapping
    public Result<String> createEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
        return Result.success("员工创建成功");
    }

    // 更新员工
    @PutMapping("/{empId}")
    public Result<String> updateEmployee(@PathVariable String empId, @RequestBody Employee employee) {
        employee.setEmpId(empId);
        employeeService.update(employee);
        return Result.success("员工信息更新成功");
    }

    // 复核员工档案
    @PostMapping("/{empId}/review")
    public Result<String> reviewEmployee(@PathVariable String empId, @RequestParam Long reviewerId) {
        employeeService.reviewEmployee(empId, reviewerId);
        return Result.success("员工档案复核通过");
    }

    // 删除员工（逻辑删除）
    @DeleteMapping("/{empId}")
    public Result<String> deleteEmployee(@PathVariable String empId) {
        employeeService.delete(empId);
        return Result.success("员工档案已删除");
    }

    // 恢复员工
    @PostMapping("/{empId}/restore")
    public Result<String> restoreEmployee(@PathVariable String empId) {
        employeeService.restore(empId);
        return Result.success("员工档案已恢复");
    }
}
