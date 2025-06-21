package com.inventory.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    //model mapper bean
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
