package com.tanjiaming99.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author tanjiaming99.com
 * @Date 2021/6/26 19:16
 * 这是登录时传来的用户信息
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
}
