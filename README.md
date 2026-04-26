# Sistema de Gestão de Oficina Mecânica - Solivos

Este projeto é uma solução full-stack moderna para gestão de ordens de serviço, clientes, veículos e faturamento de oficinas mecânicas. A arquitetura segue o modelo Monorepo, separando as responsabilidades entre um backend robusto em Spring Boot e um frontend reativo em Vue.js.

## 🚀 Arquitetura e Tecnologias

### Backend (Spring Boot 3.4.5)
*   **Java 21**: Utilização de Virtual Threads para alta performance e escalabilidade.
*   **Spring Security + JWT**: Autenticação stateless com tokens seguros e controle de acesso por Roles (ADMIN, USER).
*   **Spring Data JPA**: Abstração de persistência com PostgreSQL.
*   **PostgreSQL (JSONB)**: Armazenamento de dados dinâmicos (como checklists e fotos) usando o tipo nativo JSONB para consultas eficientes.
*   **Lombok**: Redução de código boilerplate.
*   **Bean Validation**: Validação rigorosa de dados na entrada (DTOs).

### Frontend (Vue.js 3 + TypeScript)
*   **Vite**: Ferramenta de build ultra-rápida.
*   **Composition API**: Padrão moderno de organização de lógica no Vue.
*   **Axios**: Cliente HTTP configurado com interceptors para envio automático do Token JWT.
*   **TypeScript**: Segurança de tipos em todo o ciclo de vida do frontend.

---

## 🛠️ Configuração e Execução

### Pré-requisitos
*   JDK 21
*   Node.js (v18+) e NPM
*   PostgreSQL 14+

### Executando o Backend
1.  Configure as credenciais do banco em `src/main/resources/application.properties`.
2.  O banco de dados deve se chamar `db_oficina` (ou o nome configurado na URL JDBC).
3.  Execute o script inicial localizado em `src/main/resources/scriptSQLInicial.sql` para criar as tabelas e o usuário administrador padrão.
4.  Rode a aplicação:
    ```bash
    mvn spring-boot:run
    ```
    *A API estará disponível em `http://localhost:8080/api/v1`*

### Executando o Frontend
1.  Navegue até a pasta do frontend:
    ```bash
    cd frontend
    ```
2.  Instale as dependências:
    ```bash
    npm install
    ```
3.  Inicie o servidor de desenvolvimento:
    ```bash
    npm run dev
    ```
    *O frontend estará disponível em `http://localhost:5173`*

---

## 🔐 Segurança

*   **Usuário Padrão**: `admin@oficina.com` / `admin123`
*   **CORS**: Configurado para permitir acessos controlados do frontend local.
*   **Token JWT**: Gerado no login e deve ser enviado no header `Authorization: Bearer <token>`.

---

## 📊 Endpoints Principais

*   `POST /api/v1/auth/login`: Realiza autenticação e retorna Token + Dados do Usuário.
*   `GET /api/v1/dashboard/stats`: Retorna métricas consolidadas (Clientes, OS ativas, Faturamento).
*   `GET /api/v1/ordens-servico`: Listagem e filtros de OS.

## 📐 Padrões de Projeto (Design Patterns)
*   **DTO (Data Transfer Object)**: Isolamento das entidades de banco de dados da camada de visão.
*   **Service Layer**: Lógica de negócio centralizada e desacoplada dos controllers.
*   **Global Exception Handler**: Padronização de erros da API para o frontend.
*   **Repository Pattern**: Abstração total do acesso aos dados.

---
*Documentação gerada pelo Arquiteto de Software Gemini CLI.*
