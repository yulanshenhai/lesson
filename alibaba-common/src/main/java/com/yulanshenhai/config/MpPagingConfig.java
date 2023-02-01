package com.yulanshenhai.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JoeZhou
 */
@Configuration
public class MpPagingConfig {

    /**
     * 管理 [Mp的拦截器]
     *
     * @return [Mp的拦截器] 实例，包含了 [Mp分页相关的内置拦截器]
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        // 创建 [Mp的拦截器]
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();

        // 创建 [Mp分页相关的内置拦截器]
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);

        // 组装 [Mp的拦截器]：将 [Mp分页相关的内置拦截器] 添加到 [Mp的拦截器] 中
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);

        // 返回 [Mp的拦截器]
        return mybatisPlusInterceptor;
    }

}
