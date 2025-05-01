package com.musabeli.api_usuarios_cloud.repositories;

import com.musabeli.api_usuarios_cloud.entities.Usuario;
import com.musabeli.api_usuarios_cloud.enums.Rol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void saveUsuarioTest(){
        //Arrange
        Usuario usuario = Usuario.builder()
                .usuario("demir78687")
                .password("003456ASDFqwer5_")
                .correo("demir@gmail.com")
                .rol(Rol.CONDUCTOR)
                .build();

        //Act
        Usuario resultado = usuarioRepository.save(usuario);

        //Assert
        assertNotNull(resultado.getId());
        assertEquals("demir78687", resultado.getUsuario());
    }


    @Test
    public void findUserByIdTest(){
        // Arrange
        Usuario usuario = Usuario.builder()
                .usuario("demir78687")
                .password("003456ASDFqwer5_")
                .correo("demir@gmail.com")
                .rol(Rol.CONDUCTOR)
                .build();

        Usuario newUsuario = usuarioRepository.save(usuario);

        // Act
        Usuario usuarioById = usuarioRepository.findById(newUsuario.getId()).orElse(null);

        // Assert
        assertNotNull(usuarioById);
        assertEquals(newUsuario.getId(), usuarioById.getId());
    }


    @Test
    public void findAllUsuariosTest() {

        // Arrange
        usuarioRepository.save(
                Usuario.builder()
                        .usuario("usuario1").password("pass123").correo("usuario1@test.com").rol(Rol.USER).build()
        );
        usuarioRepository.save(
                Usuario.builder()
                        .usuario("usuario2").password("pass123").correo("usuario2@test.com").rol(Rol.ADMIN).build()
        );

        // Act
        List<Usuario> usuarios = usuarioRepository.findAll();

        // Assert
        assertTrue(usuarios.size() >= 2);
    }


    @Test
    public void findByCorreoAndPasswordTest() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .usuario("usuario123")
                .password("password456")
                .correo("usuario@test.com")
                .rol(Rol.USER)
                .build();
        usuarioRepository.save(usuario);

        // Act
        Usuario encontrado = usuarioRepository.findByCorreoAndPassword("usuario@test.com", "password456").orElse(null);

        // Assert
        assertNotNull(encontrado);
        assertEquals("usuario123", encontrado.getUsuario());
    }


    @Test
    public void deleteUsuarioTest() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .usuario("usuarioEjemplo")
                .password("ejemplo123")
                .correo("delete@test.com")
                .rol(Rol.USER)
                .build();
        Usuario saved = usuarioRepository.save(usuario);
        Long id = saved.getId();

        // Act
        usuarioRepository.deleteById(id);

        // Assert
        boolean exists = usuarioRepository.findById(id).isPresent();
        assertFalse(exists);
    }
}