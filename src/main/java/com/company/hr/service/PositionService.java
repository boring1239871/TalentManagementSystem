package com.company.hr.service;

import com.company.hr.entity.Position;
import java.util.List;

public interface PositionService {

    // 基础CRUD方法
    List<Position> findAll();

    Position findById(Long positionId);

    void save(Position position);

    void update(Position position);

    // 业务方法
    List<Position> findByOrgId(Long orgId);

    Position findDetailById(Long positionId);

    void disable(Long positionId);
}