# 🥩 MeatShop Manager — Backend

Sistema backend para gerenciamento de despesas de um açougue, desenvolvido com foco em **arquitetura limpa**, **boas práticas** e **API REST profissional**, pronto para integração com qualquer frontend.

---

## Arquitetura do Sistema

![Arquitetura do MeatShop Manager](src/main/img/projetoMeat.png)
---

## Tecnologias Utilizadas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

---

## Diagrama Geral 

```mermaid
flowchart LR
    Client[Client / Frontend] --> Controller
    Controller --> Service
    Service --> Repository
    Repository --> Database[(PostgreSQL)]

    Service --> Mapper
    Mapper --> DTO
    DTO --> Controller

    Service --> ExceptionHandler
```

---

## 📊 Funcionalidades Implementadas

### CRUD de Despesas

* Criar despesa
* Listar despesas
* Buscar despesa por ID
* Atualizar despesa
* Remover despesa

### Dashboard Financeiro

* 💰 **Total gasto**
* 📦 **Total gasto por categoria**
* 📆 **Total gasto por mês (ano/mês)**

Os dados do dashboard são calculados diretamente no banco de dados utilizando **JPQL com `SUM` e `GROUP BY`**, garantindo melhor performance e menor acoplamento com o frontend.

---

