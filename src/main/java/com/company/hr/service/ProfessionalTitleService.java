package com.company.hr.service;

import com.company.hr.entity.ProfessionalTitle;
import java.util.List;

public interface ProfessionalTitleService {

    // 基础CRUD方法
    List<ProfessionalTitle> findAll();

    ProfessionalTitle findById(Long titleId);

    void save(ProfessionalTitle title);

    void update(ProfessionalTitle title);

    // 业务方法
    List<ProfessionalTitle> findByLevel(String level);

    List<ProfessionalTitle> findAllActive();

    void disable(Long titleId);
}