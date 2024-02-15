package it.epicode.w6d4t.controller;


import com.cloudinary.Cloudinary;
import it.epicode.w6d4t.exception.BadRequestException;
import it.epicode.w6d4t.exception.NotFoundException;
import it.epicode.w6d4t.model.Auto;
import it.epicode.w6d4t.model.AutoRequest;
import it.epicode.w6d4t.model.CustomResponse;
import it.epicode.w6d4t.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class AutoController {
    @Autowired
    private AutoService autoService;
    @Autowired
    private Cloudinary cloudinary;

    @GetMapping("/auto")
    public ResponseEntity<CustomResponse> getAll(Pageable pageable){
            try{
                return CustomResponse.success(HttpStatus.OK.toString(), autoService.getAll(pageable), HttpStatus.OK);
            }
            catch (Exception e){
                return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }
    @GetMapping("/auto/{id}")
    public ResponseEntity<CustomResponse> getAutoById(@PathVariable int id){
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), autoService.getAutoById(id), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/auto")
    public ResponseEntity<CustomResponse> saveAuto(@RequestBody @Validated AutoRequest autoRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return CustomResponse.error(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).toList().toString(), HttpStatus.BAD_REQUEST);
        }

        //return CustomResponse.success(HttpStatus.OK.toString(), autoService.saveAuto(autoRequest), HttpStatus.OK);
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), autoService.saveAuto(autoRequest), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/auto/{id}")
    public ResponseEntity<CustomResponse> updateAuto(@PathVariable int id, @RequestBody @Validated AutoRequest autoRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return CustomResponse.error(bindingResult.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), autoService.updateAuto(id, autoRequest), HttpStatus.OK);
        }
            catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
            catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/auto/{id}")
    public ResponseEntity<CustomResponse> deleteAuto(@PathVariable int id){

            autoService.deleteAuto(id);
            try{
                return CustomResponse.emptyResponse("Auto con id=" + id + " cancellata", HttpStatus.OK);
            }
            catch (NotFoundException e){
                return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            catch (Exception e){
                return CustomResponse.error(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }
    @PatchMapping("/auto/{id}/upload")
    public ResponseEntity<CustomResponse> uploadLogo(@PathVariable int id,@RequestParam("upload") MultipartFile file){
        try {
            Auto auto = autoService.uploadLogo(id, (String)cloudinary.uploader().upload(file.getBytes(), new HashMap()).get("url"));
            return CustomResponse.success(HttpStatus.OK.toString(), auto, HttpStatus.OK);
        }
        catch (IOException e){
            return CustomResponse.error(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
