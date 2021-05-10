package com.adminpro.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "medico")
@Data
public class Medico {

    @Id
    private String id;

    @Indexed(name = "nombre")
    private String nombre;
    private String img;
    private List<Usuario> idUsuariosRegistrados;
    private Hospital idHospitalRegistrado;
}
