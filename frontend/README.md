# Oficina Solivos - Frontend 🚗💨

Este é o frontend da aplicação **AppOficinaVeiculos**, desenvolvido com foco em alta performance, usabilidade (UX) e um design moderno e profissional (Clean & Pro).

## 🚀 Tecnologias Utilizadas

- **Vue 3 (Composition API):** Framework progressivo para construção da interface.
- **Vite:** Ferramenta de build ultra-rápida.
- **TypeScript:** Tipagem estrita para maior segurança e manutenibilidade.
- **Vue Router:** Gerenciamento de rotas e navegação SPA.
- **Axios:** Cliente HTTP para integração com o backend.
- **Pinia:** Gerenciamento de estado global.

## 🎨 Conceito Visual: Clean & Pro
A interface foi projetada utilizando uma paleta de cores primárias refinada, com foco no **Azul Royal Moderno** para transmitir confiança e tecnologia. 
- **Componentes Customizados:** Modais elegantes, badges de status vibrantes e tabelas de alta legibilidade.
- **UX Inteligente:** Filtros dinâmicos, indicadores de progresso e feedbacks visuais instantâneos.

## 🛠️ Funcionalidades Implementadas

1. **Dashboard:** Visão geral com cards de faturamento, OS ativas e metas mensais.
2. **Gestão de Clientes:** CRUD completo com suporte a clientes VIP e vinculação de dados.
3. **Gestão de Veículos:** Cadastro de frota com design de placa Mercosul e identificação por cores.
4. **Ordens de Serviço (OS):** Fluxo inteligente de abertura de chamados, filtragem por status e acompanhamento técnico.
5. **Estoque de Peças:** Visualização inicial de insumos e alertas de estoque baixo.

## ⚙️ Integração com Backend

O frontend está preparado para consumir uma API REST na rota `/api/v1`.
- **Proxy de Desenvolvimento:** Configurado no `vite.config.ts` para redirecionar chamadas ao backend local (`localhost:8080`).
- **Modo de Simulação (Mocks):** O arquivo `src/services/appService.ts` possui uma flag `USE_MOCKS` que permite testar a interface mesmo sem o backend rodando.

## 🔧 Instalação e Execução

1.  Instale as dependências:
    ```bash
    npm install
    ```
2.  Inicie o servidor de desenvolvimento:
    ```bash
    npm run dev
    ```
3.  Para build de produção:
    ```bash
    npm run build
    ```

---
Desenvolvido por Gemini CLI para Oficina Solivos.
