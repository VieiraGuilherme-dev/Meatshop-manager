# 🥩 MeatShop Manager - BackEnd

API REST para gestão financeira de açougues. Controla receitas, despesas, equipe e
categorias, com indicadores consolidados e exportação de relatórios.

Construída em Java com Spring Boot, PostgreSQL e autenticação via JWT, seguindo
arquitetura em camadas, versionamento de schema com Flyway e autorização por perfil.

**Documentação interativa:** https://meatshop-manager.onrender.com/swagger-ui.html

**Interface web:** https://meatshop-manager.vercel.app ([repositório do frontend](https://github.com/VieiraGuilherme-dev/Meatshop-manager-front))

> A API roda no plano gratuito do Render e hiberna após 15 minutos sem acesso.
> A primeira requisição pode levar até 1 minuto enquanto o serviço sobe.


## Stack

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot |
| Segurança | Spring Security + JWT (HS512) |
| Persistência | Spring Data JPA / Hibernate |
| Banco | PostgreSQL 18 |
| Migrations | Flyway |
| Documentação | SpringDoc OpenAPI (Swagger UI) |
| Build | Maven |
| Container | Docker (multi-stage) |
| Hospedagem | Render |

---

## Arquitetura

Organização em camadas, com responsabilidades separadas e comunicação sempre via DTOs. As entidades JPA nunca cruzam a fronteira do controller.

```
Controller  →  recebe a requisição, valida o payload, devolve o status HTTP
Service     →  regras de negócio, orquestração, transações
Repository  →  acesso a dados, queries e agregações
Entity      →  mapeamento objeto-relacional
DTO         →  contratos de entrada e saída da API
```

```
src/main/java/com/meatshopmanager/
├── config/       segurança, CORS, OpenAPI
├── controller/   endpoints REST
├── service/      regras de negócio
├── repository/   interfaces Spring Data e queries customizadas
├── entity/       entidades JPA
├── dto/          objetos de requisição e resposta
├── exception/    exceções de domínio e handler global
└── security/     filtro JWT, provider e entry point
```

**Por que DTOs em vez de entidades nos controllers.** Expor a entidade acopla o contrato
da API ao schema do banco: qualquer mudança em coluna vira mudança de contrato, e campos
sensíveis vazam por descuido. Com DTOs, entrada e saída são explícitas o
`FuncionarioRequestDTO` não aceita `id` nem `ativo`, e o `UsuarioResponseDTO` nunca
carrega o hash da senha.

**Agregações no banco, não em memória.** Os indicadores do dashboard usam `SUM` e
`GROUP BY` via `@Query`, retornando projeções diretamente. Carregar milhares de
lançamentos para somar na aplicação funcionaria com dados de demonstração e
quebraria em produção.

---

## Modelo de dados

```
categoria ──┬─< receita
            └─< expenses >── funcionario

usuario  (independente, usado apenas para autenticação)
```
O vínculo opcional entre despesa e funcionário permite rastrear gastos atribuíveis a
uma pessoa (vale-transporte, adiantamento, equipamento) sem obrigar que toda despesa
tenha um responsável.

A configuração usa `spring.jpa.hibernate.ddl-auto=validate`: o Hibernate confere se o
schema corresponde às entidades, mas quem cria e altera tabelas é o Flyway. Isso impede
que uma mudança acidental numa entidade altere o banco de produção.

---

## Autenticação e autorização

### Fluxo

1. `POST /api/auth/login` recebe email e senha
2. O `AuthenticationManager` valida as credenciais contra o hash BCrypt armazenado
3. Em caso de sucesso, o `JwtService` emite um token assinado em HS512 contendo
   o email no claim `sub` e o perfil no claim `role`
4. O cliente envia o token no header `Authorization: Bearer <token>` nas requisições seguintes
5. Um filtro (`OncePerRequestFilter`) intercepta cada requisição, valida a assinatura
   e a expiração, e popula o `SecurityContext`

### Perfis

| Ação | ADMIN | FUNCIONARIO |
|---|:---:|:---:|
| Consultar categorias, funcionários, receitas e despesas | ✓ | ✓ |
| Consultar indicadores do dashboard | ✓ | ✓ |
| Exportar relatórios | ✓ | ✓ |
| Criar, editar e excluir registros | ✓ | — |
| Demitir funcionário | ✓ | — |

