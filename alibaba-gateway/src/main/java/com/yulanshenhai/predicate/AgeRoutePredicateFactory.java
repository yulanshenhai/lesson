package com.yulanshenhai.predicate;

import lombok.Data;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author JoeZhou
 */
@Component
public class AgeRoutePredicateFactory extends AbstractRoutePredicateFactory<AgeRoutePredicateFactory.Config> {

    /**
     * 将内部配置类传递给父类进行处理
     */
    public AgeRoutePredicateFactory() {
        super(Config.class);
    }

    /**
     * 用于接收主配断言配置中的 `Age` 值
     */
    @Data
    static class Config {
        private Integer minAge;
        private Integer maxAge;
    }

    /**
     * 用于将主配断言配置中的 `Age` 值赋值给内部类属性
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("minAge", "maxAge");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {

        return serverWebExchange -> {
            String ageStr = serverWebExchange.getRequest().getQueryParams().getFirst("age");

            // 若请求中没有携带age参数，则直接放行
            if (null == ageStr) {
                return true;
            }

            // 判断age是否符合要求（在主配中配置的范围）
            int age = Integer.parseInt(ageStr);
            return age > config.getMinAge() && age < config.getMaxAge();
        };
    }


}
