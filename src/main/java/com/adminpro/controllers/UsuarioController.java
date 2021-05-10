package com.adminpro.controllers;

import com.adminpro.entities.Hospital;
import com.adminpro.entities.Medico;
import com.adminpro.entities.Usuario;
import com.adminpro.entities.dto.UsuarioDTO;
import com.adminpro.services.HospitalService;
import com.adminpro.services.MedicoService;
import com.adminpro.services.UploadFileService;
import com.adminpro.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioServiceObj;

    @Autowired
    private HospitalService hospitalServiceObj;

    @Autowired
    private MedicoService medicoServiceObj;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoderObj;

    @Autowired
    private UploadFileService uploadFileServiceObj;

    @Autowired
    private ModelMapper modelMapper;




    // Buscar todo
    @GetMapping("/todo/{search}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity buscarAll(@PathVariable("search") String search) {

        Map<String, Object> response = new HashMap<>();

        //busqueda de los usuarios que coincidan con ese nombre
        List<Usuario> listaUsuarios = usuarioServiceObj.findByNombreContains(search);
        log.info(listaUsuarios.toString());
        response.put("usuarios", listaUsuarios);

        //busqueda de los hospitales
        List<Hospital> listaHospitales = hospitalServiceObj.findByNombreContains(search);
        response.put("hospitales", listaHospitales);

        //busqueda de los medicos
        List<Medico> listaMedicos = medicoServiceObj.findByNombreContains(search);
        response.put("medicos", listaMedicos);

        return new ResponseEntity(response, HttpStatus.OK);
    }


    // Obtener lista de usuarios
    @GetMapping("/usuarios")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity getUsuarios() {
        List<UsuarioDTO> listaUsuariosDTO;
        Map<String, Object> response = new HashMap<>();

        try {
            //listaUsuariosDTO = usuarioServiceObj.findAllDTO();
            listaUsuariosDTO = usuarioServiceObj.findAll()
                    .stream()
                    .map(obj -> {
                        UsuarioDTO usuarioDTO = modelMapper.map(obj, UsuarioDTO.class);
                        usuarioDTO.setPassword("");
                        return usuarioDTO;
                    })
                    .collect(Collectors.toList());

            response.put("usuarios", listaUsuariosDTO);
            log.info(listaUsuariosDTO.toString());
            return new ResponseEntity(response, HttpStatus.OK);

        } catch (DataAccessException daexp) {
            response.put("mensaje", "Error al consultar la base de datos");
            response.put("error", daexp.getMostSpecificCause().getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Guardar un usuario
    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult result) {
        Usuario usuarioNuevo;
        Map<String, Object> response = new HashMap<>();

        // Este se alinea con lo que se valida en la Entity
        if (result.hasErrors()) {
            log.error("Se han encontrado errores");
            // Se crea lista de errores
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }

        //valide si el correo ya existe en base de datos
        int count = usuarioServiceObj.checkEmailRepeat(usuarioDTO.getEmail());
        log.info("count es " + count);
        if (count > 0){
            response.put("error", "Este email ya se encuentra registrado");
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }


        // guarda y valida errores en el momento de hacer un save en la base de datos
        try {
            // Se encripta el password
            String passwordEnc = bCryptPasswordEncoderObj.encode(usuarioDTO.getPassword());

            // Se guarda el usuario
            usuarioNuevo = new Usuario();
            usuarioNuevo.setPassword(passwordEnc);
            usuarioNuevo.setEmail(usuarioDTO.getEmail());
            usuarioNuevo.setNombre(usuarioDTO.getNombre());
            usuarioServiceObj.save(usuarioNuevo);


            response.put("mensaje", "El Usuario " + usuarioDTO.getNombre()  + " ha sido creado con exito!");
            return new ResponseEntity(response, HttpStatus.CREATED);

        } catch (DataAccessException da) {
            String errorFound = da.getMostSpecificCause().getMessage();
            log.warn("ERROR: " + errorFound);
            if (errorFound.contains("email")) {
                errorFound = "Hay un duplicado en el email";
            }
            response.put("mensaje", errorFound);
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Modificar un usuario
    @PutMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity modificateUsuario(@PathVariable("id") String id, @Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult result) {
        // Se localiza al usuario en la base de datos
        Usuario usuarioBD = usuarioServiceObj.findById(id);

        Map<String, Object> response = new HashMap<>();

        // valida errores
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }

        //Si la respuesta es null
        if (usuarioBD == null) {
            response.put("error", "No se puede editar el usuario, no se encuentra en la BD");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }

        try {
            // De lo contrario se pasan los datos al usuario
            usuarioBD.setNombre(usuarioDTO.getNombre());
            usuarioBD.setEmail(usuarioDTO.getEmail());
            usuarioBD.setGoogle(usuarioDTO.getGoogle());
            usuarioBD.setImg(usuarioDTO.getImg());

            usuarioBD = usuarioServiceObj.save(usuarioBD);

            UsuarioDTO usuarioDTOResponse = modelMapper.map(usuarioBD, UsuarioDTO.class);
            usuarioDTOResponse.setPassword("");

            //Si no hay errores
            response.put("mensaje", "Se ha editado correctamente al usuario");
            response.put("usuario", usuarioDTOResponse);
            return new ResponseEntity(response, HttpStatus.CREATED);

        } catch (DataAccessException e) {
            response.put("mensaje", "Ha ocurrido un error para editar el usuario");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Eliminar el usuario a traves del id
    @DeleteMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteUsuario(@PathVariable("id") String id) {
        //Se localiza el usuario a eliminar
        Usuario usuarioToDelete = usuarioServiceObj.findById(id);

        Map<String, Object> response = new HashMap<>();

        // Se valida el usuario encontrado
        if (usuarioToDelete == null) {
            response.put("error", "El usuario no se encuentra en la base de datos");
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }

        try {
            //Se elimina foto del usuario
            uploadFileServiceObj.eliminar(usuarioToDelete.getImg());

            // Si el usuario es localizado, se elimina
            usuarioServiceObj.deleteById(id);

            response.put("mensaje", "El usuario " + usuarioToDelete.getNombre() + " se ha eliminado con exito");
            return new ResponseEntity(response, HttpStatus.OK);

        } catch (DataAccessException de) {
            response.put("mensaje", "Error al eliminar usuario");
            response.put("error", de.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Visualizar foto
    @GetMapping("/upload/usuario/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable("nombreFoto") String nombreFoto) {
        Resource resource = null;
        Map<String, Object> response = new HashMap<>();

        try {
            resource = uploadFileServiceObj.load(nombreFoto);
        } catch (MalformedURLException e) {
            response.put("error", "No se puede cargar la imagen");
            return new ResponseEntity<Resource>((Resource) response, HttpStatus.BAD_REQUEST);
        }

        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        return new ResponseEntity<>(resource, cabecera, HttpStatus.OK);
    }


    // Cargar foto en el servidor
    @PostMapping("/upload/usuario")
    public ResponseEntity upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") String id) {
        Map<String, Object> response = new HashMap<>();

        //Se localiza al usuario
        Usuario usuario = usuarioServiceObj.findById(id);

        if (!archivo.isEmpty()) {
            String nombreArchivo = null;

            try {
                //Se realiza la carga y retorna el nombre dle archivo
                nombreArchivo = uploadFileServiceObj.upload(archivo);
            } catch (IOException io) {
                log.error(io.getLocalizedMessage());
                response.put("error", "Error al cargar la imagen");
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Se revisa si el usuario ya tiene foto para eliminarla
            String nombreFotoAnterior = usuario.getImg();
            uploadFileServiceObj.eliminar(nombreFotoAnterior);

            //Se guarda en BD el nombre de la foto
            usuario.setImg(nombreArchivo);
            usuarioServiceObj.save(usuario);
            response.put("usuario", usuario);
            response.put("mensaje", "Has cargado correctamente la foto");
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
        response.put("error", "Imagen sin contenido");
        return new ResponseEntity(response, HttpStatus.NO_CONTENT);
    }

}














