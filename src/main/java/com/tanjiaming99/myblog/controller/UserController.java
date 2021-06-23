package com.tanjiaming99.myblog.controller;


import com.tanjiaming99.myblog.common.lang.Result;
import com.tanjiaming99.myblog.entity.User;
import com.tanjiaming99.myblog.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author:tanjiaming99.com
 * @since 2021-06-23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequiresAuthentication
    @GetMapping("/index")
    public Object index(){

        User user = userService.getById(1L);


        return Result.success(user);
    }


}
