package com.jayandra.bank.nasabah;

import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Nasabah {

    @Id
    @SequenceGenerator(
            name = "nasabah_sequence",
            sequenceName = "nasabah_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "nasabah_sequence"
    )
    private int nomorRekening;
    private String namaNasabah;
    private String pin;
    private String email;
    private String noTelp;
    private int status;
    private boolean blokir;
    private Long idKantor;
}