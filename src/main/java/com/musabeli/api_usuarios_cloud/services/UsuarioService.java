package com.musabeli.api_usuarios_cloud.services;

import com.musabeli.api_usuarios_cloud.dto.CreateUsuarioDto;
import com.musabeli.api_usuarios_cloud.dto.LoginRequestDto;
import com.musabeli.api_usuarios_cloud.dto.LoginResponseDto;
import com.musabeli.api_usuarios_cloud.dto.UpdateUsuarioDto;
import com.musabeli.api_usuarios_cloud.entities.Usuario;

import java.util.List;


public interface UsuarioService {

    Usuario createUsuario(CreateUsuarioDto usuarioDto);
    List<Usuario> getAllUsuarios();
    Usuario getUsuarioById(Long id);
    Usuario updateUsuario(Long id, UpdateUsuarioDto updateUsuarioDto);
    Usuario deleteUsuario(Long id);
    LoginResponseDto login(LoginRequestDto loginDto);
}