package com.jayandra.bank;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Status {

    public Map<String, Object> error411() {
        Map<String, Object> map = new HashMap<>();

        map.put("status", 411);
        map.put("pesan", "Akun nasabah tidak ditemukan.");

        return map;
    }

    public Map<String, Object> error412() {
        Map<String, Object> map = new HashMap<>();

        map.put("status", 412);
        map.put("pesan", "Akun nasabah diblokir.");

        return map;
    }
}
