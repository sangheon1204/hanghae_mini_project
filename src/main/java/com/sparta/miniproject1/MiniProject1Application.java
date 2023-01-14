package com.sparta.miniproject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MiniProject1Application {

    public static void main(String[] args) {
        SpringApplication.run(MiniProject1Application.class, args);
    }

}
