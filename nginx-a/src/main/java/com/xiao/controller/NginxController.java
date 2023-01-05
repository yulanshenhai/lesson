package com.xiao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiao
 */
@RestController
public class NginxController {

    @GetMapping("execute")
    public String execute(){
        return "我是nginx-a项目";
    }
}
