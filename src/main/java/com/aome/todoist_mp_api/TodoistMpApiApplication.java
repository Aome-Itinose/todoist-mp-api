package com.aome.todoist_mp_api;

import com.aome.todoist_mp_api.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class TodoistMpApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoistMpApiApplication.class, args);
    }

}
