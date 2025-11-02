package com.company.hr.service.impl;

import com.company.hr.entity.SalaryStandard;
import com.company.hr.mapper.SalaryStandardMapper;
import com.company.hr.service.SalaryStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalaryStandardServiceImpl implements SalaryStandardService {

    @Autowired
    private SalaryStandardMapper salaryStandardMapper;

    @Override
    public List<SalaryStandard> findAll() {
        return salaryStandardMapper.findAll();
    }

    @Override
    public SalaryStandard findById(Long stdId) {
        return salaryStandardMapper.findById(stdId);
    }

    @Override
    public void save(SalaryStandard standard) {
        if (standard.getStdCode() == null) {
            standard.setStdCode("STD" + System.currentTimeMillis());
        }
        standard.setStatus("draft");
        standard.setCreatedTime(LocalDateTime.now());
        salaryStandardMapper.insert(standard);
    }

    @Override
    public void update(SalaryStandard standard) {
        salaryStandardMapper.update(standard);
    }

    @Override
    public SalaryStandard findDetailById(Long stdId) {
        return salaryStandardMapper.findDetailById(stdId);
    }

    @Override
    public List<SalaryStandard> findAllApproved() {
        return salaryStandardMapper.findAllApproved();
    }

    @Override
    public List<SalaryStandard> findApprovedByPositionId(Long positionId) {
        return salaryStandardMapper.findApprovedByPositionId(positionId);
    }

    @Override
    public void reviewSalaryStandard(Long stdId, Long reviewerId, String comment) {
        SalaryStandard standard = salaryStandardMapper.findById(stdId);
        if (standard != null && "pending_review".equals(standard.getStatus())) {
            standard.setStatus("approved");
            standard.setReviewedBy(reviewerId);
            standard.setReviewedTime(LocalDateTime.now());
            salaryStandardMapper.update(standard);
        }
    }

    @Override
    public void disable(Long stdId) {
        SalaryStandard standard = salaryStandardMapper.findById(stdId);
        if (standard != null) {
            standard.setStatus("inactive");
            salaryStandardMapper.update(standard);
        }
    }
}