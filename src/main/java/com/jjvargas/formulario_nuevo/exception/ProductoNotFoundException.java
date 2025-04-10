package com.jjvargas.formulario_nuevo.exception;

public class ProductoNotFoundException extends RuntimeException {

    public ProductoNotFoundException(Long id) {
        super("Producto con ID " + id + " no encontrado");
    }

    public ProductoNotFoundException(String mensaje) {
        super(mensaje);
    }

    // UsuarioExistenteException.java
public class UsuarioExistenteException extends RuntimeException {
    public UsuarioExistenteException(String message) {
        super(message);
    }
}

// CredencialesInvalidasException.java
public class CredencialesInvalidasException extends RuntimeException {
    public CredencialesInvalidasException(String message) {
        super(message);
    }
}
}