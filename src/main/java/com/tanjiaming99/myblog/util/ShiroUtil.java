package com.tanjiaming99.myblog.util;

import com.tanjiaming99.myblog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @Author tanjiaming99.com
 * @Date 2021/6/23 15:56
 **/
public class ShiroUtil {

    public static AccountProfile getProfile(){

        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
