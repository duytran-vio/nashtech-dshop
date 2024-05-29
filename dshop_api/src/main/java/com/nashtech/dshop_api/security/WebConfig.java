package com.nashtech.dshop_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration thisconfig = new CorsConfiguration();
        thisconfig.setAllowCredentials(true);
        thisconfig.addAllowedOrigin("http://localhost:3000");
        thisconfig.setAllowedHeaders((Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT)));
        thisconfig.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()));
        thisconfig.setMaxAge(3600L);
        thisconfig.setAllowedHeaders(List.of("Authorization", "Content-type"));
        source.registerCorsConfiguration("/**", thisconfig);
        // FilterRegistrationBean bean = new FilterRegistrationBean(new
        // CorsFilter(source));
        // FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        // bean.setOrder(-102);
        // return bean;
        return new CorsFilter(source);
    }
}