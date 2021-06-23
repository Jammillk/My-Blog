package com.tanjiaming99.myblog.service.impl;

import com.tanjiaming99.myblog.entity.Blog;
import com.tanjiaming99.myblog.mapper.BlogMapper;
import com.tanjiaming99.myblog.service.BlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
