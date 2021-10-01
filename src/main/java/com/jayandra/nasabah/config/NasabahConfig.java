package com.jayandra.nasabah.config;

import com.jayandra.nasabah.model.Nasabah;
import com.jayandra.nasabah.repository.NasabahRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class NasabahConfig {
    @Bean
    CommandLineRunner commandLineRunner(NasabahRepository repository) {

        return args -> {
            Nasabah a = new Nasabah("12345", "A");
            Nasabah b = new Nasabah("23456", "B");

            repository.saveAll(List.of(a, b));
        };
    }
}
