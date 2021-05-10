package com.adminpro.services;

import com.adminpro.entities.Medico;
import com.adminpro.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoServiceImpl implements MedicoService{

    @Autowired
    MedicoRepository medicoRepositoryObj;


    @Override
    public List<Medico> findAll() {
        return medicoRepositoryObj.findAll();
    }

    @Override
    public Medico save(Medico medico) {
        return medicoRepositoryObj.save(medico);
    }

    @Override
    public Medico findById(String id) {
        return medicoRepositoryObj.findById(id).orElse(null);
    }

    @Override
    public void removeById(String id) {
        medicoRepositoryObj.deleteById(id);
    }

    @Override
    public List<Medico> findByNombreContains(String nombre) {
        return medicoRepositoryObj.findByNombreContains(nombre);
    }
}
