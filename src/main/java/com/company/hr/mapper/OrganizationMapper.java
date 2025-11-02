package com.company.hr.mapper;

import com.company.hr.entity.Organization;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface OrganizationMapper {

    // 基础CRUD方法
    @Select("SELECT * FROM organization")
    List<Organization> findAll();

    @Select("SELECT * FROM organization WHERE org_id = #{orgId}")
    Organization findById(Long orgId);

    @Insert("INSERT INTO organization(org_code, org_name, parent_org_id, org_path, level_code, org_level, full_code, status, created_by) "
            +
            "VALUES(#{orgCode}, #{orgName}, #{parentOrgId}, #{orgPath}, #{levelCode}, #{orgLevel}, #{fullCode}, #{status}, #{createdBy})")
    void insert(Organization organization);

    @Update("UPDATE organization SET org_name=#{orgName}, status=#{status} WHERE org_id=#{orgId}")
    void update(Organization organization);

    // 业务方法
    @Select("SELECT * FROM organization WHERE org_level = #{level} AND status = 'active' ORDER BY org_code")
    List<Organization> findByLevel(Integer level);

    @Select("SELECT * FROM organization WHERE parent_org_id = #{parentOrgId} AND status = 'active' ORDER BY org_code")
    List<Organization> findByParentId(Long parentOrgId);

    @Select("SELECT o1.*, o2.org_name as parent_org_name FROM organization o1 " +
            "LEFT JOIN organization o2 ON o1.parent_org_id = o2.org_id " +
            "WHERE o1.status = 'active' ORDER BY o1.full_code")
    List<Organization> findOrgTree();
}