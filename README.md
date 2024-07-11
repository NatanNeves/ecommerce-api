# E-commerce Spring Boot Application

Este é um projeto de aplicação de e-commerce desenvolvido usando Spring Boot com uma arquitetura em camadas. A aplicação permite gerenciar produtos e vendas, utilizando uma estrutura robusta e escalável.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.3.1
- Spring Data JPA
- Hibernate
- Lombok
- Jakarta Validation
- MySQL
- Maven

## Funcionalidades

- Gerenciamento de produtos
- Criação e gerenciamento de vendas
- Associação de itens a vendas
- Validação de dados de entrada

## Estrutura do Projeto

O projeto segue uma arquitetura em camadas para promover a separação de responsabilidades e facilitar a manutenção do código. As camadas principais são:

- **Controller**: Define os endpoints da API.
- **Service**: Contém a lógica de negócios.
- **Repository**: Gerencia a persistência de dados.

## Endpoints

Os endpoints seguem a convenção `/api/v1` e são documentados conforme abaixo:

### Produtos

- **GET /api/v1/products**: Lista todos os produtos.
- **GET /api/v1/products/{id}**: Obtém detalhes de um produto específico.
- **POST /api/v1/products**: Cria um novo produto.
- **PUT /api/v1/products/{id}**: Atualiza um produto existente.
- **DELETE /api/v1/products/{id}**: Deleta um produto.

### Vendas

- **GET /api/v1/sales**: Lista todas as vendas.
- **GET /api/v1/sales/{id}**: Obtém detalhes de uma venda específica.
- **POST /api/v1/sales**: Cria uma nova venda.
- **DELETE /api/v1/sales/{id}**: Deleta uma venda.


DIAGRAMA-ECOMMERCE-FINAL https://github.com/user-attachments/assets/fdd43078-238d-4992-b218-addf33605ff3
