package br.com.estudo.jogosapi;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("memoria")
public class JogoModelMemoria extends ArmazenamentoEmMemoria {}
