package com.tanjiaming99.myblog.common.lang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author tanjiaming99.com
 * @Date 2021/6/23 11:11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result implements Serializable {
    /**
     * 状态码
     * 200是正常
     * 非200是异常
     */
    private int code;
    /**
     * 结果信息
     */
    private String message;
    /**
     * 结果数据
     */
    private Object data;


    public static Result success(Object data) {
        return new Result(200, "操作成功", data);
    }

    public static Result fail(String message) {
        return new Result(400, message, null);
    }


}
