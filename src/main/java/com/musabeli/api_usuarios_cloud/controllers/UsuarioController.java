package com.musabeli.api_usuarios_cloud.controllers;

import com.musabeli.api_usuarios_cloud.dto.CreateUsuarioDto;
import com.musabeli.api_usuarios_cloud.entities.Usuario;
import com.musabeli.api_usuarios_cloud.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody CreateUsuarioDto usuarioDto){
        Usuario nuevoUsuario = this.usuarioService.createUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @GetMapping("/getUsuarios")
    public ResponseEntity<List<Usuario>> getUsuarios(){
        List<Usuario> usuarioList = this.usuarioService.getAllUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(usuarioList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id){
        Usuario usuario = this.usuarioService.getUsuarioById(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }
}