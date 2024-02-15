package it.epicode.w6d4t.service;


import it.epicode.w6d4t.exception.NotFoundException;

import it.epicode.w6d4t.model.Persona;
import it.epicode.w6d4t.model.PersonaRequest;
import it.epicode.w6d4t.repository.PersonaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class PersonaService {
    @Autowired
    private PersonaRepository personaRepository;


    public Page<Persona> getAll(Pageable pageable) {

        return personaRepository.findAll(pageable);
    }

    public Persona getPersonaById(int id) throws NotFoundException {
        return personaRepository.findById(id).orElseThrow(() -> new NotFoundException("Persona con id=" + id + " non trovata"));
    }

    public Persona savePersona(PersonaRequest personaRequest)  {
        Persona persona = new Persona();
        persona.setIndirizzo(personaRequest.getIndirizzo());
        persona.setNome(personaRequest.getNome());
        persona.setCognome(personaRequest.getCognome());
        persona.setDataNascita(personaRequest.getDataNascita());

        return personaRepository.save(persona);
    }

    public Persona updatePersona(int id, PersonaRequest personaRequest) throws NotFoundException {
        Persona p = getPersonaById(id);

        p.setNome(personaRequest.getNome());
        p.setCognome(personaRequest.getCognome());
        p.setIndirizzo(personaRequest.getIndirizzo());
        p.setDataNascita(personaRequest.getDataNascita());

        return personaRepository.save(p);
    }

    public void deletePersona(int id) throws NotFoundException {
        Persona persona = getPersonaById(id);
        personaRepository.delete(persona);
    }



}
