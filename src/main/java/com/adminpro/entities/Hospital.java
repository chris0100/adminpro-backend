package com.adminpro.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hospital")
@Data
public class Hospital {

    @Id
    private String id;

    private String nombre;
    private String img;
    private Usuario idUsuarioCreate;


    public Hospital() {
        this.img = "sin-foto.png";
    }
}
