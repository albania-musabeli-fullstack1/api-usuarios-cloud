# FullStack 1 - Actividad Sumativa 2

## API Gestión de usuarios

API creada en Springboot para gestionar el registro de usuarios.

## Dependencias

* Springboot-starter-web
* Springboot-devtools
* Springboot-starter-data-jpa
* Lombok
* Oracle Driver: ojdbc
* oracle pki
* osdt_core
* osdt_cert

## Configurar variables de entorno para conexión a Oracle Cloud

* Copiar archivo .env.example y renombrar a .env
* Completar las variables de entorno relacionadas con el nombre de la base de datos, dirección del Wallet descomprimido,
  nombre de usuario y password.

## Endpoints para ambiente de Desarrollo

* GET: Obtener todos los usuarios: http://localhost:8080/api/usuario/getUsuarios
* GET: Obtener usuario por ID: http://localhost:8080/api/usuario/{id}
* POST: Crear un nuevo registro de usuario: http://localhost:8080/api/usuario
* PUT: Actualizar un usuario: http://localhost:8080/api/usuario/{id}
* DELETE: Eliminar un usuario: http://localhost:8080/api/usuario/{id}
* POST: Inicio de Sesión: http://localhost:8080/api/usuario/login

## Ejemplo para Request Body - Usuario

```json
{
  "id": 1,
  "usuario": "maria",
  "password": "123456ASDFqwer_",
  "correo": "maria@correo.com",
  "rol": "ADMIN"
}
```

## Ejemplo para Request Body - Login

```json
{
    "correo":"maria@correo.com",
    "password":"123456ASDFqwer_"
}
```
