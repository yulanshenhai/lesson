package com.yulanshenhai.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author JoeZhou
 */
@Slf4j
@Component
public class ReqGatewayFilterFactory extends AbstractGatewayFilterFactory<ReqGatewayFilterFactory.Config> {

    /**
     * 重写无参构造器，将配置类的类对象传递给父类
     */
    public ReqGatewayFilterFactory() {
        super(Config.class);
    }

    /**
     * 配置过滤逻辑
     *
     * @param config 内部配置类
     * @return 过滤结果
     */
    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            // 获取当前的请求
            ServerHttpRequest request = exchange.getRequest();

            if (config.getRequestHeader()) {
                log.info("请求头中a的值为: {}", request.getHeaders().get("a"));
            }

            if (config.getRequestParam()) {
                log.info("请求参数中b的值为: {}", request.getQueryParams().get("b"));
            }

            // 放行请求到下一个过滤器
            return chain.filter(exchange);
        };
    }

    /**
     * 负责读取主配中的Req值，并且对位赋值给Config类的两个属性
     */
    @Override
    public List<String> shortcutFieldOrder() {
        // 将主配中的Req属性的第1个值，对位赋值给内部类的requestHeader属性
        // 将主配中的Req属性的第2个值，对位赋值给内部类的requestParam属性
        return Arrays.asList("requestHeader", "requestParam");
    }

    /**
     * 内部类用于接收和存储Reg的两个值：requestHeader和requestParam
     */
    @Data
    static class Config {
        private Boolean requestHeader;
        private Boolean requestParam;
    }
}
