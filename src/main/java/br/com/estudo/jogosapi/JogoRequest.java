package br.com.estudo.jogosapi;


import jakarta.validation.constraints.*;
import java.math.BigDecimal;

// Record é uma forma concisa de declarar um DTO com acessores.
public record JogoRequest(
    @NotBlank(message = "nome é obrigatório")
    @Size(max = 120, message = "nome deve ter no máximo 120 caracteres") String nome,
    @NotBlank(message = "genero é obrigatório")
    @Size(max = 60, message = "genero deve ter no máximo 60 caracteres") String genero,
    @NotNull(message = "preco é obrigatório")
    @DecimalMin(value = "0.00", message = "preco não pode ser negativo")
    @Digits(integer = 9, fraction = 2, message = "preco deve ter até 9 inteiros e 2 casas decimais") BigDecimal preco
) {}
