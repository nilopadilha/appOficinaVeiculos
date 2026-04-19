-- 1. LIMPEZA TOTAL (Ordem inversa das chaves estrangeiras)
DROP TABLE IF EXISTS pagamentos;
DROP TABLE IF EXISTS ordem_pecas;
DROP TABLE IF EXISTS ordens_servico;
DROP TABLE IF EXISTS pecas;
DROP TABLE IF EXISTS veiculos;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS usuarios;

-- Remover tipos ENUM se existirem
DROP TYPE IF EXISTS status_pagamento;
DROP TYPE IF EXISTS forma_pagamento;
DROP TYPE IF EXISTS status_os;

-- Habilitar extensão para geração de UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 2. DEFINIÇÃO DE TIPOS ENUM
CREATE TYPE status_os AS ENUM ('ORCAMENTO', 'APROVADO', 'MECANICA', 'PINTURA', 'PRONTO', 'FINALIZADO', 'CANCELADO');
CREATE TYPE forma_pagamento AS ENUM ('DINHEIRO', 'DEBITO', 'CREDITO', 'PIX', 'BOLETO');
CREATE TYPE status_pagamento AS ENUM ('PENDENTE', 'PAGO', 'CANCELADO');

-- 3. TABELA: USUARIOS (Administradores/Funcionários)
CREATE TABLE usuarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ...

-- Inserção de Admin Padrão (Senha: admin123 encodada em BCrypt)
INSERT INTO usuarios (nome, email, senha, role)
VALUES ('Administrador', 'admin@oficina.com', '$2a$12$6Kx3sZ10G3qZfH4.vT.H.ePq5o1Xm1C.L6Y/HwW8Lz1z/6X4Q.YyO', 'ADMIN');