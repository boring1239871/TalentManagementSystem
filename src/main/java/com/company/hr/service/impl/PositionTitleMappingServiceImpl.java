package com.company.hr.service.impl;

import com.company.hr.entity.PositionTitleMapping;
import com.company.hr.mapper.PositionTitleMappingMapper;
import com.company.hr.service.PositionTitleMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PositionTitleMappingServiceImpl implements PositionTitleMappingService {

    @Autowired
    private PositionTitleMappingMapper positionTitleMappingMapper;

    @Override
    public List<PositionTitleMapping> findAll() {
        return positionTitleMappingMapper.findAll();
    }

    @Override
    public PositionTitleMapping findById(Long mappingId) {
        return positionTitleMappingMapper.findById(mappingId);
    }

    @Override
    public void save(PositionTitleMapping mapping) {
        positionTitleMappingMapper.insert(mapping);
    }

    @Override
    public void update(PositionTitleMapping mapping) {
        positionTitleMappingMapper.update(mapping);
    }

    @Override
    public List<PositionTitleMapping> findByPositionId(Long positionId) {
        return positionTitleMappingMapper.findByPositionId(positionId);
    }

    @Override
    public List<PositionTitleMapping> findByTitleId(Long titleId) {
        return positionTitleMappingMapper.findByTitleId(titleId);
    }

    @Override
    public PositionTitleMapping findByPositionAndTitle(Long positionId, Long titleId) {
        return positionTitleMappingMapper.findByPositionAndTitle(positionId, titleId);
    }

    @Override
    public void disable(Long mappingId) {
        PositionTitleMapping mapping = positionTitleMappingMapper.findById(mappingId);
        if (mapping != null) {
            mapping.setStatus("inactive");
            positionTitleMappingMapper.update(mapping);
        }
    }
}