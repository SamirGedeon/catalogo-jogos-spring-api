package br.com.estudo.jogosapi;


import java.math.BigDecimal;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

// Segunda implementação didática: inicia com dados de exemplo, também na memória.
@Service
@Profile("demo")
public class JogoModelDemo extends ArmazenamentoEmMemoria {
    public JogoModelDemo() {
        criar(new Jogo(null, "Minecraft", "Aventura", new BigDecimal("99.90")));
        criar(new Jogo(null, "Stardew Valley", "Simulação", new BigDecimal("24.99")));
    }
}
