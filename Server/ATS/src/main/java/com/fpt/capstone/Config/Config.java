package com.fpt.capstone.Config;

import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

//@EnableAutoConfiguration(exclude = JacksonAutoConfiguration.class)
@EnableAutoConfiguration
public class Config {

//    @Bean
//    @ConditionalOnMissingBean
//    public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
//        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
//        converter.setGson(gson);
//        return converter;
//    }
}
