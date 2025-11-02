package com.company.hr.service;

import com.company.hr.entity.SalaryStandard;
import java.util.List;

public interface SalaryStandardService {

    // 基础CRUD方法
    List<SalaryStandard> findAll();

    SalaryStandard findById(Long stdId);

    void save(SalaryStandard standard);

    void update(SalaryStandard standard);

    // 业务方法
    SalaryStandard findDetailById(Long stdId);

    List<SalaryStandard> findAllApproved();

    List<SalaryStandard> findApprovedByPositionId(Long positionId);

    void reviewSalaryStandard(Long stdId, Long reviewerId, String comment);

    void disable(Long stdId);
}