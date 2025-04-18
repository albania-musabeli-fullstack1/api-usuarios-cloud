package com.musabeli.api_usuarios_cloud.controllers;

import com.musabeli.api_usuarios_cloud.dto.CreateUsuarioDto;
import com.musabeli.api_usuarios_cloud.entities.Usuario;
import com.musabeli.api_usuarios_cloud.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody CreateUsuarioDto usuarioDto){
        Usuario nuevoUsuario = this.usuarioService.createUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }
}