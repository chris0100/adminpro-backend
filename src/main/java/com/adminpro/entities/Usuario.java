package com.adminpro.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "usuario")
@Data
public class Usuario {

    @Id
    private String id;

    @NotNull(message = "no puede estar vacio")
    private String nombre;

    //@Indexed(unique = true)
    private String email;

    @NotNull(message = "no puede estar vacio")
    private String password;

    private String img;

    private String role;

    private Boolean google;


    public Usuario(){
        this.google = false;
        this.img = "sin-foto.png";

        this.role = "USER_ROLE";
    }
}
