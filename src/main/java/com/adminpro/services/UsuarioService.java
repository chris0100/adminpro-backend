package com.adminpro.services;

import com.adminpro.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {

    Page<Usuario> findAll(Pageable pageable);
    int totalUsuarios();
    Usuario save(Usuario usuario);
    Usuario findByNombre(String userName);
    Usuario findById(String id);
    void deleteById(String id);
    List<Usuario> findByNombreContains(String name);
    Usuario findByEmail(String email);
    int checkEmailRepeat(String email);
}
