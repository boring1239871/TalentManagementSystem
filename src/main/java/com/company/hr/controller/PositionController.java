package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.Position;
import com.company.hr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
public class PositionController extends BaseController {

    @Autowired
    private PositionService positionService;

    // 获取职位列表
    @GetMapping
    public Result<List<Position>> getPositionList() {
        List<Position> positions = positionService.findAll();
        return Result.success(positions);
    }

    // 根据机构获取职位列表
    @GetMapping("/org/{orgId}")
    public Result<List<Position>> getPositionsByOrg(@PathVariable Long orgId) {
        List<Position> positions = positionService.findByOrgId(orgId);
        return Result.success(positions);
    }

    // 获取职位详情
    @GetMapping("/{positionId}")
    public Result<Position> getPositionDetail(@PathVariable Long positionId) {
        Position position = positionService.findById(positionId);
        return Result.success(position);
    }

    // 获取职位详细信息（包含关联数据）
    @GetMapping("/{positionId}/detail")
    public Result<Position> getPositionWithDetail(@PathVariable Long positionId) {
        Position position = positionService.findDetailById(positionId);
        return Result.success(position);
    }

    // 创建职位
    @PostMapping
    public Result<String> createPosition(@RequestBody Position position) {
        positionService.save(position);
        return Result.success("职位创建成功");
    }

    // 更新职位
    @PutMapping("/{positionId}")
    public Result<String> updatePosition(@PathVariable Long positionId, @RequestBody Position position) {
        position.setPositionId(positionId);
        positionService.update(position);
        return Result.success("职位更新成功");
    }

    // 禁用职位
    @DeleteMapping("/{positionId}")
    public Result<String> disablePosition(@PathVariable Long positionId) {
        positionService.disable(positionId);
        return Result.success("职位已禁用");
    }
}