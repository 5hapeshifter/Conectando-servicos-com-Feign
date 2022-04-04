package br.com.erudio.controller;

import br.com.erudio.model.Book;
import br.com.erudio.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        var port = environment.getProperty("local.server.port");
        book.setEnvironment(port);
        return book;
    }
}
