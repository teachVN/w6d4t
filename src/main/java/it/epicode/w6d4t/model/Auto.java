package it.epicode.w6d4t.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nome;
    private String marca;
    private int cilindrata;

    private String logo;

    @Enumerated(EnumType.STRING)
    private Alimentazione alimentazione;

    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
}
