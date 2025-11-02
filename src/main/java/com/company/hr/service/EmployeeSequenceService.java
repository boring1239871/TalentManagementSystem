package com.company.hr.service;

import com.company.hr.entity.EmployeeSequence;

public interface EmployeeSequenceService {

    // 基础CRUD方法
    EmployeeSequence findByOrgAndYear(Long orgId, Integer year);

    void save(EmployeeSequence sequence);

    void update(EmployeeSequence sequence);

    // 业务方法
    String generateEmployeeId(Long orgId);

    int getNextSequence(Long orgId, Integer year);
}