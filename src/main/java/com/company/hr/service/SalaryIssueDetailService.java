package com.company.hr.service;

import com.company.hr.entity.SalaryIssueDetail;
import java.util.List;

public interface SalaryIssueDetailService {

    // 基础CRUD方法
    List<SalaryIssueDetail> findAll();

    SalaryIssueDetail findById(Long detailId);

    void save(SalaryIssueDetail detail);

    void update(SalaryIssueDetail detail);

    // 业务方法
    List<SalaryIssueDetail> findByIssueId(Long issueId);

    SalaryIssueDetail findDetailById(Long detailId);

    boolean existsByIssueAndEmp(Long issueId, String empId);

    void adjustSalaryDetail(Long detailId, Double bonusAmount, Double deductionAmount, String reason, Long operatorId);
}