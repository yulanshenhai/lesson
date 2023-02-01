package com.yulanshenhai.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author JoeZhou
 */
@Configuration
public class SentinelConfig {

    /**
     * 视图解析器列表：用于展示限流之后的响应内容
     */
    private final List<ViewResolver> viewResolvers;

    /**
     * 读写器：用于操作请求和响应
     */
    private final ServerCodecConfigurer serverCodecConfigurer;

    /**
     * 有参构造器
     */
    public SentinelConfig(ObjectProvider<List<ViewResolver>> provider,
                          ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = provider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    /**
     * 管理一个全局限流过滤器，配置最高优先级
     * <bean id="sentinelGatewayFilter"
     * class="org.springframework.cloud.gateway.filter.GlobalFilter">
     *
     * @return 全局限流过滤器的实例
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    /**
     * 管理一个全局限流异常处理器，配置最高优先级
     * <bean id="sentinelGatewayBlockExceptionHandler"
     * class="com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;"/>
     *
     * @return 全局限流异常处理器的实例
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    /**
     * 在执行过滤器之前，对API网关发生限流时的响应内容进行配置
     */
    @PostConstruct
    public void initExceptionResponse() {
        GatewayCallbackManager.setBlockHandler((exchange, throwable) -> {
            Map<String, Object> map = new HashMap<>(2);
            map.put("code", 0);
            map.put("message", "QPS过高，API网关执行限流操作");
            return ServerResponse.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(map));
        });
    }

    /**
     * 在执行过滤器之前，对API请求进行分组
     */
    @PostConstruct
    public void initApiDefinition() {

        // 设置两个分组
        ApiDefinition groupA = new ApiDefinition("groupA");
        ApiDefinition groupB = new ApiDefinition("groupB");

        // 凡是 `/user-app/api/v1/**` 开头的请求，都默认归于A组
        ApiPathPredicateItem predicateItemA = new ApiPathPredicateItem();
        predicateItemA.setPattern("/user-app/api/v1/**");
        predicateItemA.setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX);
        groupA.setPredicateItems(new HashSet<>() {{
            add(predicateItemA);
        }});

        // 将 `/user-app/api/v0/test` 请求视为B组
        ApiPathPredicateItem predicateItemB = new ApiPathPredicateItem();
        predicateItemB.setPattern("/user-app/api/v0/test");
        groupB.setPredicateItems(new HashSet<>() {{
            add(predicateItemB);
        }});

        // 将自定义分组信息加载到API网关的API分组中
        Set<ApiDefinition> definitions = new HashSet<>();
        definitions.add(groupA);
        definitions.add(groupB);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    /**
     * 在执行过滤器之前，对API的每一组设置限流规则
     */
    @PostConstruct
    public void initGatewayRules() {

        // 设置A组的限流规则：QPS阈值为1，限流3秒钟
        GatewayFlowRule ruleA = new GatewayFlowRule("groupA");
        ruleA.setCount(1).setIntervalSec(3);

        // 设置A组的限流规则：QPS阈值为5，限流3秒钟
        GatewayFlowRule ruleB = new GatewayFlowRule("groupB");
        ruleB.setCount(5).setIntervalSec(3);

        // 将限流规则的set集合加载到API网关的规则管理器中
        Set<GatewayFlowRule> rules = new HashSet<>();
        rules.add(ruleA);
        rules.add(ruleB);
        GatewayRuleManager.loadRules(rules);
    }

}
