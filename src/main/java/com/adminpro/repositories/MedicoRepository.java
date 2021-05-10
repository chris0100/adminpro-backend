package com.adminpro.repositories;

import com.adminpro.entities.Medico;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoRepository extends MongoRepository<Medico,String> {

    List<Medico> findByNombreContains(String nombre);
}
