package com.jayandra.bank.nasabah;

import com.jayandra.bank.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class NasabahService {

    private final NasabahRepository nasabahRepository;
    private final Status pesan;

    @Autowired
    public NasabahService(NasabahRepository nasabahRepository, Status pesan) {
        this.nasabahRepository = nasabahRepository;
        this.pesan = pesan;
    }

    public String getNamaNasabah(int nomorRekening) {
        Nasabah nasabah = nasabahRepository.findById(nomorRekening)
                .orElseThrow(IllegalStateException::new);

        return nasabah.getNamaNasabah();
    }

    public String getEmailNasabah(int nomorRekening) {
        Nasabah nasabah = nasabahRepository.findById(nomorRekening)
                .orElseThrow(IllegalStateException::new);

        return nasabah.getEmail();
    }

    public String getNoTelpNasabah(int nomorRekening) {
        Nasabah nasabah = nasabahRepository.findById(nomorRekening)
                .orElseThrow(IllegalStateException::new);


        return nasabah.getNoTelp();
    }

    public boolean getStatusBlokir(int nomorRekening) {
        Nasabah nasabah = nasabahRepository.findById(nomorRekening)
                .orElseThrow(IllegalStateException::new);


        return nasabah.isBlokir();
    }


    public List<Nasabah> getAllNasabah() {
        return nasabahRepository.findAll();
    }

    public void addNewNasabah(Nasabah nasabah) {
        nasabahRepository.save(nasabah);
    }

    public void deleteNasabah(int nomorRekening) {
        boolean exists = nasabahRepository.existsById(nomorRekening);
        if (!exists) {
            throw new IllegalStateException(pesan.error411().values().toString());
        }
        nasabahRepository.deleteById(nomorRekening);
    }

    @Transactional
    public void updateDataNasabah(
            int nomorRekening,
            String namaNasabah,
            String pin,
            String email,
            String noTelp,
            int status,
            boolean blokir,
            Long idKantor) {
        Nasabah nasabah = nasabahRepository.findById(nomorRekening)
                .orElseThrow(() -> new IllegalStateException(pesan.error411().values().toString()));

        if (namaNasabah != null) {
            nasabah.setNamaNasabah(namaNasabah);
        }

        if (pin != null) {
            nasabah.setPin(pin);
        }

        if (email != null) {
            nasabah.setEmail(email);
        }

        if (noTelp != null) {
            nasabah.setNoTelp(noTelp);
        }

        nasabah.setStatus(status);
        nasabah.setBlokir(blokir);
        nasabah.setIdKantor(idKantor);
    }

    public Map<String, Object> validasiNomorRekening(int nomorRekening) {
        Map<String, Object> map = new HashMap<>();

        if (nasabahRepository.existsById(nomorRekening)) {
            map.put("nomorRekening", nomorRekening);
            map.put("namaNasabah", getNamaNasabah(nomorRekening));

            if (getStatusBlokir(nomorRekening)) {
                map = pesan.error412();
            }
        } else {
            map = pesan.error411();
        }
        return map;
    }

    public Map<String, Object> getKontakNasabah(int nomorRekening) {
        Map<String, Object> map = new HashMap<>();

        if (nasabahRepository.existsById(nomorRekening)) {
            map.put("nomorRekening", nomorRekening);
            map.put("namaNasabah", getNamaNasabah(nomorRekening));
            map.put("email", getEmailNasabah(nomorRekening));
            map.put("noTelp", getNoTelpNasabah(nomorRekening));
        } else if (getStatusBlokir(nomorRekening)) {
            map = pesan.error412();
        }
        return map;
    }

    public Map<String, Object> pesanTransaksi(int jenisTransaksi, int statusTransaksi) {
        Map<String, Object> map = new HashMap<>();

        map.put("nomorRekening", 0);
        map.put("jenisTransaksi", jenisTransaksi);
        map.put("statusTransaksi", statusTransaksi);
        map.put("logTransaksi", "transaksi nasabah");

        return map;
    }
}