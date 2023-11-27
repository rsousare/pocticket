package com.nttdata.blog.cloud.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.nttdata.blog.cloud.sample.mappers.AuthorMapper;

@Configuration
public class AppConfig {
    @Bean
    public AuthorMapper authorMapper(){
        return new AuthorMapper();
    }
}
