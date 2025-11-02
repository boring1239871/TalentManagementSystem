package com.company.hr.service;

import com.company.hr.entity.SalaryIssueAdjustment;
import java.util.List;

public interface SalaryIssueAdjustmentService {

    // 基础CRUD方法
    List<SalaryIssueAdjustment> findAll();

    SalaryIssueAdjustment findById(Long adjustmentId);

    void save(SalaryIssueAdjustment adjustment);

    // 业务方法
    List<SalaryIssueAdjustment> findByDetailId(Long detailId);

    List<SalaryIssueAdjustment> findByIssueId(Long issueId);

    void recordAdjustment(Long detailId, String adjustType, Double oldAmount, Double newAmount, String reason,
            Long operatorId);
}