package com.company.hr.mapper;

import com.company.hr.entity.EmployeeSequence;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface EmployeeSequenceMapper {

    // 基础CRUD方法
    @Select("SELECT * FROM employee_sequence")
    List<EmployeeSequence> findAll();

    @Select("SELECT * FROM employee_sequence WHERE seq_id = #{seqId}")
    EmployeeSequence findById(Long seqId);

    @Insert("INSERT INTO employee_sequence(org_id, sequence_year, current_value) " +
            "VALUES(#{orgId}, #{sequenceYear}, #{currentValue})")
    void insert(EmployeeSequence sequence);

    @Update("UPDATE employee_sequence SET current_value = #{currentValue} WHERE seq_id = #{seqId}")
    void update(EmployeeSequence sequence);

    // 业务方法
    @Select("SELECT * FROM employee_sequence WHERE org_id = #{orgId} AND sequence_year = #{year}")
    EmployeeSequence findByOrgAndYear(@Param("orgId") Long orgId, @Param("year") Integer year);

    @Update("UPDATE employee_sequence SET current_value = current_value + 1 WHERE seq_id = #{seqId}")
    void incrementValue(Long seqId);
}