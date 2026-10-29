package br.com.estudo.jogosapi;


public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(Long id) {
        super("Jogo " + id + " não encontrado");
    }
}
