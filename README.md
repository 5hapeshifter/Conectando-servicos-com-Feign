# Conectando-servicos-com-Feign
Modulo do curso (Microservices do 0 com Spring Cloud, Spring Boot e Docker - Leandro Costa) para conectar serviços utilizando o Feign, Load Balancing com Eureka e Spring Cloud Gateway, configurações com Resilienc4j, Zipkin, Sleuth, Open API UI(substituto do Swagger)

# Acessar os endpoints via Gateway
- Book service - http://localhost:8765/book-service/14/BRL;
- Cambio service - http://localhost:8765/cambio-service/8/USD/CLP;

# Caminhos(padrão) para acessar a documentação em JSON (Swagger) das aplicações sem GATEWAY:
- Book service - http://localhost:8100/v3/api-docs;
- Cambio service - http://localhost:8000/v3/api-docs

# Caminhos para acessar a documentação GRÁFICA (Swagger) das aplicações:
- Book service - http://localhost:8100/swagger-ui.html
- Cambio service - http://localhost:8000/swagger-ui.html

# Caminho para acessar a documentação dos serviços via GATEWAY
- http://localhost:8765/swagger-ui.html

# Caminho padrão de acesso ao Zipkin
- http://localhost:9411/zipkin

# Caminho padrão de acesso ao Eureka
- http://localhost:8761


