# CRUD Médicos Spring Boot

**Minha primeira aplicação desenvolvida com Spring Boot:**  
Uma API RESTful que implementa um CRUD para gerenciar uma lista de médicos. Todas as funcionalidades são protegidas por autenticação, garantindo que apenas usuários autenticados possam acessar os recursos.

## Funcionalidades

- **Autenticação JWT**: Usuários devem estar autenticados para acessar as rotas.
- **Gerenciamento de Médicos**:  
  - Criação, leitura, atualização e deleção (lógica) de médicos.
  - Paginação e ordenação dos resultados da listagem.

---

## Como funciona?

### Estrutura de dados para médicos (DTO JSON)
Ao cadastrar ou atualizar um médico, use o seguinte formato:  
```json
{
  "nome": "Dr. João",
  "especializacao": "ORTOPEDISTA",
  "endereco": {
    "rua": "Rua das Flores",
    "complemento": "Apto 101",
    "numero": "123"
  },
  "telefone": "(21) 91234-5678"
}
```

Especializações disponíveis
```java
public enum Especializacao {
    ORTOPEDISTA,
    UROLOGISTA,
    CIRURGIA
}
```
## Como testar?

### 1. Instale as dependências e crie o banco de dados antes de iniciar o projeto
Certifique-se de ter o Maven instalado e execute o comando:  
```bash
mvn install
```
Crie um banco de dados PostgreSQL nomeado como *teste_alura*:
```sql
CREATE DATABASE teste_alura;
```

Ou altere as informações de conxeão com o DB no arquivo *application.properties*):
```application.properties
spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/teste_alura //*ESTE É UM EXEMPLO DE COMO DEVE ESTAR NO SEU PROJETO*
spring.datasource.username=*INSIRA O USUÁIO UTILIZADO PARA FAZER A CONEXÃO*
spring.datasource.password=*INSIRA A SUA SENHA DA CONEXÃO*
```

O flyway ficará encarregado de criar as tabelas no banco de dados utilizando as migrations salvas.

### 2. Crie um usuário
Utilize a rota POST /cadastro para criar um novo usuário.
Exemplo de corpo da requisição:
```json
{
  "username": "usuario123",
  "password": "senha123"
}
```

### 3. Autentique-se
Use a rota POST /login para obter um token JWT.
Exemplo de corpo da requisição:
```json
{
  "username": "usuario123",
  "password": "senha123"
}
```

A resposta incluirá um token no seguinte formato:
```json
{
  "token": "Bearer <seu_token_aqui>"
}
```

### 4. Adicione o token às requisições
Inclua o token no cabeçalho Authorization para acessar as demais rotas:
```makefile
Authorization: Bearer <seu_token_aqui>
```

## Rotas da API

### **Listar médicos**
- **GET `/medicos`**: Retorna uma lista paginada de médicos.  
  - **Exemplo com parâmetros:**  
    ```bash
    /medicos?size=5&page=1&sort=nome,asc
    ```
    - `size`: Número de itens por página (padrão: 10).  
    - `page`: Número da página (padrão: 0).  
    - `sort`: Campo de ordenação (ex.: `nome`, `especializacao`) e direção (`asc` ou `desc`).

---

### **Detalhar médico**
- **GET `/medicos/{id}`**: Retorna as informações de um médico específico pelo `id`.

---

### **Criar médico**
- **POST `/medicos`**: Adiciona um novo médico.  
  - **Corpo da requisição:**  
    ```json
    {
      "nome": "Dr. João",
      "especializacao": "ORTOPEDISTA",
      "endereco": {
        "rua": "Rua das Flores",
        "complemento": "Apto 101",
        "numero": "123"
      },
      "telefone": "(21) 91234-5678"
    }
    ```

---

### **Atualizar médico**
- **PUT `/medicos`**: Atualiza as informações de um médico.  
  - **Corpo da requisição:**  
    Inclua o `id` do médico no corpo:  
    ```json
    {
      "id": 1,
      "telefone": "(21) 91122-1111"
    }
    ```

---

### **Deletar médico (deleção lógica)**
- **DELETE `/medicos/{id}`**: Marca o médico como inativo.  
  - **Observação:**  
    Médicos inativos ainda podem ser acessados via **GET `/medicos/{id}`**, mas não aparecem na listagem geral (**GET `/medicos`**).  
    Isso é gerenciado pelo método `findAllByAtivoTrue()` no repositório da entidade.
