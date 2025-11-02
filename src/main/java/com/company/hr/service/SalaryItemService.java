package com.company.hr.service;

import com.company.hr.entity.SalaryItem;
import java.util.List;

public interface SalaryItemService {

    // 基础CRUD方法
    List<SalaryItem> findAll();

    SalaryItem findById(Long itemId);

    void save(SalaryItem item);

    void update(SalaryItem item);

    // 业务方法
    List<SalaryItem> findByItemType(String itemType);

    List<SalaryItem> findBySystemItem(Boolean isSystemItem);

    List<SalaryItem> findAllActive();

    void disable(Long itemId);
}