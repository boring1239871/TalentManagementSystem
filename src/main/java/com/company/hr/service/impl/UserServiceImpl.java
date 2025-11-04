package com.company.hr.service.impl;

import com.company.hr.entity.User;
import com.company.hr.mapper.UserMapper;
import com.company.hr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findById(Long userId) {
        return userMapper.findById(userId);
    }

    @Override
    public void save(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public boolean isUsernameExists(String username) {
        return userMapper.countByUsername(username) > 0;
    }

    @Override
    public void updateLastLoginTime(Long userId) {
        userMapper.updateLastLoginTime(userId);
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null)
            throw new RuntimeException("用户不存在");
        if (!"active".equals(user.getStatus()))
            throw new RuntimeException("用户状态异常");

        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (!encryptedPassword.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        userMapper.updateLastLoginTime(user.getUserId());
        return user;
    }

    @Override
    public void disable(Long userId) {
        User user = userMapper.findById(userId);
        if (user != null) {
            user.setStatus("inactive");
            userMapper.update(user);
        }
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("注册信息不能为空");
        }

        String username = user.getUsername();
        String password = user.getPassword();

        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new RuntimeException("密码不能为空");
        }

        if (isUsernameExists(username)) {
            throw new RuntimeException("用户名已存在");
        }

        // 使用与登录一致的MD5进行密码加密（保持当前系统一致性）
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        user.setPassword(encryptedPassword);

        // 默认角色与状态
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("user");
        }
        if (user.getStatus() == null || user.getStatus().trim().isEmpty()) {
            user.setStatus("active");
        }

        // 创建人默认设置为0（匿名注册）
        if (user.getCreatedBy() == null) {
            user.setCreatedBy(0L);
        }

        userMapper.insert(user);
        // 返回带ID的用户信息
        return userMapper.findByUsername(username);
    }
}