-- =============================================================
-- V2 – Dados de demonstração – Açougue de Médio Porte
-- Período coberto: fevereiro/2026 a setembro/2026 (hoje: 02/09/2026)
-- Resumo financeiro do período:
--   Despesas totais : R$ 243.715,00
--   Receitas totais : R$ 320.300,00
--   Lucro           : R$  76.585,00
-- =============================================================


-- -------------------------------------------------------------
-- 1. CATEGORIAS ADICIONAIS
--    (As 7 categorias da V1 já existem; inserindo apenas as novas)
-- -------------------------------------------------------------
INSERT INTO categoria (nome, tipo, descricao) VALUES
    ('Manutenção',           'DESPESA', 'Manutenção e reparos de equipamentos e instalações'),
    ('Embalagens',           'DESPESA', 'Bandejas, sacos, papel alumínio e embalagens em geral'),
    ('Combustível',          'DESPESA', 'Combustível para o veículo de entrega'),
    ('Impostos',             'DESPESA', 'Impostos, taxas municipais e encargos trabalhistas'),
    ('Marketing',            'DESPESA', 'Publicidade, panfletos e divulgação nas redes sociais'),
    ('Vendas no atacado',    'RECEITA', 'Receita de vendas para restaurantes, hotéis e revendedores'),
    ('Delivery',             'RECEITA', 'Receita de entregas a domicílio e por aplicativo'),
    ('Encomendas especiais', 'RECEITA', 'Receita de encomendas para eventos, festas e churrascos');


-- -------------------------------------------------------------
-- 2. FUNCIONÁRIOS  (7 ativos · 3 demitidos)
-- -------------------------------------------------------------
INSERT INTO funcionario (nome, cargo, salario, data_admissao, data_demissao, ativo) VALUES
    -- Ativos
    ('João Silva',       'Gerente',             4200.00, '2022-03-15', NULL,         true),
    ('Maria Santos',     'Açougueiro',          3200.00, '2022-07-01', NULL,         true),
    ('Pedro Oliveira',   'Açougueiro',          3000.00, '2023-02-10', NULL,         true),
    ('Ana Costa',        'Balconista',          2100.00, '2023-05-20', NULL,         true),
    ('Carlos Ferreira',  'Caixa',               1900.00, '2024-01-08', NULL,         true),
    ('Fernanda Lima',    'Estoquista',          2300.00, '2024-06-15', NULL,         true),
    ('Roberto Alves',    'Auxiliar de Limpeza', 1600.00, '2025-03-01', NULL,         true),
    -- Demitidos
    ('Luciana Mendes',   'Balconista',          2100.00, '2022-11-01', '2024-08-31', false),
    ('Marcos Rodrigues', 'Açougueiro',          2800.00, '2023-03-15', '2025-01-31', false),
    ('Patricia Gomes',   'Caixa',               1900.00, '2024-02-01', '2024-12-31', false);


-- -------------------------------------------------------------
-- 3. DESPESAS  (41 registros – "cerca de 40")
-- -------------------------------------------------------------

-- ── ALUGUEL: 8 meses (fev–set/2026) ──────────────────────────
INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Aluguel do estabelecimento - fevereiro/2026',
     (SELECT id FROM categoria WHERE nome = 'Aluguel'), NULL, 3100.00, '2026-02-05', '2026-02-05 09:00:00'),
    ('Aluguel do estabelecimento - março/2026',
     (SELECT id FROM categoria WHERE nome = 'Aluguel'), NULL, 3000.00, '2026-03-05', '2026-03-05 09:00:00'),
    ('Aluguel do estabelecimento - abril/2026',
     (SELECT id FROM categoria WHERE nome = 'Aluguel'), NULL, 3100.00, '2026-04-05', '2026-04-05 09:00:00'),
    ('Aluguel do estabelecimento - maio/2026',
     (SELECT id FROM categoria WHERE nome = 'Aluguel'), NULL, 3050.00, '2026-05-05', '2026-05-05 09:00:00'),
    ('Aluguel do estabelecimento - junho/2026',
     (SELECT id FROM categoria WHERE nome = 'Aluguel'), NULL, 3200.00, '2026-06-05', '2026-06-05 09:00:00'),
    ('Aluguel do estabelecimento - julho/2026',
     (SELECT id FROM categoria WHERE nome = 'Aluguel'), NULL, 3000.00, '2026-07-05', '2026-07-05 09:00:00'),
    ('Aluguel do estabelecimento - agosto/2026',
     (SELECT id FROM categoria WHERE nome = 'Aluguel'), NULL, 3150.00, '2026-08-05', '2026-08-05 09:00:00'),
    ('Aluguel do estabelecimento - setembro/2026',
     (SELECT id FROM categoria WHERE nome = 'Aluguel'), NULL, 3200.00, '2026-09-01', '2026-09-01 09:00:00');

