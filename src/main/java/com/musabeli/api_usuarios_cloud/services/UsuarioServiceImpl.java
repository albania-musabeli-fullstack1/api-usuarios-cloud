package com.musabeli.api_usuarios_cloud.services;

import com.musabeli.api_usuarios_cloud.dto.CreateUsuarioDto;
import com.musabeli.api_usuarios_cloud.dto.LoginRequestDto;
import com.musabeli.api_usuarios_cloud.dto.LoginResponseDto;
import com.musabeli.api_usuarios_cloud.dto.UpdateUsuarioDto;
import com.musabeli.api_usuarios_cloud.entities.Usuario;
import com.musabeli.api_usuarios_cloud.exceptions.InvalidCredentialsException;
import com.musabeli.api_usuarios_cloud.exceptions.ResourceNotFoundException;
import com.musabeli.api_usuarios_cloud.exceptions.UserEmailExistsException;
import com.musabeli.api_usuarios_cloud.mapper.UsuarioMapper;
import com.musabeli.api_usuarios_cloud.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario findUsuarioById(Long id){
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);

        if (usuario.isPresent()){
            return usuario.get();
        }
        else {
            throw new ResourceNotFoundException("No existen registros del usuario con id " + id);
        }
    }


    @Override
    public Usuario createUsuario(CreateUsuarioDto usuarioDto) {
        // Validar si existe un usuario con el correo
        boolean correoExiste = this.usuarioRepository.findByCorreo(usuarioDto.getCorreo()).isPresent();

        if (correoExiste) throw new UserEmailExistsException("El correo ingresado ya se encuentra registrado");
        Usuario usuario = UsuarioMapper.fromCreateUsuario(usuarioDto);
        // guardar en bbdd
        return this.usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return this.usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuarioById(Long id) {
        return this.findUsuarioById(id);
    }

    @Override
    public Usuario updateUsuario(Long id, UpdateUsuarioDto updateUsuarioDto) {

        Usuario updateUsuario = this.findUsuarioById(id);
        if (updateUsuarioDto.getUsuario() != null){
            updateUsuario.setUsuario(updateUsuarioDto.getUsuario());
        }
        if (updateUsuarioDto.getCorreo() != null){
            updateUsuario.setCorreo(updateUsuarioDto.getCorreo());
        }
        if (updateUsuarioDto.getPassword() != null){
            updateUsuario.setPassword(updateUsuarioDto.getPassword());
        }
        if (updateUsuarioDto.getRol() != null){
            updateUsuario.setRol(updateUsuarioDto.getRol());
        }

        // actualizar en bbdd
        return this.usuarioRepository.save(updateUsuario);
    }

    @Override
    public Usuario deleteUsuario(Long id) {
        Usuario usuario = this.findUsuarioById(id);

        this.usuarioRepository.deleteById(id);
        return usuario;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginDto) {
        Optional<Usuario> usuario = this.usuarioRepository.findByCorreoAndPassword(
                loginDto.getCorreo(),
                loginDto.getPassword()
        );

        if (usuario.isPresent()){
            return new LoginResponseDto(usuario.get().getUsuario(), "INICIO DE SESIÓN EXITOSO");
        }
        else {
            throw new InvalidCredentialsException("Error al iniciar sesión. Correo y/o contraseña inválidos");
        }
    }
}