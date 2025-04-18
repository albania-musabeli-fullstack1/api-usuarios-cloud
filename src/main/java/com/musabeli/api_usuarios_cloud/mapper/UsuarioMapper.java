package com.musabeli.api_usuarios_cloud.mapper;

import com.musabeli.api_usuarios_cloud.dto.CreateUsuarioDto;
import com.musabeli.api_usuarios_cloud.entities.Usuario;

public class UsuarioMapper {

    /**
     * Metodo estatico para crear una instancia de la clase usuario desde un DTO
     * @param dto
     * @return Usuario
     */
    public static Usuario fromCreateUsuario(CreateUsuarioDto dto){
        return Usuario.builder()
                .usuario(dto.getUsuario())
                .correo(dto.getCorreo())
                .password(dto.getPassword())
                .rol(dto.getRol())
                .build();
    }
}
