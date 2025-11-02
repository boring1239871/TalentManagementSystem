package com.company.hr.service.impl;

import com.company.hr.entity.Position;
import com.company.hr.mapper.PositionMapper;
import com.company.hr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public List<Position> findAll() {
        return positionMapper.findAll();
    }

    @Override
    public Position findById(Long positionId) {
        return positionMapper.findById(positionId);
    }

    @Override
    public void save(Position position) {
        positionMapper.insert(position);
    }

    @Override
    public void update(Position position) {
        positionMapper.update(position);
    }

    @Override
    public List<Position> findByOrgId(Long orgId) {
        return positionMapper.findByOrgId(orgId);
    }

    @Override
    public Position findDetailById(Long positionId) {
        return positionMapper.findDetailById(positionId);
    }

    @Override
    public void disable(Long positionId) {
        Position position = positionMapper.findById(positionId);
        if (position != null) {
            position.setStatus("inactive");
            positionMapper.update(position);
        }
    }
}