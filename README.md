# Criando uma API com SpringBoot

**Descrição:**
Uma aplicação Spring Boot que gerencia informações sobre funcionários, projetos e setores de uma empresa.

**Tecnologias:**
* Java
* Spring Boot
* Spring Data JPA


**Pré-requisitos:**
* JDK 11+
* Maven ou Gradle


**Estrutura do Projeto:**


* controllers: Contém os controladores REST que mapeiam as requisições HTTP para as funcionalidades da aplicação.
* dtos: Define as classes DTO (Data Transfer Object) utilizadas para transportar dados entre as camadas da aplicação, garantindo a separação de responsabilidades.
* exceptions: Contém as classes de exceção personalizadas para tratar erros específicos da aplicação.
* models: Define as entidades que representam os dados persistidos no banco de dados.
* repositories: Contém as interfaces dos repositórios, que permitem a interação com o banco de dados através do JPA.
* services: Contém as classes de serviço que encapsulam a lógica de negócio da aplicação.
