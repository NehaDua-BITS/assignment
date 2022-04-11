package com.intuit.profilevalidationsystem.config;

import com.intuit.profilevalidationsystem.controller.ControllerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private ControllerInterceptor controllerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("\n\nAdding interceptors\n\n");
        registry.addInterceptor(controllerInterceptor).addPathPatterns("/**");
    }
}