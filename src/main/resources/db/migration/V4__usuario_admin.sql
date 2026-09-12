-- Insere o usuário administrador padrão somente se ele ainda não existir.
-- Idempotente: seguro para rodar contra banco que já possua este registro.
INSERT INTO usuario (email, senha, role)
SELECT 'admin@meatshop.com',
       '$2a$10$622liGDc5FiGrG4hTTOTC.6JF5UVIeXjxLz.gLovm8iCEoYahL35.',
       'ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM usuario WHERE email = 'admin@meatshop.com'
);
