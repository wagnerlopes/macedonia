# Projeto Macedonia - Sistema de Emissão de Guias de Saúde

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Status](https://img.shields.io/badge/status-active-success.svg)]()
[![License](https://img.shields.io/badge/license-MIT-lightgrey.svg)]()

Resumo
------
Macedonia é um sistema para Emissão Eletrônica de Guias de Saúde, desenvolvido com Spring Boot 4, Spring Data e Thymeleaf 3.1. Foi desenvolvido com JDK 21 e focado em funcionalidades para beneficiários, estabelecimentos e guias de encaminhamento.

Principais funcionalidades
- Emissão eletrônica de guias de saúde
- Cadastro e listagem de beneficiários
- Cadastro e listagem de estabelecimentos
- Gerenciamento e filtro de guias
- Views server-side com Thymeleaf
- Persistência com Spring Data (JPA/Repositories)

Tecnologias
- Java 21
- Spring Boot 4
- Spring Data (JPA)
- Thymeleaf 3.1
- Banco de dados (H2/PostgreSQL/MySQL — configurar conforme ambiente)
- Maven (ou Gradle) — adapte conforme o build usado no projeto

Requisitos
- JDK 21
- Maven 3.8+ (ou Gradle, conforme o projeto)
- Banco de dados (configurar em `application.properties` / `application.yml`)

Instalação e execução (desenvolvimento)
1. Clone o repositório:
   git clone https://github.com/wagnerlopes/macedonia.git
2. Entre no diretório do projeto:
   cd macedonia
3. Build e execução (exemplo com Maven):
   ./mvnw clean package
   ./mvnw spring-boot:run
   Ou gerar jar e executar:
   ./mvnw clean package
   java -jar target/*.jar
4. Acesse a aplicação em:
   http://localhost:8080

Configuração
- Variáveis importantes (em `src/main/resources/application.properties` ou `application.yml`):
  - spring.datasource.url
  - spring.datasource.username
  - spring.datasource.password
  - spring.jpa.hibernate.ddl-auto
- Para desenvolvimento rápido, você pode usar H2 (in-memory) e console H2 habilitado.

Uso:
- Rotas principais (exemplos)
  - /beneficiarios — listagem e filtros de beneficiários
  - /estabelecimentos — listagem e filtros de estabelecimentos
  - /guias — criação e listagem de guias

Reporte bugs / abertura de issues:
- Use a seção de Issues no GitHub:
  https://github.com/wagnerlopes/macedonia/issues

Boas práticas para PRs:
- Incluir descrição do que foi alterado e por quê
- Referenciar issues relacionadas
- Incluir screenshots se houver alteração nas views

Licença
(c) 2026 WagnerSoft. [https://github.com/LICENSE]

Contato
- Wagner Lopes — https://github.com/wagnerlopes
- Adicionar CI (GitHub Actions) e badges reais
- Atualizar licença conforme necessário
