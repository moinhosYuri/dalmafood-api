package br.com.dalmafood.api.util.exceptions.entidades;

public class EntidadeException extends Exception {

    public EntidadeException() {
    }

    public EntidadeException(String message) {
        super(message);
    }

    public EntidadeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntidadeException(Throwable cause) {
        super(cause);
    }
}
