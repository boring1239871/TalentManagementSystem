package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.Organization;
import com.company.hr.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController extends BaseController {

    @Autowired
    private OrganizationService organizationService;

    // 获取机构列表
    @GetMapping
    public Result<List<Organization>> getOrganizationList() {
        List<Organization> organizations = organizationService.findAll();
        return Result.success(organizations);
    }

    // 获取机构树
    @GetMapping("/tree")
    public Result<List<Organization>> getOrgTree() {
        List<Organization> orgTree = organizationService.findOrgTree();
        return Result.success(orgTree);
    }

    // 根据层级获取机构列表
    @GetMapping("/level/{level}")
    public Result<List<Organization>> getOrgsByLevel(@PathVariable Integer level) {
        List<Organization> orgs = organizationService.findByLevel(level);
        return Result.success(orgs);
    }

    // 获取子机构列表
    @GetMapping("/{parentId}/children")
    public Result<List<Organization>> getChildOrgs(@PathVariable Long parentId) {
        List<Organization> childOrgs = organizationService.findByParentId(parentId);
        return Result.success(childOrgs);
    }

    // 获取机构详情
    @GetMapping("/{orgId}")
    public Result<Organization> getOrgDetail(@PathVariable Long orgId) {
        Organization org = organizationService.findById(orgId);
        return Result.success(org);
    }

    // 创建机构
    @PostMapping
    public Result<String> createOrganization(@RequestBody Organization organization) {
        organizationService.save(organization);
        return Result.success("机构创建成功");
    }

    // 更新机构
    @PutMapping("/{orgId}")
    public Result<String> updateOrganization(@PathVariable Long orgId, @RequestBody Organization organization) {
        organization.setOrgId(orgId);
        organizationService.update(organization);
        return Result.success("机构更新成功");
    }

    // 禁用机构
    @DeleteMapping("/{orgId}")
    public Result<String> disableOrganization(@PathVariable Long orgId) {
        organizationService.disable(orgId);
        return Result.success("机构已禁用");
    }
}