package com.adminpro.controllers;

import com.adminpro.entities.Hospital;
import com.adminpro.entities.Usuario;
import com.adminpro.services.HospitalService;
import com.adminpro.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http:localhost:4200","*"})
@RequestMapping("/api")
@Slf4j
public class HospitalController {

    @Autowired
    HospitalService hospitalServiceObj;

    @Autowired
    UsuarioService usuarioServiceObj;


    //Obtener la lista de hospitales
    @GetMapping("/hospitales")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity getHospitales(){
        //Se define una lista de tipo hospital
        List<Hospital> listaHospitales;
        Map<String, Object> response = new HashMap<>();
        List<Usuario> listaUsuarios = new ArrayList<>();

        try{
            listaHospitales = hospitalServiceObj.findAll();

            response.put("hospitales", listaHospitales);
            return new ResponseEntity(response,HttpStatus.OK);

        }catch (DataAccessException de){
            response.put("error", "No se puede acceder a la base de datos");
            response.put("mensaje", de.getMostSpecificCause().getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }



    //Crear Hospital
    @PostMapping("/hospitales/{idUsuarioCreate}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity createHospital(@Valid @RequestBody Hospital hospital, BindingResult result,
                                         @PathVariable("idUsuarioCreate") String idUsuarioCreate){
        // Hospital que se guardara en BD
        Hospital hospitalBD;
        Map<String,Object> response = new HashMap<>();
        log.info(hospital.toString());

        // Se localiza el id del usuarioCreate
        Usuario usuarioCreate = usuarioServiceObj.findById(idUsuarioCreate);
        log.info(usuarioCreate.toString());
        hospital.setIdUsuarioCreate(usuarioCreate);

        if (usuarioCreate == null){
            response.put("mensaje", "No tiene datos del usuario que lo creo");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }

        // Se valida
        if (result.hasErrors()){
            log.error("Se han enccontrado errores");
            // Se crea lista de errores
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // guarda y valida errores
        try{
            log.info(hospital.toString());
            hospitalBD = hospitalServiceObj.save(hospital);

            response.put("mensaje", "El Hospital ha sido creado con exito!");
            response.put("hospital", hospitalBD);
            return new ResponseEntity(response, HttpStatus.CREATED);

        }catch (DataAccessException da){
            String errorFound = da.getMostSpecificCause().getMessage();
            log.warn("ERROR: " + errorFound);
            response.put("mensaje", errorFound);
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    //ModificarHospital
    @PutMapping("/hospitales/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity modificateHospital(@Valid @RequestBody Hospital hospital, BindingResult result,
                                             @PathVariable("id") String id){
        //Hospital que se localiza con el id
        Hospital hospitalBD = hospitalServiceObj.findById(id);
        Map<String,Object> response = new HashMap<>();

        // Valida errores
        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "Error: " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }

        // Valida si el hospital encontrado es nulo
        if (hospitalBD == null){
            response.put("error","No existe el Hospital en la BD");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }

        try{
            // Se pasan los datos al hospital en BD
            hospitalBD.setNombre(hospital.getNombre());
            //hospitalBD.setImg(hospital.getImg());

            //Se guarda en BD
            hospitalServiceObj.save(hospitalBD);

            response.put("mensaje", "Se ha editado correctamente al Hospital");
            return new ResponseEntity(response, HttpStatus.CREATED);

        }catch (DataAccessException de){
            response.put("mensaje", "Ha ocurrido un error al acceder a BD");
            response.put("error",de.getMessage() + " : " + de.getMostSpecificCause());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    //Eliminar Hospital
    @DeleteMapping("/hospitales/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteHospital(@PathVariable("id") String id){
        //Se localiza al hospital
        Hospital hospitalBD = hospitalServiceObj.findById(id);
        Map<String, Object> response = new HashMap<>();

        //Si es nulo,se envia respuesta
        if (hospitalBD == null){
            response.put("error","El hospital no se encuentra en la BD");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }

        //Si es localizado se elimina
        try{
            hospitalServiceObj.deleteById(id);
            response.put("mensaje","El Hospital ha sido eliminado correctamente");
            return new ResponseEntity(response, HttpStatus.OK);

        }catch (DataAccessException de){
            response.put("mensaje", "No se puede eliminar el Hospital");
            response.put("error", de.getMostSpecificCause().getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}













