package com.aerospike.encryptDecrypt.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class Configs {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
