package com.adminpro.services;

import com.adminpro.entities.Usuario;
import com.adminpro.entities.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {

    List<Usuario> findAll();
    Usuario save(Usuario usuario);
    Usuario findByNombre(String userName);
    Usuario findById(String id);
    void deleteById(String id);
    List<Usuario> findByNombreContains(String name);
    Usuario findByEmail(String email);
    List<UsuarioDTO> findAllDTO();
    int checkEmailRepeat(String email);
}
