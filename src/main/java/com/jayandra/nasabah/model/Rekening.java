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
public class Rekening {

    @Id
    @SequenceGenerator(
            name = "rekening_sequence",
            sequenceName = "rekening_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rekening_sequence"
    )
    private int nomorRekening;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nik_nasabah", nullable = false)
    private Nasabah nik;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_jenis_rekening", nullable = false)
    private JenisRekening idJenis;

    private String pin;
    private boolean blocked;
}