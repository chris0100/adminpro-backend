package com.adminpro.services;

import com.adminpro.entities.Usuario;
import com.adminpro.entities.dto.UsuarioDTO;
import com.adminpro.repositories.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<Usuario> findAll() {
        return usuarioRepositoryObj.findAll();
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
        List<GrantedAuthority> authorities = usuario.getRole()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.trim()))
                .peek(authority -> System.out.println("role: " + authority.getAuthority()))
                .collect(Collectors.toList());

        return new User(usuario.getEmail().trim(), usuario.getPassword().trim(), true, true, true, true, authorities);
    }


    @Override
    public List<Usuario> findByNombreContains(String name) {
        return usuarioRepositoryObj.findByNombreContains(name);
    }


    //Otra forma de realizarlo diferente a la actual que es mas corta
    @Override
    public List<UsuarioDTO> findAllDTO() {
        return (usuarioRepositoryObj
                .findAll()
                .stream()
                .map(this::convertToUsuarioDTO)
                .collect(Collectors.toList()));
    }

    private UsuarioDTO convertToUsuarioDTO(Usuario usuario) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);

        UsuarioDTO usuarioDTO = modelMapper
                .map(usuario, UsuarioDTO.class);
        usuarioDTO.setPassword("");
        return usuarioDTO;
    }



}











