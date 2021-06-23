package com.tanjiaming99.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author tanjiaming99.com
 * @Date 2021/6/23 14:54
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO implements Serializable {

    @NotBlank(message = "昵称不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
