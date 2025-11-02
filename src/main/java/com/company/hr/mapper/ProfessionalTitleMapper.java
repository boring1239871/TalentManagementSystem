package com.company.hr.mapper;

import com.company.hr.entity.ProfessionalTitle;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ProfessionalTitleMapper {

    // 基础CRUD方法
    @Select("SELECT * FROM professional_title")
    List<ProfessionalTitle> findAll();

    @Select("SELECT * FROM professional_title WHERE title_id = #{titleId}")
    ProfessionalTitle findById(Long titleId);

    @Insert("INSERT INTO professional_title(title_code, title_name, title_level, description, display_order, status, created_by) "
            +
            "VALUES(#{titleCode}, #{titleName}, #{titleLevel}, #{description}, #{displayOrder}, #{status}, #{createdBy})")
    void insert(ProfessionalTitle title);

    @Update("UPDATE professional_title SET title_name=#{titleName}, title_level=#{titleLevel}, description=#{description}, display_order=#{displayOrder}, status=#{status} WHERE title_id=#{titleId}")
    void update(ProfessionalTitle title);

    // 业务方法
    @Select("SELECT * FROM professional_title WHERE title_level = #{level} AND status = 'active' ORDER BY display_order")
    List<ProfessionalTitle> findByLevel(String level);

    @Select("SELECT * FROM professional_title WHERE status = 'active' ORDER BY display_order, title_id")
    List<ProfessionalTitle> findAllActive();
}