package com.company.hr.mapper;

import com.company.hr.entity.Position;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PositionMapper {

        // 基础CRUD方法
        @Select("SELECT * FROM position")
        List<Position> findAll();

        @Select("SELECT * FROM position WHERE position_id = #{positionId}")
        Position findById(Long positionId);

        @Insert("INSERT INTO position(position_code, position_name, position_desc, org_id, position_category, position_grade, status, created_by) "
                        +
                        "VALUES(#{positionCode}, #{positionName}, #{positionDesc}, #{orgId}, #{positionCategory}, #{positionGrade}, #{status}, #{createdBy})")
        void insert(Position position);

        @Update("UPDATE position SET position_name=#{positionName}, position_desc=#{positionDesc}, position_category=#{positionCategory}, position_grade=#{positionGrade}, status=#{status} WHERE position_id=#{positionId}")
        void update(Position position);

        // 业务方法
        @Select("SELECT p.*, o.org_name FROM position p " +
                        "LEFT JOIN organization o ON p.org_id = o.org_id " +
                        "WHERE p.org_id = #{orgId} AND p.status = 'active'")
        List<Position> findByOrgId(Long orgId);

        @Select("SELECT p.*, o.org_name FROM position p " +
                        "LEFT JOIN organization o ON p.org_id = o.org_id " +
                        "WHERE p.position_id = #{positionId}")
        Position findDetailById(Long positionId);
}