package com.company.hr.mapper;

import com.company.hr.entity.SalaryItem;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface SalaryItemMapper {

    // 基础CRUD方法
    @Select("SELECT * FROM salary_item")
    List<SalaryItem> findAll();

    @Select("SELECT * FROM salary_item WHERE item_id = #{itemId}")
    SalaryItem findById(Long itemId);

    @Insert("INSERT INTO salary_item(item_code, item_name, item_type, calculation_type, formula_expression, is_system_item, display_order, status, created_by) "
            +
            "VALUES(#{itemCode}, #{itemName}, #{itemType}, #{calculationType}, #{formulaExpression}, #{isSystemItem}, #{displayOrder}, #{status}, #{createdBy})")
    void insert(SalaryItem item);

    @Update("UPDATE salary_item SET item_name=#{itemName}, item_type=#{itemType}, calculation_type=#{calculationType}, formula_expression=#{formulaExpression}, display_order=#{displayOrder}, status=#{status} WHERE item_id=#{itemId}")
    void update(SalaryItem item);

    // 业务方法
    @Select("SELECT * FROM salary_item WHERE item_type = #{itemType} AND status = 'active' ORDER BY display_order")
    List<SalaryItem> findByItemType(String itemType);

    @Select("SELECT * FROM salary_item WHERE is_system_item = #{isSystemItem} AND status = 'active' ORDER BY display_order")
    List<SalaryItem> findBySystemItem(Boolean isSystemItem);

    @Select("SELECT * FROM salary_item WHERE status = 'active' ORDER BY item_type, display_order")
    List<SalaryItem> findAllActive();
}