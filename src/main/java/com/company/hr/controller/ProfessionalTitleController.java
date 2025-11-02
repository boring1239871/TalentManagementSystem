package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.ProfessionalTitle;
import com.company.hr.service.ProfessionalTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professional-titles")
public class ProfessionalTitleController extends BaseController {

    @Autowired
    private ProfessionalTitleService professionalTitleService;

    // 获取所有职称列表
    @GetMapping
    public Result<List<ProfessionalTitle>> getTitleList() {
        List<ProfessionalTitle> titles = professionalTitleService.findAll();
        return Result.success(titles);
    }

    // 获取所有活跃职称
    @GetMapping("/active")
    public Result<List<ProfessionalTitle>> getActiveTitles() {
        List<ProfessionalTitle> titles = professionalTitleService.findAllActive();
        return Result.success(titles);
    }

    // 根据级别获取职称
    @GetMapping("/level/{level}")
    public Result<List<ProfessionalTitle>> getTitlesByLevel(@PathVariable String level) {
        List<ProfessionalTitle> titles = professionalTitleService.findByLevel(level);
        return Result.success(titles);
    }

    // 获取职称详情
    @GetMapping("/{titleId}")
    public Result<ProfessionalTitle> getTitleDetail(@PathVariable Long titleId) {
        ProfessionalTitle title = professionalTitleService.findById(titleId);
        return Result.success(title);
    }

    // 创建职称
    @PostMapping
    public Result<String> createTitle(@RequestBody ProfessionalTitle title) {
        professionalTitleService.save(title);
        return Result.success("职称创建成功");
    }

    // 更新职称
    @PutMapping("/{titleId}")
    public Result<String> updateTitle(@PathVariable Long titleId, @RequestBody ProfessionalTitle title) {
        title.setTitleId(titleId);
        professionalTitleService.update(title);
        return Result.success("职称更新成功");
    }

    // 禁用职称
    @DeleteMapping("/{titleId}")
    public Result<String> disableTitle(@PathVariable Long titleId) {
        professionalTitleService.disable(titleId);
        return Result.success("职称已禁用");
    }
}