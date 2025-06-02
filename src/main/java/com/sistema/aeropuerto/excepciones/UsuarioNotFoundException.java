package com.sistema.aeropuerto.excepciones;

public class UsuarioNotFoundException extends Exception{

    public UsuarioNotFoundException() {
        super("Usuario no encontrado en la base de datos");
    }

    public UsuarioNotFoundException(String message) {
        super(message);
    }
}
