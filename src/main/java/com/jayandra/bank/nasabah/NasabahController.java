package com.jayandra.bank.nasabah;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nasabah")
public class NasabahController {
    private final NasabahService nasabahService;

    @Autowired
    public NasabahController(NasabahService nasabahService) {
        this.nasabahService = nasabahService;
    }

    @GetMapping
    public List<Nasabah> getNasabah() {
        return nasabahService.getAllNasabah();
    }

    @PostMapping
    public void addNewNasabah(@RequestBody Nasabah nasabah) {
        try {
            nasabahService.addNewNasabah(nasabah);
            postLogging(4, 1);
        } catch (Exception e) {
            postLogging(4, 2);
        }
    }

    @DeleteMapping("/hapusNasabah/{nomorRekening}")
    public void deleteNasabah(@PathVariable("nomorRekening") int nomorRekening) {
        try {
            nasabahService.deleteNasabah(nomorRekening);
            postLogging(5, 1);
        } catch (Exception e) {
            postLogging(5, 2);
        }
    }

    @PutMapping("/ubahData/{nomorRekening}")
    public void updateDataNasabah(
            @PathVariable("nomorRekening") int nomorRekening,
            @RequestParam(required = false) String namaNasabah,
            @RequestParam(required = false) String pin,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String noTelp,
            @RequestParam(required = false) int status,
            @RequestParam(required = false) boolean blokir,
            @RequestParam(required = false) Long idKantor
    ) {
        nasabahService.updateDataNasabah(nomorRekening, namaNasabah, pin, email, noTelp, status, blokir, idKantor);
    }

    @GetMapping("/validasiNomorRekening/{nomorRekening}")
    public Map<String, Object> validasiNomorRekening(@PathVariable("nomorRekening") int nomorRekening) {
        return nasabahService.validasiNomorRekening(nomorRekening);
    }

    @GetMapping("/getKontakNasabah/{nomorRekening}")
    public Map<String, Object> getKontakNasabah(@PathVariable("nomorRekening") int nomorRekening) {
        return nasabahService.getKontakNasabah(nomorRekening);
    }

    @GetMapping("/getIdKantor/{idKantor}")
    public Map<String, Object> getIdKantor(@PathVariable Long idKantor) {
        WebClient nasabah = WebClient.create("http://localhost:7006");
        WebClient.ResponseSpec responseSpec = nasabah.get()
                .uri("kantor/validasiIdKantor/" + idKantor)
                .retrieve();

        return responseSpec.bodyToMono(Map.class).block();
    }

    @PostMapping("/transaksi")
    public String postLogging(int jenisTransaksi, int statusTransaksi) {
        Map<String, Object> map = nasabahService.pesanTransaksi(jenisTransaksi, statusTransaksi);

        WebClient client = WebClient.create("http://localhost:7007");
        WebClient.ResponseSpec responseSpec = client
                .post()
                .uri("/api/transaksi")
                .body(Mono.just(map), HashMap.class)
                .retrieve();

        return responseSpec.bodyToMono(String.class).block();
    }
}