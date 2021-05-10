package com.adminpro.services;


import com.adminpro.entities.Hospital;
import com.adminpro.repositories.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService{

    @Autowired
    HospitalRepository hospitalRepositoryObj;


    @Override
    public List<Hospital> findAll() {
        return hospitalRepositoryObj.findAll();
    }


    @Override
    public Hospital save(Hospital hospital) {
        return hospitalRepositoryObj.save(hospital);
    }

    @Override
    public Hospital findById(String id) {
        return hospitalRepositoryObj.findById(id).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        hospitalRepositoryObj.deleteById(id);
    }

    @Override
    public List<Hospital> findByNombreContains(String nombre) {
        return hospitalRepositoryObj.findByNombreContains(nombre);
    }
}
