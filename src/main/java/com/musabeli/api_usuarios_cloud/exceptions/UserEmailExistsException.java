package com.musabeli.api_usuarios_cloud.exceptions;

public class UserEmailExistsException extends RuntimeException {
    public UserEmailExistsException(String message) {
        super(message);
    }
}