-- ── ENERGIA: 6 meses (fev–jul/2026) ──────────────────────────
INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Conta de energia elétrica - fevereiro/2026',
     (SELECT id FROM categoria WHERE nome = 'Energia'), NULL, 1450.00, '2026-02-15', '2026-02-15 10:00:00'),
    ('Conta de energia elétrica - março/2026',
     (SELECT id FROM categoria WHERE nome = 'Energia'), NULL, 1620.00, '2026-03-15', '2026-03-15 10:00:00'),
    ('Conta de energia elétrica - abril/2026',
     (SELECT id FROM categoria WHERE nome = 'Energia'), NULL, 1380.00, '2026-04-15', '2026-04-15 10:00:00'),
    ('Conta de energia elétrica - maio/2026',
     (SELECT id FROM categoria WHERE nome = 'Energia'), NULL, 1710.00, '2026-05-15', '2026-05-15 10:00:00'),
    ('Conta de energia elétrica - junho/2026',
     (SELECT id FROM categoria WHERE nome = 'Energia'), NULL, 1580.00, '2026-06-15', '2026-06-15 10:00:00'),
    ('Conta de energia elétrica - julho/2026',
     (SELECT id FROM categoria WHERE nome = 'Energia'), NULL, 1680.00, '2026-07-15', '2026-07-15 10:00:00');

-- ── ÁGUA: 5 meses (fev–jun/2026) ─────────────────────────────
INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Conta de água - fevereiro/2026',
     (SELECT id FROM categoria WHERE nome = 'Água'), NULL, 240.00, '2026-02-20', '2026-02-20 10:00:00'),
    ('Conta de água - março/2026',
     (SELECT id FROM categoria WHERE nome = 'Água'), NULL, 280.00, '2026-03-20', '2026-03-20 10:00:00'),
    ('Conta de água - abril/2026',
     (SELECT id FROM categoria WHERE nome = 'Água'), NULL, 220.00, '2026-04-20', '2026-04-20 10:00:00'),
    ('Conta de água - maio/2026',
     (SELECT id FROM categoria WHERE nome = 'Água'), NULL, 310.00, '2026-05-20', '2026-05-20 10:00:00'),
    ('Conta de água - junho/2026',
     (SELECT id FROM categoria WHERE nome = 'Água'), NULL, 340.00, '2026-06-20', '2026-06-20 10:00:00');

-- ── MERCADORIA: 6 compras (fev–jul/2026) ─────────────────────
INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Compra de carne bovina - Frigorífico Sul - fevereiro/2026',
     (SELECT id FROM categoria WHERE nome = 'Mercadoria'), NULL, 12500.00, '2026-02-10', '2026-02-10 08:00:00'),
    ('Compra de carne bovina e suína - Frigorífico Norte - março/2026',
     (SELECT id FROM categoria WHERE nome = 'Mercadoria'), NULL,  9800.00, '2026-03-18', '2026-03-18 08:00:00'),
    ('Compra de carne bovina e embutidos - Frigorífico Sul - abril/2026',
     (SELECT id FROM categoria WHERE nome = 'Mercadoria'), NULL, 11200.00, '2026-04-22', '2026-04-22 08:00:00'),
    ('Compra de carne bovina e frango - Frigorífico Cerrado - maio/2026',
     (SELECT id FROM categoria WHERE nome = 'Mercadoria'), NULL, 13400.00, '2026-05-12', '2026-05-12 08:00:00'),
    ('Compra de carne bovina e suína - Frigorífico Sul - junho/2026',
     (SELECT id FROM categoria WHERE nome = 'Mercadoria'), NULL, 10600.00, '2026-06-09', '2026-06-09 08:00:00'),
    ('Compra de carne bovina, suína e embutidos - Frigorífico Norte - julho/2026',
     (SELECT id FROM categoria WHERE nome = 'Mercadoria'), NULL, 14800.00, '2026-07-14', '2026-07-14 08:00:00');

