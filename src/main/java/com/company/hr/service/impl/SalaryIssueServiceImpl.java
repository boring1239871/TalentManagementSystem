package com.company.hr.service.impl;

import com.company.hr.entity.SalaryIssue;
import com.company.hr.mapper.SalaryIssueMapper;
import com.company.hr.service.SalaryIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalaryIssueServiceImpl implements SalaryIssueService {

    @Autowired
    private SalaryIssueMapper salaryIssueMapper;

    @Override
    public List<SalaryIssue> findAll() {
        return salaryIssueMapper.findAll();
    }

    @Override
    public SalaryIssue findById(Long issueId) {
        return salaryIssueMapper.findById(issueId);
    }

    @Override
    public void save(SalaryIssue issue) {
        if (issue.getIssueCode() == null) {
            issue.setIssueCode("SAL" + System.currentTimeMillis());
        }
        issue.setStatus("draft");
        issue.setCreatedTime(LocalDateTime.now());
        salaryIssueMapper.insert(issue);
    }

    @Override
    public void update(SalaryIssue issue) {
        salaryIssueMapper.update(issue);
    }

    @Override
    public SalaryIssue findDetailById(Long issueId) {
        return salaryIssueMapper.findDetailById(issueId);
    }

    @Override
    public List<SalaryIssue> findByStatus(String status) {
        return salaryIssueMapper.findByStatus(status);
    }

    @Override
    public SalaryIssue findByOrgAndPeriod(Long orgId, Integer year, Integer month) {
        return salaryIssueMapper.findByOrgAndPeriod(orgId, year, month);
    }

    @Override
    public void reviewSalaryIssue(Long issueId, Long reviewerId, String comment) {
        SalaryIssue issue = salaryIssueMapper.findById(issueId);
        if (issue != null && "pending_review".equals(issue.getStatus())) {
            issue.setStatus("reviewed");
            issue.setReviewedBy(reviewerId);
            issue.setReviewedTime(LocalDateTime.now());
            salaryIssueMapper.update(issue);
        }
    }

    @Override
    public void approveSalaryIssue(Long issueId, Long approverId) {
        SalaryIssue issue = salaryIssueMapper.findById(issueId);
        if (issue != null && "reviewed".equals(issue.getStatus())) {
            issue.setStatus("approved");
            issue.setReviewedBy(approverId);
            issue.setReviewedTime(LocalDateTime.now());
            salaryIssueMapper.update(issue);
        }
    }

    @Override
    public void markAsPaid(Long issueId, String paymentRef) {
        SalaryIssue issue = salaryIssueMapper.findById(issueId);
        if (issue != null && "approved".equals(issue.getStatus())) {
            issue.setStatus("paid");
            issue.setPaidTime(LocalDateTime.now());
            issue.setPaymentRef(paymentRef);
            salaryIssueMapper.update(issue);
        }
    }
}