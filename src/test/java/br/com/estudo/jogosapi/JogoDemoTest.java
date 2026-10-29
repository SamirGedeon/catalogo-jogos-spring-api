package br.com.estudo.jogosapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("demo")
class JogoDemoTest {
    @Autowired JogoModel model;
    @Test void perfilSelecionaImplementacaoComDadosIniciais() {
        assertInstanceOf(JogoModelDemo.class, model);
        assertEquals(2, model.listar(null).size());
        assertEquals("Minecraft", model.listar("AVENTURA").get(0).getNome());
    }
}
