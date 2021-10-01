package com.jayandra.nasabah.model;

import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class JenisRekening {

    @Id
    @SequenceGenerator(
            name = "jenis_rekening_sequence",
            sequenceName = "jenis_rekening_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "jenis_rekening_sequence"
    )
    private int idJenis;
    private String namaJenisRekening;
}