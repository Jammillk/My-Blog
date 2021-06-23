package com.tanjiaming99.myblog.shiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author tanjiaming99.com
 * @Date 2021/6/23 14:16
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountProfile implements Serializable {
    private Long id;

    private String username;

    private String avatar;

    private String email;

    private LocalDateTime created;

    private LocalDateTime lastLogin;
}
