package com.adminpro.controllers;

import com.adminpro.entities.Medico;
import com.adminpro.services.HospitalService;
import com.adminpro.services.MedicoService;
import com.adminpro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200", "*"})
public class MedicoController {


    @Autowired
    MedicoService medicoServiceObj;

    @Autowired
    UsuarioService usuarioServiceObj;

    @Autowired
    HospitalService hospitalServiceObj;


    //Listado de medicos
    @GetMapping("/medicos")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity getMedicos(){
        List<Medico> listaMedicos;

        Map<String,Object> response = new HashMap<>();

        try{
            listaMedicos = medicoServiceObj.findAll();


            if (listaMedicos.size() == 0){
                response.put("mensaje", "No hay Medicos registrados");
                return new ResponseEntity(response,HttpStatus.NOT_FOUND);
            }

            response.put("medicos",listaMedicos);
            return new ResponseEntity(response, HttpStatus.OK);
        }catch (DataAccessException de){
            response.put("error", de.getMostSpecificCause().getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // Agregar medico
    @PostMapping("/medicos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createMedico(@Valid @RequestBody Medico medico, BindingResult result){
        //Medico que se crea en BD
        Medico medicoBD;
        Map<String, Object> response = new HashMap<>();

        // Se analiza el result
        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try{
            medicoBD = medicoServiceObj.save(medico);

            response.put("mensaje","Medico creado satisfactoriamente");
            response.put("medico", medicoBD);
            return new ResponseEntity(response,HttpStatus.OK);

        }catch (DataAccessException de){
            response.put("error", de.getMostSpecificCause());
            return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
        }
    }


    // Modificar un medico
    @PutMapping("/medicos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity modificarMedico(@PathVariable("id") String id,
                                          @Valid @RequestBody Medico medico,
                                          BindingResult result){
        //Localiza el medico original en BD
        Medico medicoBD = medicoServiceObj.findById(id);
        Map<String, Object> response = new HashMap<>();

        // Se valida si el medico esta nulo
        if (medicoBD == null){
            response.put("mensaje", "El Medico no se encuentra registrado");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }

        //Se valida el body
        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try{
            //Se pasan datos para modificar al medico
            medicoBD.setImg(medico.getImg());
            medicoBD.setNombre(medico.getNombre());
            medicoServiceObj.save(medicoBD);

            response.put("mensaje", "El Medico se ha modificado satisfactoriamente");
            response.put("medico", medicoBD);
            return new ResponseEntity(response, HttpStatus.OK);

        }catch (DataAccessException de){
            response.put("error", de.getMostSpecificCause().getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }


    //Borrar un medico
    @DeleteMapping("medicos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteMedico(@PathVariable("id") String id){
        // Se localiza el medico a traves del id
        Medico medicoBD = medicoServiceObj.findById(id);
        Map<String,Object> response = new HashMap<>();

        //Se revisa si es null el medico encontrado
        if (medicoBD == null){
            response.put("error","El Medico no se encuentra registrado");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }

        //Se elimina el registro de la BD
        try{
            medicoServiceObj.removeById(id);
            response.put("mensaje", "El Medico ha sido eliminado satisfactoriamente");
            return new ResponseEntity(response, HttpStatus.OK);

        }catch (DataAccessException de){
            response.put("error", de.getMostSpecificCause().getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }


}


















