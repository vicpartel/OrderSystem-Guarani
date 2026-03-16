# Implementação de CI/CD

O projeto utiliza **Integração Contínua (CI)** para garantir que o código seja automaticamente compilado e testado a cada alteração enviada ao repositório.

A automação é realizada utilizando **GitHub Actions**.

---

# Estrutura do Pipeline

O pipeline executa automaticamente as seguintes etapas:

1. Download do código do repositório
2. Configuração do ambiente Java
3. Instalação das dependências Maven
4. Compilação do projeto
5. Execução dos testes unitários e de integração
6. Geração do artefato da aplicação

---

# Fluxo do Pipeline

```text
Developer Push
      ↓
GitHub Repository
      ↓
CI Pipeline (GitHub Actions)
      ↓
Build Maven
      ↓
Run Tests
      ↓
Generate Artifact (.jar)
```

---

# Configuração do GitHub Actions

Criar o arquivo:

```text
.github/workflows/ci.yml
```

Conteúdo do pipeline:

```yaml
name: CI Pipeline

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v3

      - name: Setup Java
        uses: actions/setup-java@v3
        with:
          distribution: "temurin"
          java-version: "17"

      - name: Build with Maven
        run: mvn clean install

      - name: Run Tests
        run: mvn test
```

---

# Resultado da Pipeline

A cada **push** ou **pull request**, o GitHub executará automaticamente:

* build do projeto
* execução dos testes
* verificação de falhas

Se algum teste falhar, o pipeline será marcado como **failed**, impedindo a integração de código com erros.

---

# Benefícios da CI/CD

* validação automática do código
* execução contínua de testes
* maior confiabilidade do sistema
* prevenção de regressões
* automação do processo de build

