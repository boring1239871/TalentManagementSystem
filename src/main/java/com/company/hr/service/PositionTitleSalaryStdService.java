package com.company.hr.service;

import com.company.hr.entity.PositionTitleSalaryStd;
import java.time.LocalDate;
import java.util.List;

public interface PositionTitleSalaryStdService {

    // 基础CRUD方法
    List<PositionTitleSalaryStd> findAll();

    PositionTitleSalaryStd findById(Long mapId);

    void save(PositionTitleSalaryStd mapping);

    void update(PositionTitleSalaryStd mapping);

    // 业务方法
    PositionTitleSalaryStd findCurrentByPositionAndTitle(Long positionId, Long titleId);

    List<PositionTitleSalaryStd> findEffectiveByDate(LocalDate date);

    void disable(Long mapId);
}