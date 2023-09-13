package br.com.dalmafood.api.util.exceptions.identificador;

public class IdentificadorException extends Exception{

    public IdentificadorException() {
    }

    public IdentificadorException(String message) {
        super(message);
    }

    public IdentificadorException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdentificadorException(Throwable cause) {
        super(cause);
    }
}
