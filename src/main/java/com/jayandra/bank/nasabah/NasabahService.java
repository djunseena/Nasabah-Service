package com.jayandra.bank.nasabah;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NasabahService {

    private final NasabahRepository nasabahRepository;

    @Autowired
    public NasabahService(NasabahRepository nasabahRepository) {
        this.nasabahRepository = nasabahRepository;
    }

    public List<Nasabah> getNasabah() {
        return nasabahRepository.findAll();
    }

    public void addNewNasabah(Nasabah nasabah) {
        Optional<Nasabah> nasabahOptional = nasabahRepository.findNasabahByNik(nasabah.getNik());
        if (nasabahOptional.isPresent()) {
            throw new IllegalStateException("NIK ga boleh sama ya");
        }
        nasabahRepository.save(nasabah);
    }

    public void deleteNasabah(int nomorNasabah) {
        boolean exists = nasabahRepository.existsById(nomorNasabah);
        if (!exists) {
            throw new IllegalStateException("Nasabah dengan id" + nomorNasabah + "tidak ada.");
        }
        nasabahRepository.deleteById(nomorNasabah);
    }

    @Transactional
    public void updateDataNasabah(int nomorNasabah, String namaNasabah, String pin, String nik) {
        Nasabah nasabah = nasabahRepository.findById(nomorNasabah)
                .orElseThrow(() -> new IllegalStateException(
                        "Nasabah dengan id" + nomorNasabah + "tidak ada."
                ));

        if (namaNasabah != null &&
                namaNasabah.length() > 0 &&
                !Objects.equals(nasabah.getNamaNasabah(), namaNasabah)) {
            nasabah.setNamaNasabah(namaNasabah);
        }

        if (pin != null &&
                pin.length() > 0 &&
                pin.length() < 5) {
            nasabah.setPin(pin);
        }

        if (nik != null &&
                nik.length() > 0 &&
                nik.length() < 6 &&
                !Objects.equals(nasabah.getNik(), nik)) {
            nasabah.setNik(nik);
        }
    }
}
