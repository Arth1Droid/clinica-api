# 🏥 Clínica API

API REST desenvolvida com Java e Spring Boot para gerenciamento de agendamentos em uma clínica.

O projeto foi construído com foco em organização de código, aplicação de regras de negócio e boas práticas de desenvolvimento backend.

---

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database (ambiente de desenvolvimento)
- PostgresSQL
- Maven
- Jakarta Validation
- Swagger / OpenAPI

---

## Funcionalidades

- ✔ Cadastrar pacientes  
- ✔ Listar pacientes  
- ✔ Cadastrar profissionais  
- ✔ Listar profissionais  
- ✔ Criar agendamentos  
- ✔ Listar agendamentos com filtros  
- ✔ Cancelar agendamento
- ✔ Finalizar agendamento 

---

## Regras de negócio

- Um profissional não pode ter dois agendamentos no mesmo horário  
- Um paciente não pode ter dois agendamentos no mesmo horário  
- Não é permitido criar agendamentos com data/hora no passado  
- Cancelamento exige motivo obrigatório  
- Ao cancelar, o status é atualizado para `CANCELED` sem remover o registro  
- Apenas agendamentos `SCHEDULED` podem ser cancelados ou finalizados  
- Não é possível finalizar um agendamento antes do horário marcado  
- Regra de intervalo mínimo entre consultas para evitar sobrecarga de agenda  

---

## ⚙️ Como executar o projeto

### 🔧 Pré-requisitos

- Java 21+
- Maven 3.8+

---

### Passos para execução

1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/clinica-api.git
```
2. Acessar o projeto

```bash
cd clinica-api
```
3. Executar a aplicação

Com Maven:

```bash
./mvnw spring-boot:run
```
Com IDE(IntelliJ/Eclipse/Vscode):

```bash
ClinicaApiApplication
```
## 🗄️ Banco de dados

O projeto está configurado por padrão com banco em memória (H2), facilitando execução e testes locais.

---

### 📌 Configuração H2 (padrão)

```properties
spring.datasource.url=jdbc:h2:mem:clinica
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```
## Acesso ao console H2
http://localhost:8080/h2-console

---

## 🔄 Compatibilidade com outros bancos relacionais
O projeto também é compatível com outros bancos relacionais, como PostgreSQL e Oracle.
Basta ajustar o application.properties conforme o banco desejado.

 Exemplo - PostgreSQL
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/clinica
spring.datasource.username=postgres
spring.datasource.password=sua_senha

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```
Exemplo - Oracle
```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=clinica
spring.datasource.password=sua_senha

spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
spring.jpa.hibernate.ddl-auto=update
```
## Documentação da API (Swagger)
 API está documentada utilizando Swagger (OpenAPI):
 
```bash
http://localhost:8080/swagger-ui/index.html

```
Funcionalidades do Swagger:

 - Visualizar endpoints
 - Testar requisições diretamente
 - Consultar payloads e respostas
 - Facilitar entendimento da API

---

## Testes automatizados
O projeto possui testes automatizados cobrindo regras de negócio principais.
Executar testes:
Com Maven:

```bash
./mvnw test
```
Com IDE(IntelliJ/Eclipse):

  - Clique com o botão direito na pasta src/test/java
  - Ou execute classes específicas de teste
  - Ou rode diretamente os métodos de teste individualmente

---
## 📁 Estrutura do projeto
 - controllers → Endpoints REST (camada de API)
 - services → Regras de negócio
 - repositories → Acesso ao banco de dados
 - models/entities → Entidades JPA
 - dtos → Objetos de entrada e saída
 - mappers → Conversão entre entidades e DTOs
 - exceptions → Tratamento global de erros
