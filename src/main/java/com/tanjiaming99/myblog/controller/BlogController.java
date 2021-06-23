package com.tanjiaming99.myblog.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tanjiaming99.myblog.common.lang.Result;
import com.tanjiaming99.myblog.entity.Blog;
import com.tanjiaming99.myblog.service.BlogService;
import com.tanjiaming99.myblog.util.ShiroUtil;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author author:tanjiaming99.com
 * @since 2021-06-23
 */
@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;


    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {
        // 分页完成 ？？！
        final int pageSize = 5;
        Page page = new Page(currentPage, pageSize);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.success(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已经被删除");

        return Result.success(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog temp = null;
        if (blog.getId() != null) {
            // 编辑旧文章
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()), "不是你的文章，没有权限编辑。");
        } else {
            // 添加新文章
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        String[] ignoreParams = {"id", "userId", "created", "status"};
        BeanUtils.copyProperties(blog, temp, ignoreParams);
        // 更新（它这么强大的吗？？！这个自动生成，牛啊）
        blogService.saveOrUpdate(temp);
        return Result.success(temp);
    }


}
