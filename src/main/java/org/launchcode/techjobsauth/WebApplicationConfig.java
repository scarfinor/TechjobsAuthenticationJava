package org.launchcode.techjobsauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebApplicationConfig implements WebMvcConfigurer {

    // Bean to instantiate the AuthenticationFilter
    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter();
    }

    // Register the AuthenticationFilter with Spring
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationFilter()) // Register filter
                .addPathPatterns("/**") // Apply filter to all paths
                .excludePathPatterns("/login", "/register", "/public/**");
    }
}