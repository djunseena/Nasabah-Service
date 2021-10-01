package com.jayandra.nasabah.controller;

import com.jayandra.nasabah.model.Pesan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pesan")
public class PesanController {

    private final Pesan pesan;


    @Autowired
    public PesanController(Pesan pesan) {
        this.pesan = pesan;
    }

    @PostMapping("/transaksi")
    public String postLogging(int jenis, int status) {
        Map<String, Object> map = pesan.createLog(jenis, status);

        WebClient client = WebClient.create("http://localhost:7007");
        WebClient.ResponseSpec responseSpec = client
                .post()
                .uri("/api/transaksi")
                .body(Mono.just(map), HashMap.class)
                .retrieve();

        return responseSpec.bodyToMono(String.class).block();
    }
}
