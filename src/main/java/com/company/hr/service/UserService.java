package com.company.hr.service;

import com.company.hr.entity.User;
import java.util.List;

public interface UserService {

    // 基础CRUD方法
    List<User> findAll();

    User findById(Long userId);

    void save(User user);

    void update(User user);

    // 业务方法
    User findByUsername(String username);

    boolean isUsernameExists(String username);

    void updateLastLoginTime(Long userId);

    User login(String username, String password);

    void disable(Long userId);

    // 用户注册

}