-- ── SALÁRIOS: 7 meses (fev–ago/2026) ─────────────────────────
-- Folha: João 4.200 + Maria 3.200 + Pedro 3.000 + Ana 2.100
--      + Carlos 1.900 + Fernanda 2.300 + Roberto 1.600 = R$ 18.300
-- Vinculado ao gerente responsável pelo pagamento da folha.
INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Folha de pagamento - fevereiro/2026',
     (SELECT id FROM categoria WHERE nome = 'Salários'),
     (SELECT id FROM funcionario WHERE nome = 'João Silva'),
     18300.00, '2026-02-28', '2026-02-28 17:00:00'),
    ('Folha de pagamento - março/2026',
     (SELECT id FROM categoria WHERE nome = 'Salários'),
     (SELECT id FROM funcionario WHERE nome = 'João Silva'),
     18300.00, '2026-03-31', '2026-03-31 17:00:00'),
    ('Folha de pagamento - abril/2026',
     (SELECT id FROM categoria WHERE nome = 'Salários'),
     (SELECT id FROM funcionario WHERE nome = 'João Silva'),
     18300.00, '2026-04-30', '2026-04-30 17:00:00'),
    ('Folha de pagamento - maio/2026',
     (SELECT id FROM categoria WHERE nome = 'Salários'),
     (SELECT id FROM funcionario WHERE nome = 'João Silva'),
     18300.00, '2026-05-30', '2026-05-30 17:00:00'),
    ('Folha de pagamento - junho/2026',
     (SELECT id FROM categoria WHERE nome = 'Salários'),
     (SELECT id FROM funcionario WHERE nome = 'João Silva'),
     18300.00, '2026-06-30', '2026-06-30 17:00:00'),
    ('Folha de pagamento - julho/2026',
     (SELECT id FROM categoria WHERE nome = 'Salários'),
     (SELECT id FROM funcionario WHERE nome = 'João Silva'),
     18300.00, '2026-07-31', '2026-07-31 17:00:00'),
    ('Folha de pagamento - agosto/2026',
     (SELECT id FROM categoria WHERE nome = 'Salários'),
     (SELECT id FROM funcionario WHERE nome = 'João Silva'),
     18300.00, '2026-08-30', '2026-08-30 17:00:00');

-- ── LIMPEZA: 3 compras ────────────────────────────────────────
INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Compra de material de limpeza e desinfetante - março/2026',
     (SELECT id FROM categoria WHERE nome = 'Limpeza'),
     (SELECT id FROM funcionario WHERE nome = 'Roberto Alves'),
     320.00, '2026-03-08', '2026-03-08 14:00:00'),
    ('Compra de material de limpeza - junho/2026',
     (SELECT id FROM categoria WHERE nome = 'Limpeza'),
     (SELECT id FROM funcionario WHERE nome = 'Roberto Alves'),
     285.00, '2026-06-12', '2026-06-12 14:00:00'),
    ('Compra de material de limpeza e produtos sanitizantes - agosto/2026',
     (SELECT id FROM categoria WHERE nome = 'Limpeza'),
     (SELECT id FROM funcionario WHERE nome = 'Roberto Alves'),
     350.00, '2026-08-07', '2026-08-07 14:00:00');

