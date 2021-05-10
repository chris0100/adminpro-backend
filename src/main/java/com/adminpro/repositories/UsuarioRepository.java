package com.adminpro.repositories;

import com.adminpro.entities.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Usuario findByNombre(String userName);

    List<Usuario> findByNombreContains(String userName);

    Usuario findByEmail(String email);

    int countByEmail(String email);
}
