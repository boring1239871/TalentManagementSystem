package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.User;
import com.company.hr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    // 获取用户列表
    @GetMapping
    public Result<List<User>> getUserList() {
        List<User> users = userService.findAll();
        return Result.success(users);
    }

    // 根据用户名获取用户
    @GetMapping("/username/{username}")
    public Result<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    // 获取用户详情
    @GetMapping("/{userId}")
    public Result<User> getUserDetail(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return Result.success(user);
    }

    // 创建用户
    @PostMapping
    public Result<String> createUser(@RequestBody User user) {
        // 检查用户名是否已存在
        if (userService.isUsernameExists(user.getUsername())) {
            return Result.error("用户名已存在");
        }

        user.setStatus("active");
        userService.save(user);
        return Result.success("用户创建成功");
    }

    // 更新用户
    @PutMapping("/{userId}")
    public Result<String> updateUser(@PathVariable Long userId, @RequestBody User user) {
        user.setUserId(userId);
        userService.update(user);
        return Result.success("用户更新成功");
    }

    // 禁用用户
    @DeleteMapping("/{userId}")
    public Result<String> disableUser(@PathVariable Long userId) {
        userService.disable(userId);
        return Result.success("用户已禁用");
    }

    // 更新用户登录时间
    @PostMapping("/{userId}/login-time")
    public Result<String> updateLastLoginTime(@PathVariable Long userId) {
        userService.updateLastLoginTime(userId);
        return Result.success("登录时间更新成功");
    }
}