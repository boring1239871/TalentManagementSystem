package com.company.hr.mapper;

import com.company.hr.entity.SalaryStandard;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface SalaryStandardMapper {

        // 基础CRUD方法
        @Select("SELECT * FROM salary_standard")
        List<SalaryStandard> findAll();

        @Select("SELECT * FROM salary_standard WHERE std_id = #{stdId}")
        SalaryStandard findById(Long stdId);

        @Insert("INSERT INTO salary_standard(std_code, std_name, creator, created_by, applicable_position_id, status, salary_items, base_salary, total_amount) "
                        +
                        "VALUES(#{stdCode}, #{stdName}, #{creator}, #{createdBy}, #{applicablePositionId}, #{status}, #{salaryItems}, #{baseSalary}, #{totalAmount})")
        void insert(SalaryStandard standard);

        @Update("UPDATE salary_standard SET std_name=#{stdName}, creator=#{creator}, applicable_position_id=#{applicablePositionId}, status=#{status}, salary_items=#{salaryItems}, base_salary=#{baseSalary}, total_amount=#{totalAmount} WHERE std_id=#{stdId}")
        void update(SalaryStandard standard);

        // 业务方法
        @Select("SELECT ss.*, p.position_name " +
                        "FROM salary_standard ss " +
                        "LEFT JOIN position p ON ss.applicable_position_id = p.position_id " +
                        "WHERE ss.std_id = #{stdId}")
        SalaryStandard findDetailById(Long stdId);

        @Select("SELECT ss.*, p.position_name " +
                        "FROM salary_standard ss " +
                        "LEFT JOIN position p ON ss.applicable_position_id = p.position_id " +
                        "WHERE ss.status = 'approved'")
        List<SalaryStandard> findAllApproved();

        @Select("SELECT * FROM salary_standard WHERE status = 'approved' AND applicable_position_id = #{positionId}")
        List<SalaryStandard> findApprovedByPositionId(Long positionId);
}