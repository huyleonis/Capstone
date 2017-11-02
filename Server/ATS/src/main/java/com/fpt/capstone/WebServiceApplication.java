package com.fpt.capstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@ComponentScan(basePackages = "com.fpt.capstone")
@EnableJpaRepositories(basePackages = "com.fpt.capstone.Repositories")
@EntityScan(basePackages = "com.fpt.capstone.Entities")
public class WebServiceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebServiceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WebServiceApplication.class, args);
    }
}
