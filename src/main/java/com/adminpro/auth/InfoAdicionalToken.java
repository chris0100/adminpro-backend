package com.adminpro.auth;

import com.adminpro.entities.Usuario;
import com.adminpro.services.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InfoAdicionalToken implements TokenEnhancer {

    @Autowired
    private UsuarioService usuarioServiceObj;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        log.info(authentication.getName() + " desde enhance");

        Usuario usuario = usuarioServiceObj.findByEmail(authentication.getName());

        Map<String, Object> info = new HashMap<>();
        info.put("id", usuario.getId());
        info.put("nombre", usuario.getNombre());
        info.put("email", usuario.getEmail());
        info.put("img", usuario.getImg());
        info.put("google", usuario.getGoogle());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
