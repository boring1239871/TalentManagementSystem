package com.company.hr.mapper;

import com.company.hr.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserMapper {

    // 基础CRUD方法
    @Select("SELECT * FROM system_user")
    List<User> findAll();

    @Select("SELECT * FROM system_user WHERE user_id = #{userId}")
    User findById(Long userId);

    @Insert("INSERT INTO system_user(username, password, real_name, email, phone, role, status, created_by) " +
            "VALUES(#{username}, #{password}, #{realName}, #{email}, #{phone}, #{role}, #{status}, #{createdBy})")
    void insert(User user);

    @Update("UPDATE system_user SET real_name=#{realName}, email=#{email}, phone=#{phone}, role=#{role}, status=#{status} WHERE user_id=#{userId}")
    void update(User user);

    // 业务方法
    @Select("SELECT * FROM system_user WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT COUNT(*) FROM system_user WHERE username = #{username}")
    int countByUsername(String username);

    @Update("UPDATE system_user SET last_login_time = NOW() WHERE user_id = #{userId}")
    void updateLastLoginTime(Long userId);
}