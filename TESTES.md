# Relatório de Testes

Os testes do projeto estão localizados em:

```text
src/test/java/com/store/order_system/
```

---

# Frameworks Utilizados

Os seguintes frameworks foram utilizados para implementação dos testes:

* **JUnit 5** — framework principal de testes
* **Mockito** — utilizado para criação de mocks e isolamento de dependências

---

# Testes Unitários

Foram implementados testes unitários para validar as principais regras de negócio dos serviços da aplicação.

| Classe               | Objetivo                               |
| -------------------- | -------------------------------------- |
| `ProductServiceTest` | valida criação e operações de produtos |
| `OrderServiceTest`   | valida criação e cálculo de pedidos    |
| `PaymentServiceTest` | valida processamento de pagamentos     |
| `UserServiceTest`    | valida gerenciamento de usuários       |

Esses testes verificam o comportamento das classes de serviço de forma isolada, utilizando **mocks das dependências**.

---

# Testes de Integração

Os testes de integração validam a comunicação entre diferentes camadas do sistema.

Camadas testadas:

* **Controller**
* **Service**
* **Repository**
* **Database**
* **Security**

## Exemplo Implementado

```
OrderIntegrationTest
```

Esse teste verifica:

* criação de um pedido
* persistência dos dados no banco
* integração correta entre Controller, Service e Repository

---

# Execução dos Testes

Para executar todos os testes do projeto, utilize o comando:

```bash
mvn clean test
```

---

# Resultado da Execução

Após a execução dos testes, o resultado obtido foi:

```
Tests run: 6
Failures: 0
Errors: 0
Skipped: 0
```

Isso indica que todos os testes foram executados com sucesso, sem falhas ou erros.

