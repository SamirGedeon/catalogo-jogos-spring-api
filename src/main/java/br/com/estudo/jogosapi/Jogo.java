package br.com.estudo.jogosapi;


import java.math.BigDecimal;

// Objeto interno do domínio. Não é exposto diretamente nos endpoints.
public class Jogo {
    private final Long id;
    private final String nome;
    private final String genero;
    private final BigDecimal preco;

    public Jogo(Long id, String nome, String genero, BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.preco = preco;
    }
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getGenero() { return genero; }
    public BigDecimal getPreco() { return preco; }
}
