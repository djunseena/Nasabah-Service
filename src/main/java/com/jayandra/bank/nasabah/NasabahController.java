package com.jayandra.bank.nasabah;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.ws.rs.POST;
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
        nasabahService.addNewNasabah(nasabah);
    }

    @DeleteMapping("/hapusNasabah/{nomorRekening}")
    public void deleteNasabah(@PathVariable("nomorRekening") int nomorRekening) {
        nasabahService.deleteNasabah(nomorRekening);
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
    public Map<String, Object> getNomorRekening(@PathVariable("nomorRekening") int nomorRekening) {
        return nasabahService.getNomorRekening(nomorRekening);
    }

    @GetMapping("/getKontakNasabah/{nomorRekening}")
    public Map<String, Object> getKontakNasabah(@PathVariable("nomorRekening") int nomorRekening) {
        return nasabahService.getKontakNasabah(nomorRekening);
    }

    @GetMapping("/getIdKantor/{idKantor}")
    public Map<String, Object> getIdKantor(@PathVariable Long idKantor) {
//        WebClient nasabah = WebClient.create("http://10.10.30.35:7006");
        WebClient nasabah = WebClient.create("http://localhost:7006");
        WebClient.ResponseSpec responseSpec = nasabah.get()
                .uri("kantor/validasiIdKantor/" + idKantor)
                .retrieve();

        return responseSpec.bodyToMono(HashMap.class).block();
    }

    @PostMapping("/transaksi")
    public String postLogging() {
        Map<String, Object> map = nasabahService.transaksi();

//        WebClient client = WebClient.create("http://10.10.30.32:7007");
        WebClient client = WebClient.create("http://localhost:7007");
        WebClient.ResponseSpec responseSpec = client
                .post()
                .uri("/api/transaksi")
                .body(Mono.just(map), HashMap.class)
                .retrieve();

        return responseSpec.bodyToMono(String.class).block();
    }
}