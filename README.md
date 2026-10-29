# Catálogo de Jogos API

Projeto de estudo de Laboratório de Programação FullStack: API REST de jogos com CRUD, filtro por gênero, DTOs, validação, erros padronizados e injeção por construtor.

## Tecnologias e requisitos

Java 17 ou 21, Maven e Spring Boot 3.5.15. O código é compilado para Java 17 e pode rodar no JDK 21 usado nas aulas. Spring Web, Validation e testes automatizados. Valores monetários usam BigDecimal. Sem Lombok e sem banco externo.

## Abrir no IntelliJ

1. Extraia o ZIP, se necessário. Use **File > Open** e selecione a pasta com `pom.xml`.
2. Confirme a importação como projeto Maven e espere as dependências baixarem.
3. Em **File > Project Structure > Project**, escolha um JDK instalado (17 ou 21). O diretório precisa conter `bin/java.exe` no Windows.
4. Se necessário, clique com o botão direito em `pom.xml` e escolha **Add as Maven Project**; depois use **Reload All Maven Projects** no painel Maven.
5. Abra `src/main/java/br/com/estudo/jogosapi/JogosApiApplication.java` e execute o método `main`.
6. Espere `Started JogosApiApplication` e abra `http://localhost:8080/jogos`. A resposta inicial será `[]`.

O Maven Wrapper está incluído para executar sem instalar Maven separadamente. O primeiro uso precisa de internet.

```powershell
.\mvnw.cmd spring-boot:run
.\mvnw.cmd test
```

No Linux/macOS: `./mvnw spring-boot:run` e `./mvnw test`.

## Rotas

| Método | Rota | Resultado |
|---|---|---|
| POST | `/jogos` | 201, JSON e cabeçalho Location |
| GET | `/jogos` | 200 e lista |
| GET | `/jogos?genero=RPG` | 200 e lista filtrada (igualdade sem distinguir maiúsculas) |
| GET | `/jogos/{id}` | 200 ou 404 |
| PUT | `/jogos/{id}` | 200 ou 404; envie todos os campos |
| DELETE | `/jogos/{id}` | 204 sem corpo ou 404 |

No Postman, para POST e PUT, selecione **Body > raw > JSON**. Envie `Content-Type: application/json` e use `Accept: application/json`.

```json
{"nome":"Minecraft","genero":"Aventura","preco":99.90}
```

O ID é gerado no servidor. Nome e gênero são obrigatórios e não podem conter apenas espaços. Preço é obrigatório, não negativo e tem no máximo duas casas decimais. JSON com propriedades desconhecidas é rejeitado. Entrada inválida retorna 400; tipo de entrada incompatível, 415; representação de saída incompatível, 406. Uma busca sem resultados devolve 200 e `[]`.

Arquivo `requisicoes.http`: exemplos prontos para o cliente HTTP do IntelliJ, caso disponível, ou para reproduzir no Postman.

## Como estudar o código

1. `Jogo`: atributos do domínio e getters.
2. `JogoRequest` e `JogoResponse`: contratos de entrada e saída. São records; por exemplo, `request.nome()` lê o campo, sem escrever getters manualmente.
3. `JogoMapper`: converte DTO em entidade e entidade em resposta.
4. `JogoModel`: interface que define o que o serviço oferece.
5. `ArmazenamentoEmMemoria`: regras de armazenamento, busca, atualização e exclusão.
6. `JogoModelMemoria` e `JogoModelDemo`: duas implementações selecionadas com `@Profile`.
7. `JogoController`: rotas, parâmetros, validação e respostas HTTP.
8. `ApiExceptionHandler`: tratamento centralizado de erros.

Fluxo de cadastro: JSON > Request DTO > validação > Controller > Mapper > Model > entidade criada > Response DTO > JSON.

`@PathVariable` recebe o ID do caminho. `@RequestParam` recebe o gênero depois de `?`. `@RequestBody` recebe o JSON. `@Valid` ativa as regras do DTO. `ResponseEntity.created(...)` define 201 e Location.

## Trocar a implementação (IoC/DI)

Em `src/main/resources/application.properties`, altere:

```properties
spring.profiles.active=demo
```

Reinicie. O perfil `demo` inclui dois jogos iniciais; `memoria` começa vazio. Ative apenas um desses perfis por vez. O controller recebe a interface pelo construtor, sem criar implementações com `new`. As duas versões armazenam em memória; nenhuma usa SQL.

## Limites

Projeto didático sem autenticação, persistência, paginação ou uso em produção. Os dados desaparecem ao encerrar o processo. O preço zero é permitido para jogos gratuitos. Ao mudar o código, reinicie a aplicação para carregá-lo. Se a porta 8080 estiver ocupada, encerre outra execução ou altere `server.port`.

## Prática para a prova

- Recrie GET por ID sem copiar e explique 200 versus 404.
- Adicione filtro por parte do nome usando `@RequestParam`.
- Explique por que Request DTO não recebe ID.
- Troque o perfil e observe o controller permanecer igual.
- Envie nome vazio, preço negativo, JSON quebrado e um ID textual.
- Adapte o projeto para livros, substituindo gênero por autor.

## Verificação realizada

Três testes automatizados passaram: ciclo completo de CRUD e filtro; validação e negociação HTTP; seleção do perfil demo. Os testes usam MockMvc com o contexto real do Spring. A configuração de Mockito em src/test/resources evita exigir anexação de um agente à JVM em ambientes restritos; os testes não usam mocks.
