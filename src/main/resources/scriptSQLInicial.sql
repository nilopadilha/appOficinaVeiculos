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
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. TABELA: CLIENTES
CREATE TABLE clientes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(100) NOT NULL,
    documento VARCHAR(14) NOT NULL UNIQUE,
    telefone VARCHAR(15),
    is_vip BOOLEAN DEFAULT FALSE,
    endereco JSONB,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. TABELA: VEICULOS
CREATE TABLE veiculos (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    cliente_id UUID NOT NULL REFERENCES clientes(id) ON DELETE CASCADE,
    modelo VARCHAR(100) NOT NULL,
    marca VARCHAR(50),
    ano VARCHAR(4) NOT NULL,
    placa VARCHAR(10) NOT NULL UNIQUE,
    cor_codigo VARCHAR(50),
    vin_chassi VARCHAR(17) UNIQUE
);

-- 6. TABELA: PECAS (Estoque)
CREATE TABLE pecas (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(100) NOT NULL,
    sku VARCHAR(50) UNIQUE,
    preco_unitario NUMERIC(12,2) NOT NULL,
    quantidade_estoque INTEGER NOT NULL DEFAULT 0,
    estoque_minimo INTEGER DEFAULT 5
);

-- 7. TABELA: ORDENS_SERVICO
CREATE TABLE ordens_servico (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    veiculo_id UUID NOT NULL REFERENCES veiculos(id) ON DELETE CASCADE,
    responsavel_id UUID REFERENCES usuarios(id),
    numero_os SERIAL UNIQUE,
    descricao_problema TEXT NOT NULL,
    laudo_tecnico TEXT,
    valor_mao_obra NUMERIC(12,2) NOT NULL DEFAULT 0,
    status status_os DEFAULT 'ORCAMENTO',
    data_abertura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_finalizacao TIMESTAMP,
    checklist_entrada JSONB
);

-- 8. TABELA: ORDEM_PECAS (Relacionamento N:N)
CREATE TABLE ordem_pecas (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    os_id UUID NOT NULL REFERENCES ordens_servico(id) ON DELETE CASCADE,
    peca_id UUID NOT NULL REFERENCES pecas(id),
    quantidade INTEGER NOT NULL DEFAULT 1,
    preco_aplicado NUMERIC(12,2) NOT NULL
);

-- 9. TABELA: PAGAMENTOS
CREATE TABLE pagamentos (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    os_id UUID NOT NULL REFERENCES ordens_servico(id) ON DELETE CASCADE,
    valor_total NUMERIC(12,2) NOT NULL,
    metodo forma_pagamento NOT NULL,
    status status_pagamento DEFAULT 'PENDENTE',
    data_pagamento TIMESTAMP
);

-- Inserção de Admin Padrão
INSERT INTO usuarios (nome, email, senha, role)
VALUES ('Administrador', 'admin@oficina.com', 'admin123', 'ADMIN');