package br.com.erudio.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean // configuracoes de rota onde podemos configurar muitas opcoes das requisicoes como header e parametros
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
//                .route(p -> p.path("/get") // padrao que sera interceptado e sera redirecionado para a uri abaixo
//                        .filters(f -> f
//                                .addRequestHeader("Hello", "World")
//                                .addRequestParameter("Hello", "World"))
//                        // A configuracao abaixo serve para encurtar o path e nao ficar com nome duplicado, http://localhost:8765/cambio-service/cambio-service/8/USD/CLP, de cambio-service, novo path http://localhost:8765/cambio-service/8/USD/CLP
//                        .uri("http://httpbin.org:80")) // ferramenta padrao de diagnostico do Spring que converte chamadas http em json como resposta do diagnostico da sua api
                .route(p -> p.path("/cambio-service/**") // estamos enviando todas as requisicoes do cambio-service para o load balancer do cambio service
                        .uri("lb://cambio-service")) // nome do servico registrado no eureka, quando a request chega no api-gateway por esse endereço, podemos acessar o Eureka e encontrar as locations para fazer o balancemento
                .route(p -> p.path("/book-service/**") // estamos enviando todas as requisicoes do cambio-service para o load balancer do cambio service
                        .uri("lb://book-service")) // nome do servico registrado no eureka, quando a request chega no api-gateway por esse endereço, podemos acessar o Eureka e encontrar as locations para fazer o balancemento
                .build();
    }

}
