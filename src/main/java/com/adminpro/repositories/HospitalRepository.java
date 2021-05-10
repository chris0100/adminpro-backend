package com.adminpro.repositories;


import com.adminpro.entities.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {

    List<Hospital> findByNombreContains(String nombre);

}
