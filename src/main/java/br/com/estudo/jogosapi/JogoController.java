package br.com.estudo.jogosapi;


import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jogos", produces = "application/json")
public class JogoController {
    private final JogoModel model;

    // Injeção pelo construtor: o Spring entrega a implementação do perfil ativo.
    public JogoController(JogoModel model) { this.model = model; }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<JogoResponse> criar(@Valid @RequestBody JogoRequest request) {
        Jogo criado = model.criar(JogoMapper.toEntity(request, null));
        return ResponseEntity.created(URI.create("/jogos/" + criado.getId()))
            .body(JogoMapper.toResponse(criado));
    }
    @GetMapping
    public List<JogoResponse> listar(@RequestParam(name = "genero", required = false) String genero) {
        return model.listar(genero).stream().map(JogoMapper::toResponse).toList();
    }
    @GetMapping("/{id}")
    public JogoResponse buscar(@PathVariable("id") Long id) {
        return JogoMapper.toResponse(model.buscar(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException(id)));
    }
    @PutMapping(value = "/{id}", consumes = "application/json")
    public JogoResponse atualizar(@PathVariable("id") Long id, @Valid @RequestBody JogoRequest request) {
        return JogoMapper.toResponse(model.atualizar(id, JogoMapper.toEntity(request, id))
            .orElseThrow(() -> new RecursoNaoEncontradoException(id)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        if (!model.excluir(id)) throw new RecursoNaoEncontradoException(id);
        return ResponseEntity.noContent().build();
    }
}
