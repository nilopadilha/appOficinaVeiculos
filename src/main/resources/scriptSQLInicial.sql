-- 1. LIMPEZA TOTAL
DROP TABLE IF EXISTS pagamentos CASCADE;
DROP TABLE IF EXISTS ordem_pecas CASCADE;
DROP TABLE IF EXISTS ordens_servico CASCADE;
DROP TABLE IF EXISTS pecas CASCADE;
DROP TABLE IF EXISTS veiculos CASCADE;
DROP TABLE IF EXISTS clientes CASCADE;
DROP TABLE IF EXISTS usuarios CASCADE;

-- 2. TABELAS
CREATE TABLE usuarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE clientes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    telefone VARCHAR(20),
    cpf_cnpj VARCHAR(20) UNIQUE,
    endereco TEXT
);

CREATE TABLE veiculos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    modelo VARCHAR(50) NOT NULL,
    marca VARCHAR(50),
    ano VARCHAR(4),
    placa VARCHAR(10) NOT NULL UNIQUE,
    cor_codigo VARCHAR(20),
    vin_chassi VARCHAR(50),
    cliente_id UUID REFERENCES clientes(id)
);

CREATE TABLE pecas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL,
    codigo_referencia VARCHAR(50),
    preco_unitario DECIMAL(10,2) NOT NULL,
    quantidade_estoque INTEGER DEFAULT 0
);

CREATE SEQUENCE os_numero_seq START 1000;

CREATE TABLE ordens_servico (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    numero_os INTEGER DEFAULT nextval('os_numero_seq'),
    descricao_problema TEXT NOT NULL,
    laudo_tecnico TEXT,
    valor_mao_obra DECIMAL(10,2) DEFAULT 0,
    status VARCHAR(20) DEFAULT 'ORCAMENTO',
    tipo_servico VARCHAR(20) DEFAULT 'MECANICA',
    data_abertura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_finalizacao TIMESTAMP,
    checklist_entrada JSONB,
    fotos_pintura JSONB,
    veiculo_id UUID REFERENCES veiculos(id),
    responsavel_id UUID REFERENCES usuarios(id)
);

CREATE TABLE ordem_pecas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    os_id UUID REFERENCES ordens_servico(id),
    peca_id UUID REFERENCES pecas(id),
    quantidade INTEGER NOT NULL,
    preco_applied DECIMAL(10,2) NOT NULL
);

-- Inserção de Admin Padrão (Senha: admin123)
INSERT INTO usuarios (nome, email, senha, role)
VALUES ('Administrador', 'admin@oficina.com', '$2a$12$6Kx3sZ10G3qZfH4.vT.H.ePq5o1Xm1C.L6Y/HwW8Lz1z/6X4Q.YyO', 'ADMIN');
