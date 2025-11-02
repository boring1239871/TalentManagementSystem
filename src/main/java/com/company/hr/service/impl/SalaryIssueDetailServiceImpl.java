package com.company.hr.service.impl;

import com.company.hr.entity.SalaryIssueDetail;
import com.company.hr.mapper.SalaryIssueDetailMapper;
import com.company.hr.service.SalaryIssueDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SalaryIssueDetailServiceImpl implements SalaryIssueDetailService {

    @Autowired
    private SalaryIssueDetailMapper salaryIssueDetailMapper;

    @Override
    public List<SalaryIssueDetail> findAll() {
        return salaryIssueDetailMapper.findAll();
    }

    @Override
    public SalaryIssueDetail findById(Long detailId) {
        return salaryIssueDetailMapper.findById(detailId);
    }

    @Override
    public void save(SalaryIssueDetail detail) {
        salaryIssueDetailMapper.insert(detail);
    }

    @Override
    public void update(SalaryIssueDetail detail) {
        salaryIssueDetailMapper.update(detail);
    }

    @Override
    public List<SalaryIssueDetail> findByIssueId(Long issueId) {
        return salaryIssueDetailMapper.findByIssueId(issueId);
    }

    @Override
    public SalaryIssueDetail findDetailById(Long detailId) {
        return salaryIssueDetailMapper.findDetailById(detailId);
    }

    @Override
    public boolean existsByIssueAndEmp(Long issueId, String empId) {
        return salaryIssueDetailMapper.existsByIssueAndEmp(issueId, empId) > 0;
    }

    @Override
    public void adjustSalaryDetail(Long detailId, Double bonusAmount, Double deductionAmount, String reason,
            Long operatorId) {
        SalaryIssueDetail detail = salaryIssueDetailMapper.findById(detailId);
        if (detail != null) {
            detail.setBonusAmount(bonusAmount);
            detail.setDeductionAmount(deductionAmount);
            detail.setAdjustmentReason(reason);
            // 重新计算总额
            double subtotal = detail.getBaseSalaryAmount() + bonusAmount - deductionAmount;
            detail.setSubtotalAmount(subtotal);
            detail.setTotalAmount(subtotal);
            detail.setStatus("adjusted");
            salaryIssueDetailMapper.update(detail);
        }
    }
}