A distinção é declarada por método com `@PreAuthorize`:

```java
@PreAuthorize("hasAnyRole('ADMIN','FUNCIONARIO')")
public ResponseEntity<Page<ReceitaResponseDTO>> listar(...)

@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<ReceitaResponseDTO> criar(...)
```
---

## Endpoints

### Autenticação

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| POST | `/api/auth/login` | Autentica e retorna o token JWT | público |
| POST | `/api/auth/registrar` | Cria um usuário | público |

### Categorias

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| GET | `/api/categorias` | Lista paginada, com filtro opcional por tipo | ambos |
| GET | `/api/categorias/{id}` | Busca por id | ambos |
| POST | `/api/categorias` | Cria | ADMIN |
| PUT | `/api/categorias/{id}` | Atualiza | ADMIN |
| DELETE | `/api/categorias/{id}` | Remove | ADMIN |

### Funcionários

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| GET | `/api/funcionarios` | Lista paginada | ambos |
| GET | `/api/funcionarios/{id}` | Busca por id | ambos |
| POST | `/api/funcionarios` | Cria | ADMIN |
| PUT | `/api/funcionarios/{id}` | Atualiza | ADMIN |
| PATCH | `/api/funcionarios/{id}/demitir` | Registra a demissão | ADMIN |
| DELETE | `/api/funcionarios/{id}` | Remove | ADMIN |

### Receitas

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| GET | `/api/receitas` | Lista paginada | ambos |
| GET | `/api/receitas/{id}` | Busca por id | ambos |
| POST | `/api/receitas` | Cria | ADMIN |
| PUT | `/api/receitas/{id}` | Atualiza | ADMIN |
| DELETE | `/api/receitas/{id}` | Remove | ADMIN |

### Despesas

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| GET | `/api/expenses` | Lista paginada | ambos |
| GET | `/api/expenses/{id}` | Busca por id | ambos |
| POST | `/api/expenses` | Cria | ADMIN |
| PUT | `/api/expenses/{id}` | Atualiza | ADMIN |
| DELETE | `/api/expenses/{id}` | Remove | ADMIN |

### Dashboard

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| GET | `/api/dashboard/resumo` | Indicadores consolidados do período | ambos |
| GET | `/api/dashboard/lucro` | Receitas, despesas e lucro | ambos |
| GET | `/api/dashboard/total` | Total de despesas | ambos |
| GET | `/api/dashboard/by-month` | Despesas agrupadas por mês | ambos |
| GET | `/api/dashboard/by-category` | Despesas agrupadas por categoria | ambos |
| GET | `/api/dashboard/export/pdf` | Relatório financeiro em PDF | ambos |
| GET | `/api/dashboard/export/excel` | Relatório financeiro em Excel | ambos |

#### `GET /api/dashboard/resumo`

Endpoint principal do painel. Aceita `mes` e `ano` como parâmetros opcionais, sem eles,
usa o período corrente.

```json
{
  "receitas": 51100.00,
  "despesas": 39055.00,
  "lucro": 12045.00,
  "margemLucro": 23.57,
  "variacaoReceitas": 11.09,
  "variacaoDespesas": -0.28,
  "variacaoLucro": 76.23,
  "funcionariosAtivos": 7,
  "totalFolha": 17700.00,
  "maiorCategoriaDespesa": {
    "nome": "Salários",
    "valor": 18300.00,
    "variacao": 0.00
  }
}
```

Os campos de variação retornam `null` quando não há dados no mês anterior.

### Folha de pagamento

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| POST | `/api/folha-pagamento/executar` | Gera as despesas de salário do período | ADMIN |

---

## Regras de negócio

### Demissão de funcionário

`PATCH /api/funcionarios/{id}/demitir`

```json
{ "dataDemissao": "2026-09-13" }
```

Duas validações são aplicadas antes de qualquer alteração:

1. A data de demissão não pode ser anterior à data de admissão
2. Um funcionário já inativo não pode ser demitido novamente

