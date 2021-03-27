package br.com.senior.avaliacao.exception;

public class AvaliacaoException extends RuntimeException {

    public AvaliacaoException(String msg) {
        super(msg);
    }

    public AvaliacaoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
