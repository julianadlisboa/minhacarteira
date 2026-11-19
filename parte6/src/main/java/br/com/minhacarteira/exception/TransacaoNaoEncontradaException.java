package br.com.minhacarteira.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // faz a API responder 404
public class TransacaoNaoEncontradaException extends RuntimeException {

    public TransacaoNaoEncontradaException(Long id) {
        super("Transação não encontrada com id " + id);
    }
}