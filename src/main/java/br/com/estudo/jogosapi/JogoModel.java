package br.com.estudo.jogosapi;


import java.util.List;
import java.util.Optional;

// Contrato: o controller conhece esta interface, sem conhecer o armazenamento.
public interface JogoModel {
    Jogo criar(Jogo jogo);
    List<Jogo> listar(String genero);
    Optional<Jogo> buscar(Long id);
    Optional<Jogo> atualizar(Long id, Jogo jogo);
    boolean excluir(Long id);
}