-- ── MANUTENÇÃO: 2 serviços ────────────────────────────────────
INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Manutenção preventiva da câmara fria - fevereiro/2026',
     (SELECT id FROM categoria WHERE nome = 'Manutenção'),
     (SELECT id FROM funcionario WHERE nome = 'João Silva'),
     850.00, '2026-02-22', '2026-02-22 11:00:00'),
    ('Reparo da balança e afiação de equipamentos de corte - maio/2026',
     (SELECT id FROM categoria WHERE nome = 'Manutenção'),
     (SELECT id FROM funcionario WHERE nome = 'João Silva'),
     1200.00, '2026-05-27', '2026-05-27 11:00:00');

-- ── EMBALAGENS: 1 compra ─────────────────────────────────────
INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Compra de bandejas, sacos e embalagens a vácuo - abril/2026',
     (SELECT id FROM categoria WHERE nome = 'Embalagens'), NULL,
     520.00, '2026-04-18', '2026-04-18 13:00:00');

-- ── COMBUSTÍVEL: 1 abastecimento ─────────────────────────────
INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Abastecimento do veículo de entrega - maio/2026',
     (SELECT id FROM categoria WHERE nome = 'Combustível'),
     (SELECT id FROM funcionario WHERE nome = 'Pedro Oliveira'),
     280.00, '2026-05-08', '2026-05-08 07:30:00');

-- ── IMPOSTOS: 1 lançamento ────────────────────────────────────
INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Pagamento SIMPLES Nacional e taxas municipais - fevereiro/2026',
     (SELECT id FROM categoria WHERE nome = 'Impostos'), NULL,
     2400.00, '2026-02-28', '2026-02-28 16:00:00');

-- ── MARKETING: 1 ação ────────────────────────────────────────
INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Campanha Dia dos Pais - panfletos e anúncio nas redes sociais - julho/2026',
     (SELECT id FROM categoria WHERE nome = 'Marketing'),
     (SELECT id FROM funcionario WHERE nome = 'João Silva'),
     1500.00, '2026-07-20', '2026-07-20 09:00:00');


-- -------------------------------------------------------------
-- 4. RECEITAS  (32 registros – "cerca de 30")
--    Totais mensais dentro da faixa R$ 35.000 – R$ 55.000
--    Setembro: parcial (apenas 2 dias)
-- -------------------------------------------------------------

