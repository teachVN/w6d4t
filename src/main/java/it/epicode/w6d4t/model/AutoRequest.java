package it.epicode.w6d4t.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AutoRequest {
    @NotNull(message = "Nome obbligatorio")
    @NotEmpty(message = "Nome non vuoto")
    private String nome;
    @NotNull(message = "marca obbligatorio")
    @NotEmpty(message = "marca non vuoto")
    private String marca;
    private int cilindrata;

    private Alimentazione alimentazione;

    @NotNull(message = "Persona obbligatoria")
    private Integer idPersona;
}
