-- =============================================================
-- V3 – Completa dados de demonstração – Agosto e Setembro/2026
-- Adiciona categorias faltantes para paridade com fev-jul/2026
--
-- Agosto   – despesas totais: R$ 39.015 | receitas: R$ 45.200 | lucro: R$ 6.185
-- Setembro – despesas totais: R$ 37.055 | receitas: R$ 51.100 | lucro: R$ 14.045
-- =============================================================


-- -------------------------------------------------------------
-- 1. DESPESAS DE AGOSTO/2026 (categorias ausentes no V2)
-- -------------------------------------------------------------

INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Conta de energia elétrica - agosto/2026',
     (SELECT id FROM categoria WHERE nome = 'Energia'), NULL,
     1550.00, '2026-08-15', '2026-08-15 10:00:00');

INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Compra de carne bovina e frango - Frigorífico Sul - agosto/2026',
     (SELECT id FROM categoria WHERE nome = 'Mercadoria'), NULL,
     11800.00, '2026-08-11', '2026-08-11 08:00:00');

INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Compra de bandejas e sacos a vácuo - agosto/2026',
     (SELECT id FROM categoria WHERE nome = 'Embalagens'), NULL,
     490.00, '2026-08-19', '2026-08-19 13:00:00');

INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Abastecimento do veículo de entrega - agosto/2026',
     (SELECT id FROM categoria WHERE nome = 'Combustível'),
     (SELECT id FROM funcionario WHERE nome = 'Pedro Oliveira'),
     275.00, '2026-08-06', '2026-08-06 07:30:00');

INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Pagamento SIMPLES Nacional e taxas municipais - agosto/2026',
     (SELECT id FROM categoria WHERE nome = 'Impostos'), NULL,
     2350.00, '2026-08-29', '2026-08-29 16:00:00');

INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Anúncio nas redes sociais e panfletos promocionais - agosto/2026',
     (SELECT id FROM categoria WHERE nome = 'Marketing'),
     (SELECT id FROM funcionario WHERE nome = 'João Silva'),
     750.00, '2026-08-14', '2026-08-14 09:00:00');


-- -------------------------------------------------------------
-- 2. DESPESAS DE SETEMBRO/2026 (categorias ausentes no V2)
-- -------------------------------------------------------------

INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Folha de pagamento - setembro/2026',
     (SELECT id FROM categoria WHERE nome = 'Salários'),
     (SELECT id FROM funcionario WHERE nome = 'João Silva'),
     18300.00, '2026-09-30', '2026-09-30 17:00:00');

INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Conta de energia elétrica - setembro/2026',
     (SELECT id FROM categoria WHERE nome = 'Energia'), NULL,
     1420.00, '2026-09-15', '2026-09-15 10:00:00');

INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Compra de carne bovina e embutidos - Frigorífico Norte - setembro/2026',
     (SELECT id FROM categoria WHERE nome = 'Mercadoria'), NULL,
     10500.00, '2026-09-08', '2026-09-08 08:00:00');

INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Compra de embalagens a vácuo e bandejas - setembro/2026',
     (SELECT id FROM categoria WHERE nome = 'Embalagens'), NULL,
     460.00, '2026-09-10', '2026-09-10 13:00:00');

INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Abastecimento do veículo de entrega - setembro/2026',
     (SELECT id FROM categoria WHERE nome = 'Combustível'),
     (SELECT id FROM funcionario WHERE nome = 'Pedro Oliveira'),
     295.00, '2026-09-05', '2026-09-05 07:30:00');

INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Pagamento SIMPLES Nacional e taxas municipais - setembro/2026',
     (SELECT id FROM categoria WHERE nome = 'Impostos'), NULL,
     2200.00, '2026-09-29', '2026-09-29 16:00:00');

INSERT INTO expenses (description, categoria_id, funcionario_id, amount, expense_date, created_at) VALUES
    ('Campanha início de primavera - redes sociais - setembro/2026',
     (SELECT id FROM categoria WHERE nome = 'Marketing'),
     (SELECT id FROM funcionario WHERE nome = 'João Silva'),
     680.00, '2026-09-09', '2026-09-09 09:00:00');


-- -------------------------------------------------------------
-- 3. RECEITAS DE AGOSTO/2026
-- V2 já tem R$ 38.700; V3 adiciona encomenda para cobrir
-- as novas despesas (total despesas agosto: R$ 39.015)
-- -------------------------------------------------------------

INSERT INTO receita (descricao, valor, data, categoria_id) VALUES
    ('Encomenda churrasco para evento corporativo - agosto/2026',
     6500.00, '2026-08-23',
     (SELECT id FROM categoria WHERE nome = 'Encomendas especiais'));

-- Agosto: receitas R$ 45.200 | despesas R$ 39.015 | lucro R$ 6.185


-- -------------------------------------------------------------
-- 4. RECEITAS DE SETEMBRO/2026
-- V2 registrou R$ 5.200 (primeiros 2 dias); V3 completa o mês
-- -------------------------------------------------------------

INSERT INTO receita (descricao, valor, data, categoria_id) VALUES
    ('Vendas no balcão - de 03 a 15 de setembro/2026',
     13800.00, '2026-09-15',
     (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas no balcão - 2ª quinzena de setembro/2026',
     14200.00, '2026-09-30',
     (SELECT id FROM categoria WHERE nome = 'Vendas')),
    ('Vendas para restaurante e hotel - setembro/2026',
      9800.00, '2026-09-30',
     (SELECT id FROM categoria WHERE nome = 'Vendas no atacado')),
    ('Entregas a domicílio - setembro/2026',
      3600.00, '2026-09-30',
     (SELECT id FROM categoria WHERE nome = 'Delivery')),
    ('Encomenda confraternização empresarial - setembro/2026',
      4500.00, '2026-09-20',
     (SELECT id FROM categoria WHERE nome = 'Encomendas especiais'));

-- Setembro: receitas R$ 51.100 | despesas R$ 37.055 | lucro R$ 14.045
