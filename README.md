### AedesControl
Sistema backend para controle e monitoramento inteligente de focos do mosquito Aedes aegypti. 
API REST desenvolvida para otimizar o trabalho de agentes de saúde e fornecer métricas em tempo real no combate à dengue, zika e chikungunya.

## Tecnologias utilizadas

- **Maven** - Gerenciamento de dependências
- **Java 21** - Linguagem de programação
- **Spring Boot** - Framework principal
- **PostgreSQL** - Banco de dados
- **JPA** - Persistência de dados
- **Hibernate** - ORM
- **Docker** - Containerização
- **Git** - Controle de versão
- **JUnit** - Testes unitários
- **Swagger/OpenAPI** - Documentação da API

## Pré requisitos
- Java 21 or higher
- Maven 3.6+
- Docker
- PostgreSQL

## Installation

```
git clone https://github.com/viitorgonzalez/AedesControl-backend.git
cd AedesControl-backend
mvn spring-boot:run
```

Aplicação disponivel em: http://localhost:8080

## Documentação da API

A API está documentada usando Swagger/OpenAPI. Após executar a aplicação, acesse:

```
http://localhost:8080/swagger-ui.html
http://localhost:8080/v3/api-docs
```

## Execução com Docker
Edite o arquivo docker-compose.yaml para configurar o Docker de acordo com o exemplo docker-compsoe.yaml.example

```
# Subir containers
docker-compose up -d

# Parar containers
docker-compose down
```

## Testes
```
mvn test
```

## Configuração

Edite o arquivo src/main/resources/application.yaml para configurar o PostGreSQL de acordo com o exemplo application.yaml.example
