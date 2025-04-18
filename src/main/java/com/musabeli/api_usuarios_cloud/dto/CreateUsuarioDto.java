package com.musabeli.api_usuarios_cloud.dto;

import com.musabeli.api_usuarios_cloud.enums.Rol;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateUsuarioDto {

    @NotBlank(message = "El campo usuario es obligatorio")
    @Size(min = 5, max = 20, message = "El nombre de usuario debe tener entre 5 y 20 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "El nombre de usuario permite letras y números, sin espacios ni caracteres especiales")
    private String usuario;

    @NotBlank(message = "El campo password es obligatorio")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial"
    )
    private String password;

    @NotBlank(message = "El campo correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @NotNull(message = "El campo rol es obligatorio")
    private Rol rol;
}
