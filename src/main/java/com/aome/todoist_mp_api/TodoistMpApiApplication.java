package com.aome.todoist_mp_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.aome.todoist_mp_api.config")
public class TodoistMpApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoistMpApiApplication.class, args);
    }

}
