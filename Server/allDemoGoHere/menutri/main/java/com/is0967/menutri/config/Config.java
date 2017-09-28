package com.is0967.menutri.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.is0967.menutri.repositories")
@EntityScan(basePackages = "com.is0967.menutri.entities")
@ComponentScan(basePackages = "com.is0967.menutri.services")
public class Config {
}
