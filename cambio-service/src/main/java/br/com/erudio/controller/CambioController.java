package br.com.erudio.controller;

import br.com.erudio.model.Cambio;
import br.com.erudio.repository.CambioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Tag(name = "Cambio Service endpoint") // Configuracao do Swagger
@RestController
@RequestMapping("cambio-service")
public class CambioController {

    @Autowired
    private Environment environment; //Com environment conseguimos acessas informacoes do ambiente que estamos trabalhando

    @Autowired
    private CambioRepository repository;

    @Operation(description = "Get cambio from currency.")
    @GetMapping(value = "/{amount}/{from}/{to}")
    public Cambio get(
            @PathVariable("amount") BigDecimal amount,
            @PathVariable("from") String from,
            @PathVariable("to") String to
            ) {
        var cambio = repository.findByFromAndTo(from, to);
        if (cambio == null) throw new RuntimeException("Currency Unsupported.");

        var port = environment.getProperty("local.server.port");
        BigDecimal conversionFactor = cambio.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(amount);
        cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING)); // setScale serve para definir a quantidade de casas decimais e o RoundingMode para arredondar
        cambio.setEnvironment(port);
        return cambio;
    }
}
