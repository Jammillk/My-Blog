package com.tanjiaming99.myblog.controller;

import okhttp3.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * @Author tanjiaming99.com
 * @Date 2021/6/26 18:07
 **/
@Controller
public class AuthorizationController {

    @RequestMapping("/callback")
    public String callback(String code){
        String clientId = "84a49f5bbcdb63b7b5a2";
        String clientSecret = "72c6df704c1f938ab6af79444bcfa3c0efe141a1";
        System.out.println(code);
//
//        MediaType JSON
//                = MediaType.get("application/json; charset=utf-8");
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = RequestBody.create(JSON, json);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        return "/index";
    }
}
