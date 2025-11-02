package com.company.hr.service.impl;

import com.company.hr.entity.EmployeeChangeLog;
import com.company.hr.mapper.EmployeeChangeLogMapper;
import com.company.hr.service.EmployeeChangeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeChangeLogServiceImpl implements EmployeeChangeLogService {

    @Autowired
    private EmployeeChangeLogMapper employeeChangeLogMapper;

    @Override
    public List<EmployeeChangeLog> findAll() {
        return employeeChangeLogMapper.findAll();
    }

    @Override
    public EmployeeChangeLog findById(Long logId) {
        return employeeChangeLogMapper.findById(logId);
    }

    @Override
    public void save(EmployeeChangeLog log) {
        log.setChangedTime(LocalDateTime.now());
        employeeChangeLogMapper.insert(log);
    }

    @Override
    public List<EmployeeChangeLog> findByEmpId(String empId) {
        return employeeChangeLogMapper.findByEmpId(empId);
    }

    @Override
    public List<EmployeeChangeLog> findAllWithDetails() {
        return employeeChangeLogMapper.findAllWithDetails();
    }

    @Override
    public void recordEmployeeChange(String empId, String changeType, String changedField, String oldValue,
            String newValue, String reason, Long operatorId) {
        EmployeeChangeLog log = new EmployeeChangeLog();
        log.setEmpId(empId);
        log.setChangeType(changeType);
        log.setChangedField(changedField);
        log.setOldValue(oldValue);
        log.setNewValue(newValue);
        log.setChangeReason(reason);
        log.setChangedBy(operatorId);
        save(log);
    }
}