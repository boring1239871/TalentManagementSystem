package com.company.hr.service.impl;

import com.company.hr.entity.SalaryIssueAdjustment;
import com.company.hr.mapper.SalaryIssueAdjustmentMapper;
import com.company.hr.service.SalaryIssueAdjustmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalaryIssueAdjustmentServiceImpl implements SalaryIssueAdjustmentService {

    @Autowired
    private SalaryIssueAdjustmentMapper salaryIssueAdjustmentMapper;

    @Override
    public List<SalaryIssueAdjustment> findAll() {
        return salaryIssueAdjustmentMapper.findAll();
    }

    @Override
    public SalaryIssueAdjustment findById(Long adjustmentId) {
        return salaryIssueAdjustmentMapper.findById(adjustmentId);
    }

    @Override
    public void save(SalaryIssueAdjustment adjustment) {
        adjustment.setAdjustedTime(LocalDateTime.now());
        salaryIssueAdjustmentMapper.insert(adjustment);
    }

    @Override
    public List<SalaryIssueAdjustment> findByDetailId(Long detailId) {
        return salaryIssueAdjustmentMapper.findByDetailId(detailId);
    }

    @Override
    public List<SalaryIssueAdjustment> findByIssueId(Long issueId) {
        return salaryIssueAdjustmentMapper.findByIssueId(issueId);
    }

    @Override
    public void recordAdjustment(Long detailId, String adjustType, Double oldAmount, Double newAmount, String reason,
            Long operatorId) {
        SalaryIssueAdjustment adjustment = new SalaryIssueAdjustment();
        adjustment.setDetailId(detailId);
        adjustment.setAdjustType(adjustType);
        adjustment.setOldAmount(oldAmount);
        adjustment.setNewAmount(newAmount);
        adjustment.setAdjustReason(reason);
        adjustment.setAdjustedBy(operatorId);
        save(adjustment);
    }
}