package com.jayandra.bank.nasabah;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;
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

    @GetMapping("/getIdKantor/{idKantor]")
    public void getIdKantor(@PathVariable Long idKantor) {
        nasabahService.getIdKantor(idKantor);
    }

//    @PostMapping("/api/transaksi")
//    public String postLogging() {
//        return nasabahService.makeLogging();
//    }
}