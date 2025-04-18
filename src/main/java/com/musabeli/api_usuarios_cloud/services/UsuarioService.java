package com.musabeli.api_usuarios_cloud.services;

import com.musabeli.api_usuarios_cloud.dto.CreateUsuarioDto;
import com.musabeli.api_usuarios_cloud.entities.Usuario;



public interface UsuarioService {

    Usuario createUsuario(CreateUsuarioDto usuarioDto);
}