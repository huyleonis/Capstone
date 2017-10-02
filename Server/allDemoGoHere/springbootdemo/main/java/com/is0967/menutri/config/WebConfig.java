package com.is0967.menutri.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.lang.reflect.Modifier;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

   @Override
   public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**").allowedMethods("OPTION", "POST", "GET", "PUT", "PATCH", "DELETE");
   }

   @Override
   public void configureMessageConverters(List<HttpMessageConverter< ? >> converters) {
      GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
      Gson gson = new GsonBuilder()
              .enableComplexMapKeySerialization()
              .excludeFieldsWithModifiers(Modifier.TRANSIENT)
              .create();
      converter.setGson(gson);
      converters.add(converter);
   }

}
