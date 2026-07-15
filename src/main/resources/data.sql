INSERT INTO categoria (nome, tipo, descricao) VALUES
('Água', 'DESPESA', 'Conta de água mensal'),
('Energia', 'DESPESA', 'Conta de energia elétrica mensal'),
('Mercadoria', 'DESPESA', 'Compra de mercadoria para revenda'),
('Limpeza', 'DESPESA', 'Produtos e materiais de limpeza'),
('Aluguel', 'DESPESA', 'Aluguel do estabelecimento'),
('Salários', 'DESPESA', 'Folha de pagamento de funcionários'),
('Vendas', 'RECEITA', 'Receita proveniente de vendas')
ON CONFLICT (nome) DO NOTHING;