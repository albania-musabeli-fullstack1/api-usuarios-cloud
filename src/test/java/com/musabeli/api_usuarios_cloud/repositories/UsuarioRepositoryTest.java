package com.musabeli.api_usuarios_cloud.repositories;

import com.musabeli.api_usuarios_cloud.entities.Usuario;
import com.musabeli.api_usuarios_cloud.enums.Rol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}