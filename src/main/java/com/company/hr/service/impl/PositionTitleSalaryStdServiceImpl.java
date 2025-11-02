package com.company.hr.service.impl;

import com.company.hr.entity.PositionTitleSalaryStd;
import com.company.hr.mapper.PositionTitleSalaryStdMapper;
import com.company.hr.service.PositionTitleSalaryStdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class PositionTitleSalaryStdServiceImpl implements PositionTitleSalaryStdService {

    @Autowired
    private PositionTitleSalaryStdMapper positionTitleSalaryStdMapper;

    @Override
    public List<PositionTitleSalaryStd> findAll() {
        return positionTitleSalaryStdMapper.findAll();
    }

    @Override
    public PositionTitleSalaryStd findById(Long mapId) {
        return positionTitleSalaryStdMapper.findById(mapId);
    }

    @Override
    public void save(PositionTitleSalaryStd mapping) {
        positionTitleSalaryStdMapper.insert(mapping);
    }

    @Override
    public void update(PositionTitleSalaryStd mapping) {
        positionTitleSalaryStdMapper.update(mapping);
    }

    @Override
    public PositionTitleSalaryStd findCurrentByPositionAndTitle(Long positionId, Long titleId) {
        return positionTitleSalaryStdMapper.findCurrentByPositionAndTitle(positionId, titleId);
    }

    @Override
    public List<PositionTitleSalaryStd> findEffectiveByDate(LocalDate date) {
        return positionTitleSalaryStdMapper.findEffectiveByDate(date);
    }

    @Override
    public void disable(Long mapId) {
        PositionTitleSalaryStd mapping = positionTitleSalaryStdMapper.findById(mapId);
        if (mapping != null) {
            mapping.setStatus("inactive");
            positionTitleSalaryStdMapper.update(mapping);
        }
    }
}