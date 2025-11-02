package com.company.hr.service.impl;

import com.company.hr.entity.SalaryItem;
import com.company.hr.mapper.SalaryItemMapper;
import com.company.hr.service.SalaryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SalaryItemServiceImpl implements SalaryItemService {

    @Autowired
    private SalaryItemMapper salaryItemMapper;

    @Override
    public List<SalaryItem> findAll() {
        return salaryItemMapper.findAll();
    }

    @Override
    public SalaryItem findById(Long itemId) {
        return salaryItemMapper.findById(itemId);
    }

    @Override
    public void save(SalaryItem item) {
        salaryItemMapper.insert(item);
    }

    @Override
    public void update(SalaryItem item) {
        salaryItemMapper.update(item);
    }

    @Override
    public List<SalaryItem> findByItemType(String itemType) {
        return salaryItemMapper.findByItemType(itemType);
    }

    @Override
    public List<SalaryItem> findBySystemItem(Boolean isSystemItem) {
        return salaryItemMapper.findBySystemItem(isSystemItem);
    }

    @Override
    public List<SalaryItem> findAllActive() {
        return salaryItemMapper.findAllActive();
    }

    @Override
    public void disable(Long itemId) {
        SalaryItem item = salaryItemMapper.findById(itemId);
        if (item != null) {
            item.setStatus("inactive");
            salaryItemMapper.update(item);
        }
    }
}