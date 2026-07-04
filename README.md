# 🎮 DSList — Catálogo de Jogos

API REST de um catálogo de jogos organizados em listas personalizadas. O projeto vai além de um CRUD: implementa relacionamento N-N com dados extras, DTOs, projections e reordenação de itens dentro de uma lista via consulta nativa.

> 📚 **Status:** projeto de estudos concluído — construído para aprofundar o padrão de camadas, perfis de ambiente e boas práticas do ecossistema Spring.

## 🛠️ Tecnologias

- **Java 21**
- **Spring Boot 3.4** (Web, Data JPA)
- **H2 Database** — banco em memória (perfil `test`)
- **PostgreSQL** — perfis `dev` e `prod`
- **Maven**

## 🏗️ Modelo de domínio

```
┌──────────┐       ┌───────────────┐       ┌──────────────┐
│   Game   │ 1───* │   Belonging   │ *───1 │   GameList   │
└──────────┘       │  (position)   │       └──────────────┘
                   └───────────────┘
```

- `Game` — jogo com título, ano, gênero, plataformas, score, imagens e descrições
- `Gamelist` — lista personalizada de jogos (ex.: "Aventura e RPG", "Jogos de plataforma")
- `Belonging` — entidade de associação N-N com chave composta (`BelongingPK`) e atributo extra `position`, que controla a ordem dos jogos dentro da lista

## 📌 Endpoints

| Método | Rota | Descrição |
|--------|------|-----------|
| `GET` | `/games` | Lista todos os jogos (DTO resumido) |
| `GET` | `/games/{id}` | Detalhes completos de um jogo |
| `GET` | `/lists` | Lista todas as listas de jogos |
| `GET` | `/lists/{listId}/games` | Jogos de uma lista, ordenados por posição |
| `POST` | `/lists/{listId}/replacement` | Move um jogo de posição dentro da lista |

### Exemplo — reordenando uma lista

```http
POST /lists/1/replacement
Content-Type: application/json

{
  "sourceIndex": 3,
  "destinationIndex": 1
}
```

A operação remove o item da posição de origem, reinsere na posição de destino e atualiza no banco apenas o intervalo de posições afetado.

## ⚙️ Perfis de ambiente

| Perfil | Banco | Uso |
|--------|-------|-----|
| `test` (padrão) | H2 em memória | Desenvolvimento rápido, com console em `/h2-console` e seed automático via `import.sql` |
| `dev` | PostgreSQL local | Homologação local |
| `prod` | PostgreSQL via variáveis de ambiente (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`) | Deploy |

O perfil ativo é controlado pela variável `APP_PROFILE` (padrão: `test`), e as origens de CORS pela variável `CORS_ORIGINS`.

## 🚀 Como rodar

### Pré-requisitos

- Java 21+
- (Opcional) PostgreSQL para os perfis `dev`/`prod`

### Rodando com H2 (perfil test — zero configuração)

```bash
git clone https://github.com/luanmvcosta0/dslist.git
cd dslist
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080` já populada com os dados de exemplo do `import.sql`. O console do H2 fica disponível em `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`, usuário `sa`, sem senha).

### Rodando com PostgreSQL (perfil dev)

1. Suba um PostgreSQL local na porta `5433` com um banco chamado `dslist` (via Docker, por exemplo):

```bash
docker run --name dslist-pg -e POSTGRES_PASSWORD=1234567 -e POSTGRES_DB=dslist -p 5433:5432 -d postgres
```

2. Rode a aplicação com o perfil `dev`:

```bash
APP_PROFILE=dev ./mvnw spring-boot:run
```

## 🎯 O que eu pratiquei nesse projeto

- Relacionamento **N-N com atributos extras** e chave primária composta (`@Embeddable`, `@EmbeddedId`)
- Padrão **DTO** para desacoplar a API do modelo de domínio (DTO resumido vs. completo)
- **SQL Projections** para consultas otimizadas de leitura
- Consultas nativas com `@Query(nativeQuery = true)` e `@Modifying`
- Algoritmo de **reordenação de lista** com atualização mínima no banco
- **Perfis de ambiente** (test/dev/prod) e configuração via variáveis de ambiente
- Controle transacional com `@Transactional(readOnly = true)`
- Configuração de **CORS** para integração com front-ends

---

Feito por [Luan Costa](https://github.com/luanmvcosta0) 👋
