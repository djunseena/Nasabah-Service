package com.jayandra.nasabah.controller;

import com.jayandra.nasabah.model.Rekening;
import com.jayandra.nasabah.service.RekeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rekening")
public class RekeningController {
    private final RekeningService rekeningService;
    private final PesanController pesanController;

    @Autowired
    public RekeningController(RekeningService rekeningService, PesanController pesanController) {
        this.rekeningService = rekeningService;
        this.pesanController = pesanController;
    }

    @GetMapping("/getRekening/{noRek}")
    public Rekening getNasabah(@RequestBody int noRek) {
        return rekeningService.getRekening(noRek);
    }

    @GetMapping("/getAllRekening")
    public List<Rekening> getAllRekening() {
        return rekeningService.getAllRekening();
    }

    @PostMapping("/addRekening")
    public void addNewRekening(@RequestBody Rekening rekening) {
        rekeningService.addNewRekening(rekening);
    }

    @PutMapping("/updateDataRekening/{noRek}")
    public void updateDataRekening(
            @PathVariable("noRek") int noRek,
            @RequestParam String pin,
            @RequestParam boolean blocked
    ) {
        rekeningService.updateDataRekening(noRek, pin, blocked);
    }

    @DeleteMapping("/deleteRekening/{noRek}")
    public void deleteRekening(@PathVariable("nomorRekening") int noRek) {
        try {
            rekeningService.deleteRekening(noRek);
            pesanController.postLogging(5, 1);
        } catch (Exception e) {
            pesanController.postLogging(5, 2);
        }
    }

    @GetMapping("/validateRekening/{noRek}")
    public Map<String, Object> validasiNomorRekening(@PathVariable("noRek") int noRek) {
        return rekeningService.validateNomorRekening(noRek);
    }

    @GetMapping("/login")
    public void login(
            @RequestBody int noRek,
            @RequestBody String pin) {
        rekeningService.login(noRek, pin);
    }
}
