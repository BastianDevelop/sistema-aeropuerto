package com.sistema.aeropuerto.excepciones;

public class UsuarioFoundException extends Exception {

    public UsuarioFoundException() {
        super("Usuario ya existe en la base de datos");
    }
    public UsuarioFoundException(String message) {
        super(message);
    }
}
