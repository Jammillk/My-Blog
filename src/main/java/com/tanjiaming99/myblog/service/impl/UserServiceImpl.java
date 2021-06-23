package com.tanjiaming99.myblog.service.impl;

import com.tanjiaming99.myblog.entity.User;
import com.tanjiaming99.myblog.mapper.UserMapper;
import com.tanjiaming99.myblog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author:tanjiaming99.com
 * @since 2021-06-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
