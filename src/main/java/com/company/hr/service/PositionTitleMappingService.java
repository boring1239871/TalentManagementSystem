package com.company.hr.service;

import com.company.hr.entity.PositionTitleMapping;
import java.util.List;

public interface PositionTitleMappingService {

    // 基础CRUD方法
    List<PositionTitleMapping> findAll();

    PositionTitleMapping findById(Long mappingId);

    void save(PositionTitleMapping mapping);

    void update(PositionTitleMapping mapping);

    // 业务方法
    List<PositionTitleMapping> findByPositionId(Long positionId);

    List<PositionTitleMapping> findByTitleId(Long titleId);

    PositionTitleMapping findByPositionAndTitle(Long positionId, Long titleId);

    void disable(Long mappingId);
}