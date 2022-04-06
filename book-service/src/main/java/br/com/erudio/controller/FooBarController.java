package br.com.erudio.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("book-service")
public class FooBarController {

    private Logger logger = LoggerFactory.getLogger(FooBarController.class);

    // fallbackMethod e o metodo que sera chamado para responder a situacao quando ocorrer um erro e esgotar a tentativa de retry
    //@Retry(name = "foo-bar", fallbackMethod = "fallbackMethod") // Configuracao de tentativas de request que deve ser feita configurada no application.yml, tambem podemos implementar essa configuracao por endpoint
    @CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod") // Quando ocorre muitas falhas de requisicoes, o circuit breaker envia as requisicoes direto para o fallback method para a aplicacao nao colapsar
    @GetMapping("/foo-bar")
    public String foobar(){
        logger.info("Request to foo-bar is received!");
        var response = new RestTemplate()
                .getForEntity("http://localhost:8080/foo-bar", String.class); // endereco errado propositalmente para gerar erro
        //return "Foo-Bar!!";
        return response.getBody();
    }

    public String fallbackMethod(Exception ex) { // Metodo que sera executado e sera chamado pelo retry do metodo acima, foobar
        return "fallbackMethod foo-bar!!!";
    }

}
