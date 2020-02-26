package com.ship.ship_app;

import com.github.fabiomaffioletti.firebase.EnableFirebaseRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
@EnableFirebaseRepositories
public class ShipAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShipAppApplication.class, args);

    }
    @Bean
    public RestTemplate restTemplate() { return new RestTemplateBuilder().build();
    }


}
