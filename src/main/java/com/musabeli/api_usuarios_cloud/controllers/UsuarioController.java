package com.musabeli.api_usuarios_cloud.controllers;

import com.musabeli.api_usuarios_cloud.dto.CreateUsuarioDto;
import com.musabeli.api_usuarios_cloud.dto.LoginRequestDto;
import com.musabeli.api_usuarios_cloud.dto.LoginResponseDto;
import com.musabeli.api_usuarios_cloud.dto.UpdateUsuarioDto;
import com.musabeli.api_usuarios_cloud.entities.Usuario;
import com.musabeli.api_usuarios_cloud.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.extern.slf4j.Slf4j;
import java.util.List;

@RestController
@RequestMapping("/usuario")
@Slf4j
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody CreateUsuarioDto usuarioDto){
        Usuario nuevoUsuario = this.usuarioService.createUsuario(usuarioDto);
        log.info("METODO POST: crear usuario OK");

        // Uso de HATEOAS
        nuevoUsuario.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioController.class).getUsuarioById(nuevoUsuario.getId())).withSelfRel()
        );
        nuevoUsuario.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioController.class).getUsuarios()).withRel(IanaLinkRelations.COLLECTION)
        );

        return ResponseEntity.created(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(UsuarioController.class).getUsuarioById(nuevoUsuario.getId())
                ).toUri()
        ).body(nuevoUsuario);
    }

    @GetMapping("/getUsuarios")
    public ResponseEntity<List<Usuario>> getUsuarios(){
        List<Usuario> usuarioList = this.usuarioService.getAllUsuarios();

        for (Usuario usuario:usuarioList){
            usuario.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(UsuarioController.class).getUsuarioById(usuario.getId())).withSelfRel()
            );
            usuario.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(UsuarioController.class).getUsuarios()).withRel(IanaLinkRelations.COLLECTION)
            );
        }
        log.info("METODO GET: obtener usuarios OK");
        return ResponseEntity.status(HttpStatus.OK).body(usuarioList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id){
        Usuario usuario = this.usuarioService.getUsuarioById(id);
        usuario.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioController.class).getUsuarioById(usuario.getId())).withSelfRel()
        );
        usuario.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioController.class).getUsuarios()).withRel(IanaLinkRelations.COLLECTION)
        );
        log.info("Usuario encontrado: id: {}", usuario.getId());
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @Valid @RequestBody UpdateUsuarioDto usuarioDto){
        Usuario usuario = this.usuarioService.updateUsuario(id, usuarioDto);
        usuario.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioController.class).getUsuarioById(usuario.getId())).withSelfRel()
        );
        usuario.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioController.class).getUsuarios()).withRel(IanaLinkRelations.COLLECTION)
        );
        log.info("Usuario con id: {} actualizado", usuario.getId());
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deleteUsuario(@PathVariable Long id){
        Usuario usuario = this.usuarioService.deleteUsuario(id);
        log.info("Usuario con id: {} eliminado", usuario.getId());
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    // Inicio de sesion
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginDto){
        LoginResponseDto userLogin = this.usuarioService.login(loginDto);
        log.info("Inicio de sesión OK");
        return ResponseEntity.status(HttpStatus.OK).body(userLogin);
    }
}