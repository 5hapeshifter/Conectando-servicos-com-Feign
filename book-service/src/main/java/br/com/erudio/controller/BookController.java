package br.com.erudio.controller;

import br.com.erudio.model.Book;
import br.com.erudio.repository.BookRepository;
import br.com.erudio.response.Cambio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("book-service")
public class BookController {

    @Autowired
    private Environment environment; //Com environment conseguimos acessas informacoes do ambiente que estamos trabalhando

    @Autowired
    BookRepository repository;

    @GetMapping(value = "{id}/{currency}")
    public Book findBook(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "currency") String currency
    ){
        var book = repository.getById(id);
        if(book == null) throw new RuntimeException("Book not found");

        HashMap<String, String> params = new HashMap<>();
        params.put("amount", book.getPrice().toString());
        params.put("from", "USD");
        params.put("to", currency);

        // Configuracao do endereco da request, do objeto que tera que ser feita a conversa e os parametros que devem ser informados para processar a requisicao
        var response = new RestTemplate().getForEntity("http://localhost:8000/cambio-service/" +
                        "{amount}/{from}/{to}",
                Cambio.class,
                params);

        var cambio = response.getBody();

        var port = environment.getProperty("local.server.port");
        book.setEnvironment(port);
        book.setPrice(cambio.getConvertedValue());
        return book;
    }
}