-- ── FEVEREIRO/2026 — R$ 40.600 ───────────────────────────────
INSERT INTO receita (descricao, valor, data, categoria_id) VALUES
    ('Vendas no balcão - 1ª quinzena de fevereiro/2026',
     14800.00, '2026-02-15', (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas no balcão - 2ª quinzena de fevereiro/2026',
     13500.00, '2026-02-28', (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas para restaurante Sabor da Terra - fevereiro/2026',
      9200.00, '2026-02-28', (SELECT id FROM categoria WHERE nome = 'Vendas no atacado')),
    ('Entregas a domicílio - fevereiro/2026',
      3100.00, '2026-02-28', (SELECT id FROM categoria WHERE nome = 'Delivery'));

-- ── MARÇO/2026 — R$ 51.900 ───────────────────────────────────
INSERT INTO receita (descricao, valor, data, categoria_id) VALUES
    ('Vendas no balcão - 1ª quinzena de março/2026',
     15200.00, '2026-03-15', (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas no balcão - 2ª quinzena de março/2026',
     16800.00, '2026-03-31', (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas para restaurante e buffet Gourmet - março/2026',
     10500.00, '2026-03-31', (SELECT id FROM categoria WHERE nome = 'Vendas no atacado')),
    ('Entregas a domicílio - março/2026',
      3600.00, '2026-03-31', (SELECT id FROM categoria WHERE nome = 'Delivery')),
    ('Encomenda churrasco - evento corporativo Alfa Seguros - março/2026',
      5800.00, '2026-03-22', (SELECT id FROM categoria WHERE nome = 'Encomendas especiais'));

-- ── ABRIL/2026 — R$ 38.200 ───────────────────────────────────
INSERT INTO receita (descricao, valor, data, categoria_id) VALUES
    ('Vendas no balcão - 1ª quinzena de abril/2026',
     13800.00, '2026-04-15', (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas no balcão - 2ª quinzena de abril/2026',
     12600.00, '2026-04-30', (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas para restaurante Sabor da Terra - abril/2026',
      8900.00, '2026-04-30', (SELECT id FROM categoria WHERE nome = 'Vendas no atacado')),
    ('Entregas a domicílio - abril/2026',
      2900.00, '2026-04-30', (SELECT id FROM categoria WHERE nome = 'Delivery'));

-- ── MAIO/2026 — R$ 52.400 ────────────────────────────────────
INSERT INTO receita (descricao, valor, data, categoria_id) VALUES
    ('Vendas no balcão - 1ª quinzena de maio/2026',
     16200.00, '2026-05-15', (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas no balcão - 2ª quinzena de maio/2026',
     14800.00, '2026-05-31', (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas para restaurante, hotel e buffet - maio/2026',
     11400.00, '2026-05-31', (SELECT id FROM categoria WHERE nome = 'Vendas no atacado')),
    ('Entregas a domicílio - maio/2026',
      3800.00, '2026-05-31', (SELECT id FROM categoria WHERE nome = 'Delivery')),
    ('Encomenda Dia das Mães - kit churrasco e peças nobres - maio/2026',
      6200.00, '2026-05-11', (SELECT id FROM categoria WHERE nome = 'Encomendas especiais'));

-- ── JUNHO/2026 — R$ 41.300 ───────────────────────────────────
INSERT INTO receita (descricao, valor, data, categoria_id) VALUES
    ('Vendas no balcão - 1ª quinzena de junho/2026',
     14900.00, '2026-06-15', (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas no balcão - 2ª quinzena de junho/2026',
     13400.00, '2026-06-30', (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas para hotel e restaurante Bella Italia - junho/2026',
      9600.00, '2026-06-30', (SELECT id FROM categoria WHERE nome = 'Vendas no atacado')),
    ('Entregas a domicílio - junho/2026',
      3400.00, '2026-06-30', (SELECT id FROM categoria WHERE nome = 'Delivery'));

-- ── JULHO/2026 — R$ 52.000 ───────────────────────────────────
INSERT INTO receita (descricao, valor, data, categoria_id) VALUES
    ('Vendas no balcão - 1ª quinzena de julho/2026',
     15200.00, '2026-07-15', (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas no balcão - 2ª quinzena de julho/2026',
     14800.00, '2026-07-31', (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas para restaurante e buffet - julho/2026',
     11000.00, '2026-07-31', (SELECT id FROM categoria WHERE nome = 'Vendas no atacado')),
    ('Entregas a domicílio - julho/2026',
      4000.00, '2026-07-31', (SELECT id FROM categoria WHERE nome = 'Delivery')),
    ('Encomenda Dia dos Pais - kit churrasco premium - julho/2026',
      7000.00, '2026-07-27', (SELECT id FROM categoria WHERE nome = 'Encomendas especiais'));

-- ── AGOSTO/2026 — R$ 38.700 ──────────────────────────────────
INSERT INTO receita (descricao, valor, data, categoria_id) VALUES
    ('Vendas no balcão - 1ª quinzena de agosto/2026',
     13600.00, '2026-08-15', (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas no balcão - 2ª quinzena de agosto/2026',
     12900.00, '2026-08-31', (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas para restaurante Sabor da Terra - agosto/2026',
      9000.00, '2026-08-31', (SELECT id FROM categoria WHERE nome = 'Vendas no atacado')),
    ('Entregas a domicílio - agosto/2026',
      3200.00, '2026-08-31', (SELECT id FROM categoria WHERE nome = 'Delivery'));

-- ── SETEMBRO/2026 — R$ 5.200 (parcial – 2 dias) ──────────────
INSERT INTO receita (descricao, valor, data, categoria_id) VALUES
    ('Vendas no balcão - início de setembro/2026',
      5200.00, '2026-09-02', (SELECT id FROM categoria WHERE nome = 'Vendas'));