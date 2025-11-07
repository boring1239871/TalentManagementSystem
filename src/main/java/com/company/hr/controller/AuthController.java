package com.company.hr.controller;

import com.company.hr.common.Result;
import com.company.hr.entity.User;
import com.company.hr.service.UserService;
import com.company.hr.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

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

        if (user == null) {
            return Result.error("用户名或密码错误");
        }

        // 生成JWT Token
        String token = jwtUtil.generateToken(username);

        // 构建返回数据
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("token", token);

        return Result.success("登录成功", result);
    }

    // 获取当前用户信息（从JWT中提取）
    @GetMapping("/current-user")
    public Result<User> getCurrentUser(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = null;
        if (authentication != null && authentication.isAuthenticated()) {
            // 通过安全上下文获取用户名（JWT 过滤器会自动填充）
            username = authentication.getName();
        } else {
            // 兼容：若过滤器未生效或上下文为空，尝试从 Authorization 头解析 JWT
            final String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return Result.error("未认证");
            }
            String jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            if (username == null || !jwtUtil.validateToken(jwt, username)) {
                return Result.error("未认证");
            }
        }

        User currentUser = userService.findByUsername(username);
        if (currentUser == null) {
            return Result.error("用户不存在");
        }
        return Result.success(currentUser);
    }

    // 用户登出
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        // 清理安全上下文（对于 JWT 无状态认证，登出更多依赖前端丢弃令牌或服务端黑名单）
        SecurityContextHolder.clearContext();
        return Result.success("登出成功");
    }


}