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
public class Nasabah {

    @Id
    private String nik;
    private String namaNasabah;
}