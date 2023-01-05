package com.xiao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiao
 */
@RestController
public class NginxController {

    @GetMapping("hello")
    public String hello(){
        return "我是nginx-a项目";
    }

    @GetMapping("get-session")
    public String getSession(HttpServletRequest req){
        return "nginx-a项目的session-id是：" + req.getSession().getId();
    }
}
