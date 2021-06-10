package com.github.tgiachi.diamond.homecontrol.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.text.SimpleDateFormat;

@Configuration
public class SerializerConfig {

    @Bean
    @Primary
    ObjectMapper getObjectMapper() {
        var mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return mapper.findAndRegisterModules();
    }

    @Bean
    @Qualifier("yaml")
    ObjectMapper yamlObjectMapper() {
        var mapper = new ObjectMapper(new YAMLFactory());
        return mapper.findAndRegisterModules();

    }
}
