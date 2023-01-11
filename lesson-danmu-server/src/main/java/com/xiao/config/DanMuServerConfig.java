package com.xiao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * @author xiao
 */
@Configuration
public class DanMuServerConfig {

    /**
     * 该bean会自动扫描@ServerEndpoint注解并使其生效
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}