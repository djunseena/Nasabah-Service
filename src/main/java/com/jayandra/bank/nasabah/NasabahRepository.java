package com.jayandra.bank.nasabah;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NasabahRepository extends JpaRepository<Nasabah, Integer> {
    @Query(value = "SELECT n FROM Nasabah n WHERE n.nik = ?1")
    Optional<Nasabah> findNasabahByNik(String Nik);
}