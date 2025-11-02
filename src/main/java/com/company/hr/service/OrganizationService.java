package com.company.hr.service;

import com.company.hr.entity.Organization;
import java.util.List;

public interface OrganizationService {

    // 基础CRUD方法
    List<Organization> findAll();

    Organization findById(Long orgId);

    void save(Organization organization);

    void update(Organization organization);

    // 业务方法
    List<Organization> findByLevel(Integer level);

    List<Organization> findByParentId(Long parentOrgId);

    List<Organization> findOrgTree();

    void disable(Long orgId);
}