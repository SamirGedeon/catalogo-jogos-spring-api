package br.com.estudo.jogosapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JogoApiTest {
    @Autowired MockMvc mvc;
    @Autowired com.fasterxml.jackson.databind.ObjectMapper json;

    @Test void cicloCrudEFiltro() throws Exception {
        String response = mvc.perform(post("/jogos").contentType(MediaType.APPLICATION_JSON)
            .content("{\"nome\":\"Portal\",\"genero\":\"Puzzle\",\"preco\":19.90}"))
            .andExpect(status().isCreated()).andExpect(header().exists("Location"))
            .andReturn().getResponse().getContentAsString();
        long id = json.readTree(response).get("id").asLong();
        mvc.perform(get("/jogos/" + id)).andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Portal"));
        mvc.perform(get("/jogos").param("genero", "puzzle"))
            .andExpect(status().isOk()).andExpect(jsonPath("$[0].nome").value("Portal"));
        mvc.perform(put("/jogos/" + id).contentType(MediaType.APPLICATION_JSON)
            .content("{\"nome\":\"Portal 2\",\"genero\":\"Puzzle\",\"preco\":29.90}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Portal 2"));
        mvc.perform(delete("/jogos/" + id)).andExpect(status().isNoContent()).andExpect(content().string(""));
        mvc.perform(get("/jogos/" + id)).andExpect(status().isNotFound());
        mvc.perform(delete("/jogos/" + id)).andExpect(status().isNotFound());
    }
    @Test void entradaInvalidaEContratoHttp() throws Exception {
        mvc.perform(post("/jogos").contentType(MediaType.APPLICATION_JSON)
            .content("{\"nome\":\" \" ,\"genero\":\"RPG\",\"preco\":-1}"))
            .andExpect(status().isBadRequest()).andExpect(jsonPath("$.campos.length()").value(2));
        mvc.perform(get("/jogos/abc")).andExpect(status().isBadRequest());
        mvc.perform(post("/jogos").contentType(MediaType.APPLICATION_JSON).content("{"))
            .andExpect(status().isBadRequest());
        mvc.perform(post("/jogos").contentType(MediaType.TEXT_PLAIN).content("teste"))
            .andExpect(status().isUnsupportedMediaType());
        mvc.perform(get("/jogos").accept(MediaType.TEXT_HTML)).andExpect(status().isNotAcceptable());
        mvc.perform(get("/jogos").param("genero", "inexistente"))
            .andExpect(status().isOk()).andExpect(content().json("[]"));
    }
}
