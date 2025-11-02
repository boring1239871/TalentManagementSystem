package com.company.hr.service.impl;

import com.company.hr.entity.EmployeeSalaryHistory;
import com.company.hr.mapper.EmployeeSalaryHistoryMapper;
import com.company.hr.service.EmployeeSalaryHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeSalaryHistoryServiceImpl implements EmployeeSalaryHistoryService {

    @Autowired
    private EmployeeSalaryHistoryMapper employeeSalaryHistoryMapper;

    @Override
    public List<EmployeeSalaryHistory> findAll() {
        return employeeSalaryHistoryMapper.findAll();
    }

    @Override
    public EmployeeSalaryHistory findById(Long historyId) {
        return employeeSalaryHistoryMapper.findById(historyId);
    }

    @Override
    public void save(EmployeeSalaryHistory history) {
        history.setChangedTime(LocalDateTime.now());
        employeeSalaryHistoryMapper.insert(history);
    }

    @Override
    public List<EmployeeSalaryHistory> findByEmpId(String empId) {
        return employeeSalaryHistoryMapper.findByEmpId(empId);
    }

    @Override
    public EmployeeSalaryHistory findCurrentByEmpIdAndDate(String empId, LocalDate date) {
        return employeeSalaryHistoryMapper.findCurrentByEmpIdAndDate(empId, date);
    }

    @Override
    public void recordSalaryChange(String empId, Long salaryStandardId, String salaryItems, Double totalAmount,
            LocalDate effectiveDate, String reason, Long operatorId) {
        EmployeeSalaryHistory history = new EmployeeSalaryHistory();
        history.setEmpId(empId);
        history.setSalaryStandardId(salaryStandardId);
        history.setSalaryItems(salaryItems);
        history.setTotalAmount(totalAmount);
        history.setEffectiveDate(effectiveDate);
        history.setChangeReason(reason);
        history.setChangedBy(operatorId);
        save(history);
    }
}