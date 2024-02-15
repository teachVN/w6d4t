package it.epicode.w6d4t.controller;


import it.epicode.w6d4t.exception.BadRequestException;
import it.epicode.w6d4t.exception.NotFoundException;
import it.epicode.w6d4t.model.CustomResponse;
import it.epicode.w6d4t.model.PersonaRequest;
import it.epicode.w6d4t.service.PersonaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
public class PersonaController {
    @Autowired
    private PersonaService personaService;

    @GetMapping("/persone")
    public ResponseEntity<CustomResponse> getAll(Pageable pageable){

        try {
            return CustomResponse.success(HttpStatus.OK.toString(), personaService.getAll(pageable), HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/persone/{id}")
    public ResponseEntity<CustomResponse> getPersonaById(@PathVariable int id){

        try {
            return CustomResponse.success(HttpStatus.OK.toString(), personaService.getPersonaById(id), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/persone")
    public ResponseEntity<CustomResponse> savePersona(@RequestBody @Validated PersonaRequest personaRequest, BindingResult bindingResult)  {
            if(bindingResult.hasErrors()){
                return CustomResponse.error(bindingResult.getAllErrors().toString(), HttpStatus.BAD_REQUEST);

            }

        try{
            return CustomResponse.success(HttpStatus.OK.toString(), personaService.savePersona(personaRequest), HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/persone/{id}")
    public ResponseEntity<CustomResponse> updatePersona(@PathVariable int id, @RequestBody @Validated PersonaRequest personaRequest , BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return CustomResponse.error(bindingResult.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }

        try {
            return CustomResponse.success(HttpStatus.OK.toString(), personaService.updatePersona(id, personaRequest), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/persone/{id}")
    public ResponseEntity<CustomResponse> deletePersona(@PathVariable int id){

        try{
            personaService.deletePersona(id);
            return CustomResponse.emptyResponse("Persona con id=" + id + " cancellata", HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }




}
