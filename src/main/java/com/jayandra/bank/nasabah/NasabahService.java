package com.jayandra.bank.nasabah;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class NasabahService {

    private final NasabahRepository nasabahRepository;

    @Autowired
    public NasabahService(NasabahRepository nasabahRepository) {
        this.nasabahRepository = nasabahRepository;
    }

    public String getNamaNasabah(int nomorRekening) {
        Nasabah nasabah = nasabahRepository.findById(nomorRekening)
                .orElseThrow(() -> new IllegalStateException(
                        "Nasabah dengan nomor rekening " + nomorRekening + " tidak ada."
                ));

        return nasabah.getNamaNasabah();
    }

    public String getEmailNasabah(int nomorRekening) {
        Nasabah nasabah = nasabahRepository.findById(nomorRekening)
                .orElseThrow(() -> new IllegalStateException(
                        "Nasabah dengan nomor rekening " + nomorRekening + " tidak ada."
                ));

        return nasabah.getEmail();
    }

    public String getNoTelpNasabah(int nomorRekening) {
        Nasabah nasabah = nasabahRepository.findById(nomorRekening)
                .orElseThrow(() -> new IllegalStateException(
                        "Nasabah dengan nomor rekening " + nomorRekening + " tidak ada."
                ));

        return nasabah.getNoTelp();
    }

    public boolean getStatusBlokir(int nomorRekening) {
        Nasabah nasabah = nasabahRepository.findById(nomorRekening)
                .orElseThrow(() -> new IllegalStateException(
                        "Nasabah dengan nomor rekening " + nomorRekening + " tidak ada."
                ));

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
            throw new IllegalStateException("Nasabah dengan id" + nomorRekening + "tidak ada.");
        }
        nasabahRepository.deleteById(nomorRekening);
    }

    @Transactional
    public void updateDataNasabah(int nomorRekening, String namaNasabah, String pin, String email,
                                  String noTelp, int status, boolean blokir, Long idKantor) {
        Nasabah nasabah = nasabahRepository.findById(nomorRekening)
                .orElseThrow(() -> new IllegalStateException(
                        "Nasabah dengan id" + nomorRekening + "tidak ada."
                ));

        if (namaNasabah != null && namaNasabah.length() > 0) {
            nasabah.setNamaNasabah(namaNasabah);
        }

        if (pin != null && pin.length() > 0 && pin.length() < 5) {
            nasabah.setPin(pin);
        }

        if (email != null && noTelp.length() > 0) {
            nasabah.setEmail(email);
        }

        if (noTelp != null && noTelp.length() > 0) {
            nasabah.setNoTelp(noTelp);
        }

        nasabah.setStatus(status);
        nasabah.setBlokir(blokir);
        nasabah.setIdKantor(idKantor);
    }

    public Map<String, Object> getNomorRekening(int nomorRekening) {
        Map<String, Object> map = new HashMap<>();

        if (nasabahRepository.existsById(nomorRekening)) {
            map.put("nomorRekening:", nomorRekening);
            map.put("namaNasabah:", getNamaNasabah(nomorRekening));

            if (getStatusBlokir(nomorRekening)) {
                map.put("status:", 412);
                map.put("pesan:", "Akun nasabah diblokir.");
            }
        } else {
            map.put("status:", 411);
            map.put("pesan:", "Akun nasabah tidak ditemukan.");
        }
        return map;
    }

    public Map<String, Object> getKontakNasabah(int nomorRekening) {
        Map<String, Object> map = new HashMap<>();

        if (nasabahRepository.existsById(nomorRekening)) {
            map.put("nomorRekening:", nomorRekening);
            map.put("namaNasabah:", getNamaNasabah(nomorRekening));
            map.put("email:", getEmailNasabah(nomorRekening));
            map.put("noTelp:", getNoTelpNasabah(nomorRekening));
        } else if (getStatusBlokir(nomorRekening)) {
            map.put("status:", 412);
            map.put("pesan:", "Akun nasabah diblokir.");
        }
        return map;
    }

    public void getIdKantor(Long idKantor) {
//        WebClient nasabah = WebClient.create("http://10.10.30.35:7006");
        WebClient nasabah = WebClient.create("http://localhost:7006");
        WebClient.ResponseSpec responseSpec = nasabah.get()
                .uri("/kantor/validasiIdKantor/" + idKantor)
                .retrieve();

        Object response = responseSpec.bodyToMono(Object.class).block();
        System.out.println(response.toString());
    }

    public String makeLogging() {
//        WebClient client = WebClient.create("http://10.10.30.32:7002");
        WebClient client = WebClient.create("http://localhost:7002");

        HashMap map = new HashMap();
        map.put("nomorRekening:", 1);
        map.put("namaNasabah:", "A");

        WebClient.ResponseSpec responseSpec = client.put()
                .uri("/tabungan/kurangi_saldo")
                .body(Mono.just(map), HashMap.class)
                .retrieve();

        String responseBody = responseSpec.bodyToMono(String.class).block();

        return responseBody;
    }
}