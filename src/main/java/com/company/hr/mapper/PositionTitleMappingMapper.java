package com.company.hr.mapper;

import com.company.hr.entity.PositionTitleMapping;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PositionTitleMappingMapper {

        // 基础CRUD方法
        @Select("SELECT * FROM position_title_mapping")
        List<PositionTitleMapping> findAll();

        @Select("SELECT * FROM position_title_mapping WHERE mapping_id = #{mappingId}")
        PositionTitleMapping findById(Long mappingId);

        @Insert("INSERT INTO position_title_mapping(position_id, title_id, is_required, display_order, status, created_by) "
                        +
                        "VALUES(#{positionId}, #{titleId}, #{isRequired}, #{displayOrder}, #{status}, #{createdBy})")
        void insert(PositionTitleMapping mapping);

        @Update("UPDATE position_title_mapping SET is_required=#{isRequired}, display_order=#{displayOrder}, status=#{status} WHERE mapping_id=#{mappingId}")
        void update(PositionTitleMapping mapping);

        // 业务方法
        @Select("SELECT ptm.*, p.position_name, t.title_name " +
                        "FROM position_title_mapping ptm " +
                        "LEFT JOIN position p ON ptm.position_id = p.position_id " +
                        "LEFT JOIN professional_title t ON ptm.title_id = t.title_id " +
                        "WHERE ptm.position_id = #{positionId} AND ptm.status = 'active' " +
                        "ORDER BY ptm.display_order")
        List<PositionTitleMapping> findByPositionId(Long positionId);

        @Select("SELECT ptm.*, p.position_name, t.title_name " +
                        "FROM position_title_mapping ptm " +
                        "LEFT JOIN position p ON ptm.position_id = p.position_id " +
                        "LEFT JOIN professional_title t ON ptm.title_id = t.title_id " +
                        "WHERE ptm.title_id = #{titleId} AND ptm.status = 'active'")
        List<PositionTitleMapping> findByTitleId(Long titleId);

        @Select("SELECT ptm.* FROM position_title_mapping ptm " +
                        "WHERE ptm.position_id = #{positionId} AND ptm.title_id = #{titleId} AND ptm.status = 'active'")
        PositionTitleMapping findByPositionAndTitle(@Param("positionId") Long positionId,
                        @Param("titleId") Long titleId);
}