Violar qualquer uma retorna **422 Unprocessable Entity** com a mensagem correspondente.

**Por que 422 e não 400 ou 409.** A requisição está bem formada e é sintaticamente
válida, não é 400. Não há conflito de recurso duplicado, não é 409. O que ocorre é
uma regra de domínio violada sobre dados semanticamente corretos, que é exatamente o
que o 422 descreve.

---

## Tratamento de erros

Um `@RestControllerAdvice` centraliza a tradução de exceções em respostas HTTP,
mantendo os controllers livres de blocos `try/catch`.

| Situação | Exceção | Status |
|---|---|---|
| Recurso inexistente | `ResourceNotFoundException` | 404 |
| Regra de domínio violada | `RegraDeNegocioException` | 422 |
| Payload inválido | `MethodArgumentNotValidException` | 400 |
| Sem autenticação | tratado pelo `AuthenticationEntryPoint` | 401 |
| Sem permissão | `AccessDeniedException` | 403 |

A distinção entre 401 e 403 exigiu um `AuthenticationEntryPoint` customizado: por
padrão o Spring Security responde 403 em ambos os casos, o que confunde
"você não está autenticado" com "você está, mas não pode". O cliente usa essa
diferença para decidir entre redirecionar ao login ou exibir uma mensagem de permissão.

---

## Migrations

Todo o schema e os dados de apoio são versionados com Flyway, em
`src/main/resources/db/migration`.

| Versão | Descrição |
|---|---|
| `V1` | Schema inicial: tabelas, constraints e categorias padrão |
| `V2` | Dados de demonstração: funcionários, receitas e despesas |
| `V3` | Complemento dos dados nos meses mais recentes |
| `V4` | Usuário administrador padrão |

A V4 usa `INSERT ... SELECT ... WHERE NOT EXISTS`, o que a torna idempotente — ela
pode ser aplicada num banco que já possui o usuário sem violar a constraint de email
único nem derrubar o deploy.

Com isso, qualquer ambiente nasce completo a partir de um banco vazio: schema,
dados de demonstração e credencial de acesso.

---

## Rodando localmente

### Pré-requisitos

- Java 21
- PostgreSQL 14 ou superior
- Maven (ou use o wrapper incluído)

### Passos

```bash
git clone git@github.com:VieiraGuilherme-dev/Meatshop-manager.git
cd Meatshop-manager
```

Crie o banco:

```sql
CREATE DATABASE meatshop_manager;
```

A aplicação lê variáveis de ambiente com valores padrão para desenvolvimento local,
definidos em `application.properties`. Se seu PostgreSQL usa usuário e senha diferentes
de `postgres/postgres`, exporte-os antes de subir:

```bash
export SPRING_DATASOURCE_USERNAME=seu_usuario
export SPRING_DATASOURCE_PASSWORD=sua_senha
```

```bash
./mvnw spring-boot:run
```

O Flyway aplica as quatro migrations no primeiro start. A API sobe em
`http://localhost:8081`, e o Swagger fica em `http://localhost:8081/swagger-ui.html`.

### Com Docker

```bash
docker build -t meatshop-manager .
docker run -p 8081:8081 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/meatshop_manager \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  -e JWT_SECRET=$(openssl rand -base64 48) \
  meatshop-manager
```

O `Dockerfile` usa build multi-stage: a primeira etapa compila com o JDK completo,
a segunda copia apenas o JAR para uma imagem JRE. A imagem final não carrega Maven,
código-fonte nem ferramentas de build.

---

## Deploy

Hospedado no Render como serviço Docker, com deploy automático a cada push na `main`.
O banco é um PostgreSQL gerenciado na mesma região, acessado pela rede interna.

A URL da aplicação em produção está registrada na configuração de CORS junto ao
`corsConfigurationSource` do Spring Security. A ordem importa: o Security intercepta
a requisição antes do MVC, então uma configuração de CORS declarada apenas no
`WebMvcConfigurer` não tem efeito sobre requisições autenticadas.

---

## Repositório relacionado

[**Meatshop-manager-front**](https://github.com/VieiraGuilherme-dev/Meatshop-manager-front) interface web em React, Vite e Tailwind CSS.
