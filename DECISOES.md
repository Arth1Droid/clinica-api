# DECISOES.md

## 1. Principais decisões técnicas

O projeto foi estruturado com foco em organização, clareza e separação de responsabilidades, seguindo boas práticas de desenvolvimento com Spring Boot.

As principais decisões técnicas foram:

- Uso de arquitetura em camadas (Controller, Service, Repository)
- Uso de DTOs para entrada e saída de dados, evitando exposição direta das entidades JPA
- Mapeamento manual entre entidades e DTOs através de Mappers
- Implementação das regras de negócio na camada de Service
- Uso de `GlobalExceptionHandler` com `@RestControllerAdvice` para centralização do tratamento de erros
- Uso de banco em memória H2 para facilitar execução e testes locais
- Documentação da API com Swagger (OpenAPI) para facilitar consumo e testes dos endpoints
- Uso de validações com Jakarta Validation (`@NotNull`, `@NotBlank`, `@Email`, etc.)

O foco principal foi garantir uma API consistente, organizada e de fácil entendimento.

---

## 2. O que foi priorizado e o que ficou de fora

### Priorizado:
- Implementação completa dos requisitos obrigatórios do teste
- Regras de negócio relacionadas a agendamento (conflito de horário, cancelamento e validações)
- Estrutura limpa e separação de responsabilidades
- Tratamento de exceções centralizado
- Filtros na listagem de agendamentos
- Testes automatizados cobrindo regras principais de negócio
- Facilidade de execução e testes locais com H2

### Ficou de fora:
- Interface frontend (Angular ou similar)
- Integração com Oracle em ambiente real (mantida apenas compatibilidade via configuração)

A decisão foi manter o foco na robustez da API backend e na correta implementação das regras de negócio exigidas.

---

## 3. Uso de IA

A inteligência artificial foi utilizada como apoio durante o desenvolvimento para:

- Sugestões de estruturação do projeto
- Apoio na organização da documentação
- Ideias para melhoria e validação de regras de negócio

A IA foi utilizada apenas como ferramenta de apoio para acelerar o desenvolvimento e melhorar a produtividade, sendo todo o 
conteúdo revisado, adaptado e validado manualmente para garantir aderência às regras de negócio e funcionamento correto da aplicação
