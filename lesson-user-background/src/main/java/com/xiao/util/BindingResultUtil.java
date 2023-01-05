package com.xiao.util;

import org.springframework.validation.BindingResult;


public class BindingResultUtil {
    public static void check(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                throw new RuntimeException(error.getObjectName() + "." +
                        error.getField() + "校验失败: " + error.getDefaultMessage());
            });
        }
    }
}
