package it.epicode.w6d4t.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonaRequest {
    @NotNull(message = "nome obbligatorio")
    @NotEmpty(message = "nome non vuoto")
    private String nome;
    @NotNull(message = "cognome obbligatorio")
    @NotEmpty(message = "cognome non vuoto")
    private String cognome;
    private String indirizzo;
    @NotNull(message = "data nascita obbligatorio")
    //@NotEmpty(message = "data nascita non vuoto")
    private LocalDate dataNascita;
}
