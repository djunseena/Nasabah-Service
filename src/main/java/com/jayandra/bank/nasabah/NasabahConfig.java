package com.jayandra.bank.nasabah;

import com.google.common.net.HttpHeaders;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Configuration
public class NasabahConfig {
    @Bean
    CommandLineRunner commandLineRunner(NasabahRepository repository) {

        return args -> {
            Nasabah a = new Nasabah(1, "A", "1234", "a@gmail.com", "1234567890", 1, true, 1L);
            Nasabah b = new Nasabah(2, "B", "1234", "b@gmail.com", "1234567890", 2, false, 1L);

            repository.saveAll(List.of(a, b));
        };
    }
}
