package com.adminpro.services;

import com.adminpro.entities.Medico;

import java.util.List;

public interface MedicoService {

    List<Medico> findAll();
    Medico save(Medico medico);
    Medico findById(String id);
    void removeById(String id);
    List<Medico> findByNombreContains(String nombre);
}
