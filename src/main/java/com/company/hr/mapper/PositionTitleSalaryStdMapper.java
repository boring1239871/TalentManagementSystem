package com.company.hr.mapper;

import com.company.hr.entity.PositionTitleSalaryStd;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PositionTitleSalaryStdMapper {

        // 基础CRUD方法
        @Select("SELECT * FROM position_title_salary_std")
        List<PositionTitleSalaryStd> findAll();

        @Select("SELECT * FROM position_title_salary_std WHERE map_id = #{mapId}")
        PositionTitleSalaryStd findById(Long mapId);

        @Insert("INSERT INTO position_title_salary_std(position_id, title_id, salary_standard_id, effective_date, expiration_date, is_current, status, created_by) "
                        +
                        "VALUES(#{positionId}, #{titleId}, #{salaryStandardId}, #{effectiveDate}, #{expirationDate}, #{isCurrent}, #{status}, #{createdBy})")
        void insert(PositionTitleSalaryStd mapping);

        @Update("UPDATE position_title_salary_std SET salary_standard_id=#{salaryStandardId}, effective_date=#{effectiveDate}, expiration_date=#{expirationDate}, is_current=#{isCurrent}, status=#{status} WHERE map_id=#{mapId}")
        void update(PositionTitleSalaryStd mapping);

        // 业务方法
        @Select("SELECT ptss.*, p.position_name, t.title_name, ss.std_name " +
                        "FROM position_title_salary_std ptss " +
                        "LEFT JOIN position p ON ptss.position_id = p.position_id " +
                        "LEFT JOIN professional_title t ON ptss.title_id = t.title_id " +
                        "LEFT JOIN salary_standard ss ON ptss.salary_standard_id = ss.std_id " +
                        "WHERE ptss.position_id = #{positionId} AND ptss.title_id = #{titleId} " +
                        "AND ptss.is_current = true AND ptss.status = 'active'")
        PositionTitleSalaryStd findCurrentByPositionAndTitle(@Param("positionId") Long positionId,
                        @Param("titleId") Long titleId);

        @Select("SELECT ptss.*, p.position_name, t.title_name, ss.std_name " +
                        "FROM position_title_salary_std ptss " +
                        "LEFT JOIN position p ON ptss.position_id = p.position_id " +
                        "LEFT JOIN professional_title t ON ptss.title_id = t.title_id " +
                        "LEFT JOIN salary_standard ss ON ptss.salary_standard_id = ss.std_id " +
                        "WHERE ptss.effective_date <= #{date} AND (ptss.expiration_date IS NULL OR ptss.expiration_date >= #{date}) "
                        +
                        "AND ptss.status = 'active' " +
                        "ORDER BY ptss.effective_date DESC")
        List<PositionTitleSalaryStd> findEffectiveByDate(java.time.LocalDate date);
}