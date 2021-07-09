package com.adminpro.services;

import com.adminpro.entities.Usuario;
import com.adminpro.entities.dto.UsuarioDTO;
import com.adminpro.repositories.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UsuarioServiceImp implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepositoryObj;


    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepositoryObj.findByEmail(email);
    }

    @Override
    public int checkEmailRepeat(String email) {
        return usuarioRepositoryObj.countByEmail(email);
    }

    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepositoryObj.findAll(pageable);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepositoryObj.save(usuario);
    }

    @Override
    public Usuario findByNombre(String userName) {
        return usuarioRepositoryObj.findByNombre(userName);
    }

    @Override
    public Usuario findById(String id) {
        return usuarioRepositoryObj.findById(id).orElse(null);
    }

    @Override
    public void deleteById(String id) {
        usuarioRepositoryObj.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username + " es el nombre de usuario");

        Usuario usuario = usuarioRepositoryObj.findByEmail(username);

        if (usuario != null) {
            log.info("datos usuario: " + usuario.getNombre() + " : " + usuario.getEmail() + " : " + usuario.getId());
        }

        if (usuario == null) {
            log.warn("usuario no existe");
            throw new UsernameNotFoundException("Error: el usuario no existe");
        }

        // Se carga el rol del usuario
        GrantedAuthority authorities = new SimpleGrantedAuthority(usuario.getRole().trim());


        return new User(usuario.getEmail().trim(), usuario.getPassword().trim(), true, true, true, true, Collections.singletonList(authorities));
    }


    @Override
    public List<Usuario> findByNombreContains(String name) {
        return usuarioRepositoryObj.findByNombreContains(name);
    }



    private UsuarioDTO convertToUsuarioDTO(Usuario usuario) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);

        UsuarioDTO usuarioDTO = modelMapper
                .map(usuario, UsuarioDTO.class);
        usuarioDTO.setPassword("");
        return usuarioDTO;
    }


    @Override
    public int totalUsuarios() {
        return usuarioRepositoryObj.findAll().size();
    }
}











