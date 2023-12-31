package com.movieapp.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Configuration
public class ErrorConfig {

  @Bean
  public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {

    SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();

    Properties properties = new Properties();
    properties.setProperty(IllegalArgumentException.class.getSimpleName(), "500");

    resolver.setExceptionMappings(properties);

    return resolver;
  }
}