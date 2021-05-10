package com.adminpro.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "usuario")
@Data
public class Usuario {

    @Id
    private String id;

    @NotNull(message = "no puede estar vacio")
    private String nombre;

    private String email;

    @NotNull(message = "no puede estar vacio")
    private String password;

    private String img;

    @NotNull(message = "no puede estar vacio")
    private List<String> role;

    private Boolean google;


    public Usuario(){
        this.google = false;
        this.img = "sin-foto.png";

        List<String> listaRol = new ArrayList<>();
        listaRol.add("USER_ROLE");
        this.role = listaRol;
    }
}
