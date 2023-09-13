package br.com.dalmafood.api.util.exceptions.parametros;

public class ParametroException extends RuntimeException {

    public ParametroException() {
    }

    public ParametroException(String message) {
        super(message);
    }

    public ParametroException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParametroException(Throwable cause) {
        super(cause);
    }

}
