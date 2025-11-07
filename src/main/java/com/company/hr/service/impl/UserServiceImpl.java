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
        // 如果 createdBy 未设置，则默认设置为系统管理员 ID (例如 1L)
        // 在实际应用中，这应该来自经过身份验证的用户的上下文
        if (user.getCreatedBy() == null) {
            user.setCreatedBy(1L); // 假设 1L 是默认管理员用户 ID
        }
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

}