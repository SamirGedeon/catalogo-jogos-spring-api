package br.com.estudo.jogosapi;


import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

// Compartilha a lógica entre as duas demonstrações, sem fingir usar SQL.
public abstract class ArmazenamentoEmMemoria implements JogoModel {
    private final Map<Long, Jogo> jogos = new LinkedHashMap<>();
    private final AtomicLong sequencia = new AtomicLong();

    @Override
    public synchronized Jogo criar(Jogo entrada) {
        long id = sequencia.incrementAndGet();
        Jogo jogo = new Jogo(id, entrada.getNome(), entrada.getGenero(), entrada.getPreco());
        jogos.put(id, jogo);
        return jogo;
    }
    @Override
    public synchronized List<Jogo> listar(String genero) {
        return jogos.values().stream()
            .filter(j -> genero == null || genero.isBlank()
                || j.getGenero().equalsIgnoreCase(genero.strip()))
            .toList();
    }
    @Override
    public synchronized Optional<Jogo> buscar(Long id) {
        return Optional.ofNullable(jogos.get(id));
    }
    @Override
    public synchronized Optional<Jogo> atualizar(Long id, Jogo entrada) {
        if (!jogos.containsKey(id)) return Optional.empty();
        Jogo jogo = new Jogo(id, entrada.getNome(), entrada.getGenero(), entrada.getPreco());
        jogos.put(id, jogo);
        return Optional.of(jogo);
    }
    @Override
    public synchronized boolean excluir(Long id) {
        return jogos.remove(id) != null;
    }
}
