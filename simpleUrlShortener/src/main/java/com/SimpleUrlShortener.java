package com;

import com.abstractkamen.configuration.DbConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan({"com.abstractkamen.configuration",
        "com.abstractkamen.simpleurlshortener.dao",
        "com.abstractkamen.simpleurlshortener.web.rest"})
@SpringBootApplication
@EnableConfigurationProperties(DbConfig.class)
public class SimpleUrlShortener {
    public static void main(String[] args) {
        SpringApplication.run(SimpleUrlShortener.class, args);
    }
}
