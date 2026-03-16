# Order System API

## 📦 Instruções de Instalação e Configuração do Ambiente

---

# 1. Pré-requisitos

Antes de executar o projeto, certifique-se de que as seguintes ferramentas estão instaladas:

* **Java JDK 17**
* **Maven 3.8+**
* **MySQL**
* **IDE utilizada:** VS Code

Verifique as versões instaladas:

```bash
java -version
mvn -version
```

---

# 2. Baixar o repositório

Clone o projeto utilizando Git:

```bash
git clone https://github.com/seu-usuario/order-system.git
cd order-system
```

---

# 3. Configuração do Banco de Dados

Crie um banco de dados no seu gerenciador (PostgreSQL ou MySQL).

Exemplo:

```sql
CREATE DATABASE order_system;
```

Configure as credenciais no arquivo:

```
src/main/resources/application.yml
```

Exemplo de configuração:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ordersystem
    username: ordersystem_user
    password: senha123
    driver-class-name: com.mysql.cj.jdbc.Driver
```

---

# 4. Instalar dependências

Execute o Maven para baixar todas as dependências do projeto:

```bash
mvn clean install
```

---

# 5. Executar a aplicação

Inicie o servidor Spring Boot:

```bash
mvn spring-boot:run
```

Ou execute a classe principal:

```
OrderSystemApplication.java
```

A aplicação será iniciada em:

```
http://localhost:8080
```

---

# 6. Documentação da API (Swagger)

Após iniciar o projeto, a documentação da API pode ser acessada em:

```
http://localhost:8080/swagger-ui/index.html
```

---

# 7. Executar os testes

Para rodar os testes unitários e de integração:

```bash
mvn test
```

---

# 8. Estrutura do Projeto

```
src/main/java/com/store/order_system

controller/   -> endpoints da API
service/      -> serviços da aplicação
repository/   -> acesso ao banco de dados
model/        -> entidades do sistema
config/       -> configurações e inicialização de usuários
```

---

# 9. Autenticação e Controle de Acesso (Roles)

O sistema utiliza **Spring Security** para implementar autenticação e autorização baseada em **roles**.

A autenticação é realizada através de **HTTP Basic Authentication**, onde cada requisição protegida deve incluir credenciais válidas.

## Fluxo de autenticação

```
Client Request
      ↓
Spring Security Filter
      ↓
UserDetailsService
      ↓
Validação do usuário no banco
      ↓
Verificação das roles
      ↓
Acesso liberado ou negado
```

---

# Roles do Sistema

O sistema possui três níveis de acesso que controlam quais endpoints cada usuário pode acessar.

---

## ADMIN

Usuários com a role **ADMIN** possuem acesso completo ao sistema.

### Permissões

* Criar produtos
* Atualizar produtos
* Remover produtos
* Criar pedidos
* Visualizar todos os pedidos
* Gerenciar usuários

### Endpoints acessíveis

```
POST /products
PUT /products/{id}
DELETE /products/{id}
GET /orders
GET /users
```

---

## CLIENT

Usuários com a role **CLIENT** possuem acesso às funcionalidades básicas da aplicação.

### Permissões

* Consultar produtos disponíveis
* Criar pedidos
* Visualizar seus próprios pedidos

### Endpoints acessíveis

```
GET /products
POST /orders
GET /orders/{id}
```

---

## OPERATOR

Usuários com a role **OPERATOR** possuem acesso intermediário voltado para operações administrativas.

### Permissões

* Visualizar pedidos
* Atualizar pedidos
* Consultar produtos

### Endpoints acessíveis

```
GET /orders
PUT /orders/{id}
GET /products
```

---

# Exemplo de Requisição Autenticada

Para acessar endpoints protegidos é necessário enviar credenciais.

### Basic Authentication

```
Authorization: Basic base64(username:password)
```

### Exemplo utilizando curl

```bash
curl -u admin:admin123 http://localhost:8080/orders
```

---

# Respostas de Segurança

| Código           | Significado                            |
| ---------------- | -------------------------------------- |
| 401 Unauthorized | Usuário não autenticado                |
| 403 Forbidden    | Usuário autenticado, mas sem permissão |

Esse mecanismo garante que apenas usuários autorizados possam acessar determinadas funcionalidades da API.

---

# Usuários padrão (configurado no DataInitializer)

### ADMIN

```
email: admin@store.com
role: ADMIN
password: admin123
```

### OPERATOR

```
email: operator@store.com
role: OPERATOR
password: operator123
```

### CLIENT

```
email: client@store.com
role: CLIENT
password: client123
```

---

# 10. Fluxo da API

A API segue uma **arquitetura em camadas típica do Spring Boot**.

---

## a. Cliente realiza requisição HTTP

Um cliente (Postman, navegador ou frontend) envia uma requisição para um endpoint.

### Exemplo

```
POST /orders
```

### Corpo da requisição

```json
{
  "items": [
    {
      "product": {
        "id": 1
      },
      "quantity": 2
    }
  ]
}
```

---

## b. Controller recebe a requisição

O **Controller** recebe a requisição HTTP e encaminha para a camada de serviço.

### Exemplo

```
OrderController
```

### Responsabilidades

* Mapear endpoints REST
* Validar entrada de dados
* Retornar respostas HTTP

### Fluxo

```
Client → Controller
```

---

## c. Service executa a regra de negócio

A camada **Service** contém as regras de negócio.

### Exemplo

```
OrderService
```

### Responsabilidades

* calcular subtotal
* aplicar descontos
* aplicar taxas
* validar produtos
* preparar o pedido para persistência

### Fluxo

```
Controller → Service
```

---

## d. Repository acessa o banco de dados

A camada **Repository** comunica-se com o banco usando **Spring Data JPA**.

### Exemplos

```
OrderRepository
ProductRepository
UserRepository
```

### Responsabilidades

* salvar entidades
* buscar registros
* atualizar dados

### Fluxo

```
Service → Repository → Database
```

---

## e. Resposta retornada ao cliente

Após a operação ser concluída:

1. O **Repository** retorna os dados ao **Service**
2. O **Service** processa o resultado
3. O **Controller** envia a resposta HTTP

### Fluxo final

```
Database
   ↓
Repository
   ↓
Service
   ↓
Controller
   ↓
Client (JSON Response)
```

---

# Fluxo completo da API

```
Client
  ↓
Security (Authentication / Authorization)
  ↓
Controller
  ↓
Service (Business Logic)
  ↓
Repository
  ↓
Database
  ↓
Response JSON
```

Esse fluxo garante **separação de responsabilidades**, facilitando manutenção, testes e escalabilidade do sistema.

