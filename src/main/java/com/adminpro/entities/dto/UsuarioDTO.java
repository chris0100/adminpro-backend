package com.adminpro.entities.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsuarioDTO implements Serializable {

    private String id;
    private String nombre;
    private String email;
    private String password;
    private Boolean google;
    private String role;
    private String img;
}
