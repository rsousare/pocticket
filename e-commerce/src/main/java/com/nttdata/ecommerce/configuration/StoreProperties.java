package com.nttdata.ecommerce.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
@ConfigurationProperties(prefix = "store")
@Data
public class StoreProperties {

    private String name;
    private String city;
    private String country;
}
