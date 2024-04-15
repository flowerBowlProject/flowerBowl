package com.flowerbowl.web;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final EntityManager em;
    private final DataSource dataSource;
    public SpringConfig(DataSource dataSource, EntityManager em) { // 빨간줄 인텔리제이 문제, 코드는 돌아감
        this.dataSource = dataSource;
        this.em = em;
    }

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customizer(){
        return p -> p.setOneIndexedParameters(true);
    }
}
