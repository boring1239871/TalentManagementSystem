package com.company.hr.service.impl;

import com.company.hr.entity.Organization;
import com.company.hr.mapper.OrganizationMapper;
import com.company.hr.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public List<Organization> findAll() {
        return organizationMapper.findAll();
    }

    @Override
    public Organization findById(Long orgId) {
        return organizationMapper.findById(orgId);
    }

    @Override
    public void save(Organization organization) {
        organizationMapper.insert(organization);
    }

    @Override
    public void update(Organization organization) {
        organizationMapper.update(organization);
    }

    @Override
    public List<Organization> findByLevel(Integer level) {
        return organizationMapper.findByLevel(level);
    }

    @Override
    public List<Organization> findByParentId(Long parentOrgId) {
        return organizationMapper.findByParentId(parentOrgId);
    }

    @Override
    public List<Organization> findOrgTree() {
        return organizationMapper.findOrgTree();
    }

    @Override
    public void disable(Long orgId) {
        Organization org = organizationMapper.findById(orgId);
        if (org != null) {
            org.setStatus("inactive");
            organizationMapper.update(org);
        }
    }
}