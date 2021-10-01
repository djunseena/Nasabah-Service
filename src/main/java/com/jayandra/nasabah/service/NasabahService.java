package com.jayandra.nasabah.service;

import com.jayandra.nasabah.model.Pesan;
import com.jayandra.nasabah.model.Nasabah;
import com.jayandra.nasabah.repository.NasabahRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NasabahService {

    private final NasabahRepository nasabahRepository;
    private final Pesan pesan;

    @Autowired
    public NasabahService(NasabahRepository nasabahRepository, Pesan pesan) {
        this.nasabahRepository = nasabahRepository;
        this.pesan = pesan;
    }

    public Nasabah getNasabah(String nik) {
        return nasabahRepository.findById(nik)
                .orElseThrow(() -> new IllegalStateException("Akun nasabah tidak ditemukan."));
    }

    public List<Nasabah> getAllNasabah() {
        return nasabahRepository.findAll();
    }

    public void addNewNasabah(Nasabah nasabah) {
        nasabahRepository.save(nasabah);
    }

    public void deleteNasabah(String nik) {
        if (nasabahRepository.existsById(nik)) {
            pesan.createError412();
        }
        nasabahRepository.deleteById(nik);
    }

    public boolean validateNasabah(String nik) {
        return nasabahRepository.existsById(nik);
    }
}