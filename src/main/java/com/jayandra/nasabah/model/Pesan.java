package com.jayandra.nasabah.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Pesan {
    private int jenis;
    private String status;

    public Map<String, Object> createPesan(int jenis, String status) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("jenis", jenis);
        map.put("status", status);

        return map;
    }

    public Map<String, Object> createLog(int jenis, int log) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("nomorRekening", 0);
        map.put("jenisTransaksi", jenis);
        map.put("statusTransaksi", log);
        map.put("logTransaksi", "transaksi nasabah");

        return map;
    }

    public Map<String, Object> createError411() {
        return createPesan(411, "Akun nasabah tidak ditemukan.");
    }

    public Map<String, Object> createError412() {
        return createPesan(412, "Akun nasabah diblokir.");
    }

    public Map<String, Object> createError413() {
        return createPesan(413, "Akun nasabah telah memiliki rekening dengan jenis tersebut");
    }
}