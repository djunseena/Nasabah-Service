package com.jayandra.nasabah.service;

import com.jayandra.nasabah.model.Pesan;
import com.jayandra.nasabah.model.JenisRekening;
import com.jayandra.nasabah.model.Rekening;
import com.jayandra.nasabah.repository.JenisRekeningRepository;
import com.jayandra.nasabah.repository.RekeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RekeningService {

    private final RekeningRepository rekeningRepository;
    private final JenisRekeningRepository jenisRekeningRepository;
    private final Pesan pesan;

    @Autowired
    public RekeningService(RekeningRepository rekeningRepository, JenisRekeningRepository jenisRekeningRepository, Pesan pesan) {
        this.rekeningRepository = rekeningRepository;
        this.jenisRekeningRepository = jenisRekeningRepository;
        this.pesan = pesan;
    }

    public Rekening getRekening(int nomorRekening) {
        return rekeningRepository.findById(nomorRekening)
                .orElseThrow(() -> new IllegalStateException("Akun nasabah tidak ditemukan."));
    }

    public boolean getJenisRekening(String nik, int idJenis) {
        Rekening rekening = rekeningRepository.findRekeningByNik(nik)
                .orElseThrow(() -> new IllegalStateException("Akun nasabah tidak ditemukan."));
        JenisRekening jenisRekening = jenisRekeningRepository.getById(idJenis);

        return rekening.getIdJenis().equals(jenisRekening);
    }

    public boolean getBlockedStatus(int nomorRekening) {
        return getRekening(nomorRekening).isBlocked();
    }

    public List<Rekening> getAllRekening() {
        return rekeningRepository.findAll();
    }


    public void addNewRekening(Rekening rekening) {
        boolean nikExists = rekeningRepository.findRekeningByNik(rekening.getNik().getNik()).isPresent();
        boolean jenisExists = getJenisRekening(rekening.getNik().getNik(), rekening.getIdJenis().getIdJenis());

        if (!nikExists) {
            rekeningRepository.save(rekening);
        } else if (!jenisExists) {
            rekeningRepository.save(rekening);
        } else {
            throw new IllegalStateException("Nasabah tidak dapat memiliki dua rekening dengan jenis yang sama");
        }
    }

    @Transactional
    public void updateDataRekening(int nomorRekening, String pin, boolean blocked) {
        Rekening rekening = rekeningRepository.findById(nomorRekening)
                .orElseThrow(() -> new IllegalStateException("Rekening nasabah tidak ditemukan"));

        String oldPin = rekeningRepository.findRekeningByPin(pin).getPin();
        if (pin != null && !pin.equals(oldPin)) {
            rekening.setPin(pin);
        }

        rekening.setBlocked(blocked);
    }

    public void deleteRekening(int nomorRekening) {
        if (rekeningRepository.existsById(nomorRekening)) {
            throw new IllegalStateException("Akun nasabah tidak ditemukan.");
        }
        rekeningRepository.deleteById(nomorRekening);
    }

    public Map<String, Object> validateNomorRekening(int nomorRekening) {
        Map<String, Object> map = new HashMap<>();

        if (rekeningRepository.existsById(nomorRekening)) {
            map.put("NIK", getRekening(nomorRekening).getNik().getNik());
            map.put("nomorRekening", nomorRekening);

            if (getBlockedStatus(nomorRekening)) {
                map = pesan.createError412();
            }
        } else {
            map = pesan.createError411();
        }
        return map;
    }

    public boolean login(int nomorRekening, String pin) {
        Rekening rekening = getRekening(nomorRekening);

        return rekening.getPin().equals(pin);
    }
}
