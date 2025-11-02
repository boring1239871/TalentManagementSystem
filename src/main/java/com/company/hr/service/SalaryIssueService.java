package com.company.hr.service;

import com.company.hr.entity.SalaryIssue;
import java.util.List;

public interface SalaryIssueService {

    // 基础CRUD方法
    List<SalaryIssue> findAll();

    SalaryIssue findById(Long issueId);

    void save(SalaryIssue issue);

    void update(SalaryIssue issue);

    // 业务方法
    SalaryIssue findDetailById(Long issueId);

    List<SalaryIssue> findByStatus(String status);

    SalaryIssue findByOrgAndPeriod(Long orgId, Integer year, Integer month);

    void reviewSalaryIssue(Long issueId, Long reviewerId, String comment);

    void approveSalaryIssue(Long issueId, Long approverId);

    void markAsPaid(Long issueId, String paymentRef);
}