package com.example.gymAp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@ComponentScan("com.example.gymAp")
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = "com.example.gymAp.dao")
@PropertySource(value = "classpath:application.properties")
public class AppConfig implements WebMvcConfigurer {

//    @Override
//    public void addViewControllers(final ViewControllerRegistry registry) {
//
//        registry.addViewController("/").setViewName("index");
//
//        registry.addViewController("/swagger-ui/").setViewName("forward:/swagger-ui/index.html");
//
//    }
//
//    @Override
//    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//
//        registry.addResourceHandler("/swagger-ui/**")
//
//                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
//
//                .resourceChain(false);
//
//    }
}
