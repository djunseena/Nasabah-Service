package com.jayandra.nasabah.controller;

import com.jayandra.nasabah.model.Nasabah;
import com.jayandra.nasabah.service.NasabahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nasabah")
public class NasabahController {
    private final NasabahService nasabahService;
    private final PesanController pesanController;

    @Autowired
    public NasabahController(NasabahService nasabahService, PesanController pesanController) {
        this.nasabahService = nasabahService;
        this.pesanController = pesanController;
    }

    @GetMapping("/getNasabah/{nik}")
    public Nasabah getNasabah(@RequestBody String nik) {
        return nasabahService.getNasabah(nik);
    }

    @GetMapping("/getAllNasabah")
    public List<Nasabah> getAllNasabah() {
        return nasabahService.getAllNasabah();
    }

    @PostMapping("/addNasabah")
    public void addNewNasabah(@RequestBody Nasabah nasabah) {
        nasabahService.addNewNasabah(nasabah);
    }

    @DeleteMapping("/deleteNasabah/{nik}")
    public void deleteNasabah(@PathVariable("nik") String nik) {
        nasabahService.deleteNasabah(nik);
    }
}