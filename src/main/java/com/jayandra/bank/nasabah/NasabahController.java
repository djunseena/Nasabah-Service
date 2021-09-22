package com.jayandra.bank.nasabah;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/nasabah")
public class NasabahController {
    private final NasabahService nasabahService;

    @Autowired
    public NasabahController(NasabahService nasabahService) {
        this.nasabahService = nasabahService;
    }

    @GetMapping
    public List<Nasabah> getNasabah() {
        return nasabahService.getNasabah();
    }

    @PostMapping
    public void registerNewNasabah(@RequestBody Nasabah nasabah) {
        nasabahService.addNewNasabah(nasabah);
    }

    @DeleteMapping(path = "{nomorNasabah}")
    public void deleteNasabah(@PathVariable("nomorNasabah") int nomorNasabah) {
        nasabahService.deleteNasabah(nomorNasabah);
    }

    @PutMapping(path = "{nomorNasabah}")
    public void updateDataNasabah(
            @PathVariable("nomorNasabah") int nomorNasabah,
            @RequestParam(required = false) String namaNasabah,
            @RequestParam(required = false) String pin,
            @RequestParam(required = false) String nik
    ) {
        nasabahService.updateDataNasabah(nomorNasabah, namaNasabah, pin, nik);
    }
}