package com.adminpro.services;

import com.adminpro.entities.Hospital;

import java.util.List;

public interface HospitalService {

    List<Hospital> findAll();
    Hospital save(Hospital hospital);
    Hospital findById(String id);
    void deleteById(String id);
    List<Hospital> findByNombreContains(String nombre);
}
