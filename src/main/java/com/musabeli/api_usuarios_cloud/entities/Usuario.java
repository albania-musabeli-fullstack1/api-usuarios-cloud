package com.musabeli.api_usuarios_cloud.entities;

import com.musabeli.api_usuarios_cloud.enums.Rol;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "usuario")
public class Usuario extends RepresentationModel<Usuario> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rol rol;
}
