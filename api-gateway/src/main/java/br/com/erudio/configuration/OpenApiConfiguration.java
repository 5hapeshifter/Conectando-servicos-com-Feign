package br.com.erudio.configuration;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class OpenApiConfiguration {

    @Bean
    @Lazy(value = false) // Configuracao para que esse metodo seja carregado assim que a aplicacao iniciar, nao pode esperar
    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters config,
                                     RouteDefinitionLocator locator) {
        // Identificando os servicos que possuel documentacao Swagger e agrupando
        var definitions = locator.getRouteDefinitions().collectList().block(); // Estamos bloqueando o arquivo enquanto ele estiver sendo usado

        definitions.stream().filter(
                routeDefinition -> routeDefinition.getId()
                        .matches(".*-service"))
                .forEach(routeDefinition -> {
                    String name = routeDefinition.getId();
                    config.addGroup(name);
                    GroupedOpenApi.builder() // Esta adicionando todas as rotas que tem 'service' no nome(que estao no application.yml) e vai adicionar ao Grouped, que é um conjunto de documentacao Swagger
                            .pathsToMatch("/" + name + "/**")
                            .group(name)
                            .build();
                });
        return new ArrayList<>();
    }

}
