package com.yulanshenhai.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author JoeZhou
 */
//@Component
public class TokenGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 配置过滤器逻辑
     *
     * @param exchange 用于获取请求和响应实例
     * @param chain    过滤器链实例，用于放行请求
     * @return 过滤器结果
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 获取响应
        ServerHttpResponse response = exchange.getResponse();

        // 从请求参数中获取token字符串
        String token = exchange.getRequest().getQueryParams().getFirst("token");

        // 判断token字符串是否为空白字符
        if (StringUtils.isBlank(token)) {

            // 设置响应的状态码为401：401表示未认证
            response.setStatusCode(HttpStatus.UNAUTHORIZED);

            // 直接执行响应操作，相当于阻止程序运行
            return response.setComplete();
        }

        // 放行请求
        return chain.filter(exchange);
    }

    /**
     * 配置过滤器的优先级
     *
     * @return 值越小，优先级越高
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
