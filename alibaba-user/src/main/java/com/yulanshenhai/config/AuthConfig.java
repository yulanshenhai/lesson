package com.yulanshenhai.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author JoeZhou
 */
@Component
public class AuthConfig implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        // 从请求参数中尝试获取authFlag标识
        String authFlag = httpServletRequest.getParameter("auth-flag");
        if (null == authFlag) {
            // 从请求头中尝试获取authFlag标识
            authFlag = httpServletRequest.getHeader("auth-flag");
        }
        // 返回authFlag标识
        return authFlag;
    }
}

