package com.jayandra.nasabah.repository;

import com.jayandra.nasabah.model.Rekening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RekeningRepository extends JpaRepository<Rekening, Integer> {

    @Query(value = "SELECT r FROM Rekening r WHERE r.nik = ?1")
    Optional<Rekening> findRekeningByNik(String nik);

    @Query(value = "SELECT r FROM Rekening r WHERE r.idJenis = ?1")
    Rekening findRekeningByIdJenis(int idJenis);

    @Query(value = "SELECT r FROM Rekening r WHERE r.pin = ?1")
    Rekening findRekeningByPin(String pin);

}
