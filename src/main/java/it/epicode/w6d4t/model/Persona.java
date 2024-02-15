package it.epicode.w6d4t.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nome;
    private String cognome;
    private String indirizzo;
    private LocalDate dataNascita;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "persona")
    private List<Auto> auto;
}
