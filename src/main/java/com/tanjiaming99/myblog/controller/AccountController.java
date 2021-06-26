package com.tanjiaming99.myblog.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tanjiaming99.myblog.common.lang.Result;
import com.tanjiaming99.myblog.dto.LoginDTO;
import com.tanjiaming99.myblog.dto.UserDTO;
import com.tanjiaming99.myblog.entity.User;
import com.tanjiaming99.myblog.service.UserService;
import com.tanjiaming99.myblog.util.JwtUtils;
import io.jsonwebtoken.Jwt;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;
import java.time.LocalDateTime;

/**
 * @Author tanjiaming99.com
 * @Date 2021/6/23 14:53
 **/
@RestController
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDTO.getUsername()));
        Assert.notNull(user, "用户不存在！");
        // 这里使用hutool工具包authorization的md5加密
        if (!user.getPassword().equals(SecureUtil.md5(loginDTO.getPassword()))) {
            return Result.fail("密码不正确！");
        }
        // 验证有关……
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");
        // 返回一些用户信息
        return Result.success(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }

    @PostMapping("/register")
    public Result register(@Validated @RequestBody UserDTO userDTO) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", userDTO.getUsername()));
        if (user != null) {
            return Result.fail("用户名已存在，请换一个吧~");
        }
        User u = new User();
        BeanUtils.copyProperties(userDTO, u);
        u.setStatus(0);
        u.setCreated(LocalDateTime.now());
        u.setLastLogin(LocalDateTime.now());
        u.setAvatar("https://tanjiaming99.com/upload/2021/06/moving-e1d876a16627470290ab2275756deb27.gif");
        // 要加密噢……
        u.setPassword(SecureUtil.md5(userDTO.getPassword()));
        userService.save(u);
        return Result.success("创建成功！");
    }

}
