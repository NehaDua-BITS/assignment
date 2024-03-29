package com.intuit.profilevalidationsystem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class ProductAPIConfig {

    @Value("${product.base.url}")
    private String url;

}
