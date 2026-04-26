# Documentação da API - appOficinaVeiculos

Esta API gerencia o fluxo de trabalho de uma oficina mecânica e de pintura, incluindo clientes, veículos, ordens de serviço (OS), peças e pagamentos.

## 🛡️ Autenticação
A API utiliza **JWT (JSON Web Token)**.
- **Endpoint:** `POST /api/v1/auth/login`
- **Payload:** `{ "email": "admin@oficina.com", "senha": "admin" }`
- **Retorno:** Token JWT que deve ser enviado no Header `Authorization: Bearer <token>`.

---

## 👥 Clientes
Endpoints para gestão de clientes.
- `GET /api/v1/clientes`: Lista todos os clientes.
- `POST /api/v1/clientes`: Cadastra novo cliente.
- `PUT /api/v1/clientes/{id}`: Atualiza dados do cliente.
- `DELETE /api/v1/clientes/{id}`: Remove um cliente.

---

## 🚗 Veículos
Gestão de veículos vinculados a clientes.
- `GET /api/v1/veiculos`: Lista todos os veículos.
- `POST /api/v1/veiculos`: Cadastra veículo (Requer `clienteId`).
- `PUT /api/v1/veiculos/{id}`: Atualiza dados (Valida placa única).

---

## 🛠️ Ordens de Serviço (OS)
O coração do sistema, suportando Mecânica e Pintura.
- **Tipos de Serviço:** `MECANICA`, `PINTURA`.
- **Status:** `ORCAMENTO`, `APROVADO`, `MECANICA`, `PINTURA`, `PRONTO`, `FINALIZADO`, `CANCELADO`.

### Endpoints:
- `POST /api/v1/ordens-servico`: Abre uma nova OS.
  - Se `tipoServico: "PINTURA"`, pode enviar `fotosPintura` (JSON String).
- `GET /api/v1/ordens-servico/{id}/detalhes`: Retorna a OS completa com lista de peças, subtotal de peças, mão de obra e valor total geral.
- `PATCH /api/v1/ordens-servico/{id}/status?novoStatus=PRONTO`: Atualiza o status da OS.
- `GET /api/v1/ordens-servico/numero/{numeroOs}`: Busca rápida pelo número sequencial.

---

## 📦 Peças e Estoque
- `POST /api/v1/ordens-servico/{osId}/pecas`: Adiciona peça à OS (Valida estoque e congela o preço atual da peça na OS).

---

## 💰 Pagamentos
- `POST /api/v1/pagamentos`: Gera fatura baseada nos itens da OS.
- `PATCH /api/v1/pagamentos/{id}/confirmar`: Baixa o pagamento e finaliza financeiramente.

---

## 🛠️ Tecnologias Utilizadas
- **Backend:** Spring Boot 3, Java 17, Spring Security, JWT.
- **Banco de Dados:** PostgreSQL (com tipos JSONB para checklists e fotos).
- **Injeção de Dependência:** Construtores gerados via Lombok (`@RequiredArgsConstructor`).
- **Validação:** Bean Validation (`@Valid`, `@NotBlank`, etc).
