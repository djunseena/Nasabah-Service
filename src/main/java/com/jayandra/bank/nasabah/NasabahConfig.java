package com.jayandra.bank.nasabah;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class NasabahConfig {
    @Bean
    CommandLineRunner commandLineRunner(NasabahRepository repository) {

        return args -> {
            Nasabah a = new Nasabah(1, "A", "1234", "12345");
            Nasabah b = new Nasabah(2, "B", "1234", "23456");

        repository.saveAll(List.of(a, b));
        };
    }
}
