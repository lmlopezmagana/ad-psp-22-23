package com.salesianostriana.dam.controller.web.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@TestConfiguration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurationSupport {

    @Bean
    public MultipartResolver multipartResolver() {
        //return new CommonsMultipartResolver();
        return new StandardServletMultipartResolver();
    }

}
