package br.com.estudo.jogosapi;


import java.math.BigDecimal;

public record JogoResponse(Long id, String nome, String genero, BigDecimal preco) {}
