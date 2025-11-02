package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.User;
import com.company.hr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Autowired
    private UserService userService;

    // 用户登录
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // 参数校验
        if (username == null || username.trim().isEmpty()) {
            return Result.error("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            return Result.error("密码不能为空");
        }

        // 用户认证
        User user = userService.login(username, password);

        // 构建返回数据（这里简化，实际应该生成Token）
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("token", "mock-jwt-token-" + System.currentTimeMillis());

        return Result.success("登录成功", result);
    }

    // 获取当前用户信息
    @GetMapping("/current-user")
    public Result<User> getCurrentUser() {
        // 这里简化处理，实际应该从Token中获取用户ID
        User currentUser = userService.findById(1L); // 模拟用户
        return Result.success(currentUser);
    }

    // 用户登出
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success("登出成功");
    }
}