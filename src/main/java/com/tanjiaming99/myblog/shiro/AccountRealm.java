package com.tanjiaming99.myblog.shiro;

import com.tanjiaming99.myblog.entity.User;
import com.tanjiaming99.myblog.service.UserService;
import com.tanjiaming99.myblog.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author tanjiaming99.com
 * @Date 2021/6/23 13:19
 **/
@Component
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;


        String userId = jwtUtils.getClaimByToken((String) token.getPrincipal()).getSubject();
        User user = userService.getById(userId);
        if (user == null){
            throw new UnknownAccountException("账户不存在……");
        }

        if (user.getStatus() == -1){
            throw new LockedAccountException("账户已被锁定……");
        }

        AccountProfile profile = new AccountProfile();
        // 复制属性
        BeanUtils.copyProperties(user, profile);



        System.out.println("---------------");

        return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
    }
}
