package com.company.hr.service.impl;

import com.company.hr.entity.ProfessionalTitle;
import com.company.hr.mapper.ProfessionalTitleMapper;
import com.company.hr.service.ProfessionalTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProfessionalTitleServiceImpl implements ProfessionalTitleService {

    @Autowired
    private ProfessionalTitleMapper professionalTitleMapper;

    @Override
    public List<ProfessionalTitle> findAll() {
        return professionalTitleMapper.findAll();
    }

    @Override
    public ProfessionalTitle findById(Long titleId) {
        return professionalTitleMapper.findById(titleId);
    }

    @Override
    public void save(ProfessionalTitle title) {
        professionalTitleMapper.insert(title);
    }

    @Override
    public void update(ProfessionalTitle title) {
        professionalTitleMapper.update(title);
    }

    @Override
    public List<ProfessionalTitle> findByLevel(String level) {
        return professionalTitleMapper.findByLevel(level);
    }

    @Override
    public List<ProfessionalTitle> findAllActive() {
        return professionalTitleMapper.findAllActive();
    }

    @Override
    public void disable(Long titleId) {
        ProfessionalTitle title = professionalTitleMapper.findById(titleId);
        if (title != null) {
            title.setStatus("inactive");
            professionalTitleMapper.update(title);
        }
    }
}