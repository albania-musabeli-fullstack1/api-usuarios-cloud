package com.musabeli.api_usuarios_cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

    private String usuario;
    private String message;
}
