package br.com.estudo.jogosapi;


public final class JogoMapper {
    private JogoMapper() {}
    public static Jogo toEntity(JogoRequest request, Long id) {
        return new Jogo(id, request.nome().strip(), request.genero().strip(), request.preco());
    }
    public static JogoResponse toResponse(Jogo jogo) {
        return new JogoResponse(jogo.getId(), jogo.getNome(), jogo.getGenero(), jogo.getPreco());
    }
}
