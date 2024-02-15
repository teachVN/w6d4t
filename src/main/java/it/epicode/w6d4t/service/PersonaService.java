package it.epicode.w6d4t.service;


import it.epicode.w6d4t.exception.NotFoundException;

import it.epicode.w6d4t.model.Persona;
import it.epicode.w6d4t.model.PersonaRequest;
import it.epicode.w6d4t.repository.PersonaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class PersonaService {
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private JavaMailSenderImpl javaMailSender;


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
        persona.setEmail(personaRequest.getEmail());
        sendMail(persona.getEmail());

        return personaRepository.save(persona);
    }

    private void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione Servizio rest");
        message.setText("Registrazione al servizio rest avvenuta con successo");

        javaMailSender.send(message);
    }

    public Persona updatePersona(int id, PersonaRequest personaRequest) throws NotFoundException {
        Persona p = getPersonaById(id);

        p.setNome(personaRequest.getNome());
        p.setCognome(personaRequest.getCognome());
        p.setIndirizzo(personaRequest.getIndirizzo());
        p.setDataNascita(personaRequest.getDataNascita());
        p.setEmail(personaRequest.getEmail());

        return personaRepository.save(p);
    }

    public void deletePersona(int id) throws NotFoundException {
        Persona persona = getPersonaById(id);
        personaRepository.delete(persona);
    }



}
