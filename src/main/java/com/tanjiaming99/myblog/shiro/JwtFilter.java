package com.tanjiaming99.myblog.shiro;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.tanjiaming99.myblog.common.lang.Result;
import com.tanjiaming99.myblog.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @Author tanjiaming99.com
 * @Date 2021/6/23 13:35
 **/
@Component
public class JwtFilter extends AuthenticatingFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        if (!StringUtils.hasLength(jwt)) {
            return null;
        }
        return new JwtToken(jwt);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        if (StringUtils.hasLength(jwt)) {
            // 校验jwt
            Claims claim = jwtUtils.getClaimByToken(jwt);
            // 如果它为空，或者它过期了
            if (claim == null || jwtUtils.isTokenExpired(claim.getExpiration())){
                throw new ExpiredCredentialsException("token已失效，请重新登录");
            }
            // 执行登录
            return executeLogin(servletRequest, servletResponse);
        }else{
            // 没有jwt，则不用交给shiro处理，通过
            return true;
        }
    }

    /**
     * 登录出现异常的逻辑处理
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // 获取异常信息
        Throwable throwable = e.getCause() == null ? null : e.getCause();
        Result fail = Result.fail(throwable.getMessage());
        // 返回JSON信息
        String json = JSONUtil.toJsonStr(fail);
        try {
            // 然后再返回前端
            httpServletResponse.getWriter().print(json);
        } catch (IOException ioException) {

        }
        return false;
    }
}
