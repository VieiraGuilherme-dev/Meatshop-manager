

-- =============================================================
-- V1 – Schema inicial do MeatShop Manager
-- Gerado a partir das entidades JPA reais do projeto.
-- Ordem: tabelas sem FK primeiro, depois as dependentes.
-- =============================================================

-- 1. categoria (sem dependências)
CREATE TABLE categoria (
    id        BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome      VARCHAR(255) NOT NULL UNIQUE,
    tipo      VARCHAR(255) NOT NULL CHECK (tipo IN ('DESPESA', 'RECEITA')),
    descricao VARCHAR(255)
);

-- 2. funcionario (sem dependências)
CREATE TABLE funcionario (
    id              BIGINT       GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome            VARCHAR(255) NOT NULL,
    cargo           VARCHAR(255) NOT NULL,
    salario         NUMERIC(19, 2) NOT NULL,
    data_admissao   DATE         NOT NULL,
    data_demissao   DATE,
    ativo           BOOLEAN      NOT NULL
);

-- 3. usuario (sem dependências)
CREATE TABLE usuario (
    id    BIGINT       GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role  VARCHAR(255) NOT NULL CHECK (role IN ('ADMIN', 'FUNCIONARIO'))
);

-- 4. expenses (FK → categoria obrigatória, FK → funcionario opcional)
CREATE TABLE expenses (
    id             BIGINT         GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    description    VARCHAR(255)   NOT NULL,
    categoria_id   BIGINT         NOT NULL REFERENCES categoria(id),
    funcionario_id BIGINT         REFERENCES funcionario(id),
    amount         NUMERIC(10, 2) NOT NULL,
    expense_date   DATE           NOT NULL,
    created_at     TIMESTAMP
);

-- 5. receita (FK → categoria obrigatória)
CREATE TABLE receita (
    id           BIGINT         GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    descricao    VARCHAR(255)   NOT NULL,
    valor        NUMERIC(19, 2) NOT NULL,
    data         DATE           NOT NULL,
    categoria_id BIGINT         NOT NULL REFERENCES categoria(id)
);

-- =============================================================
-- Seed: categorias padrão (migrado do antigo data.sql)
-- =============================================================
INSERT INTO categoria (nome, tipo, descricao) VALUES
    ('Água',       'DESPESA', 'Conta de água mensal'),
    ('Energia',    'DESPESA', 'Conta de energia elétrica mensal'),
    ('Mercadoria', 'DESPESA', 'Compra de mercadoria para revenda'),
    ('Limpeza',    'DESPESA', 'Produtos e materiais de limpeza'),
    ('Aluguel',    'DESPESA', 'Aluguel do estabelecimento'),
    ('Salários',   'DESPESA', 'Folha de pagamento de funcionários'),
    ('Vendas',     'RECEITA', 'Receita proveniente de vendas');