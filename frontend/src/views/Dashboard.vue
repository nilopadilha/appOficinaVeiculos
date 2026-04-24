<template>
  <div class="dashboard-container">
    <div class="header-row">
      <div>
        <h1>Dashboard</h1>
        <p class="subtitle">Bem-vindo de volta! Aqui está o resumo da sua oficina.</p>
      </div>
      <button class="primary">+ Nova Ordem de Serviço</button>
    </div>

    <div class="stats-row">
      <div class="stat-card">
        <div class="icon-box blue">💰</div>
        <div class="data">
          <p>Faturamento Mensal</p>
          <h3>R$ {{ stats.faturamentoMensal.toLocaleString('pt-BR', { minimumFractionDigits: 2 }) }}</h3>
        </div>
      </div>
      <div class="stat-card">
        <div class="icon-box green">🛠️</div>
        <div class="data">
          <p>OS em Aberto</p>
          <h3>{{ stats.totalOrdensAtivas }}</h3>
        </div>
      </div>
      <div class="stat-card">
        <div class="icon-box orange">👥</div>
        <div class="data">
          <p>Novos Clientes</p>
          <h3>{{ stats.totalClientes }}</h3>
        </div>
      </div>
    </div>

    <div class="content-row">
      <div class="card main-table-card">
        <div class="card-header">
          <h3>Serviços Recentes</h3>
          <router-link to="/ordens-servico" class="link">Ver todos</router-link>
        </div>
        <table class="data-table">
          <thead>
            <tr>
              <th>Cliente</th>
              <th>Veículo</th>
              <th>Status</th>
              <th>Valor</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="os in recentOS" :key="os.id">
              <td><strong>{{ os.cliente }}</strong></td>
              <td>{{ os.veiculo }}</td>
              <td><span :class="['badge', getStatusClass(os.status)]">{{ os.status }}</span></td>
              <td>R$ 1.250,00</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="card side-card">
        <h3>Metas do Mês</h3>
        <div class="goal-item">
          <div class="goal-info">
            <span>Serviços Concluídos</span>
            <span>85%</span>
          </div>
          <div class="progress"><div class="bar" style="width: 85%"></div></div>
        </div>
        <div class="goal-item">
          <div class="goal-info">
            <span>Faturamento Esperado</span>
            <span>60%</span>
          </div>
          <div class="progress"><div class="bar" style="width: 60%"></div></div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.header-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 32px; }
.subtitle { color: var(--text-muted); font-size: 0.9rem; margin-top: 4px; }

.stats-row { display: grid; grid-template-columns: repeat(3, 1fr); gap: 24px; margin-bottom: 32px; }

.stat-card {
  background: white;
  padding: 24px;
  border-radius: var(--radius);
  display: flex;
  align-items: center;
  gap: 20px;
  box-shadow: var(--shadow-sm);
}

.icon-box {
  width: 56px;
  height: 56px;
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
}
.icon-box.blue { background: #e0e9ff; color: #0061f2; }
.icon-box.green { background: #c6f6d5; color: #22543d; }
.icon-box.orange { background: #feebc8; color: #744210; }

.data p { color: var(--text-muted); font-size: 0.85rem; font-weight: 600; margin-bottom: 4px; }
.data h3 { font-size: 1.5rem; }

.content-row { display: grid; grid-template-columns: 2fr 1fr; gap: 24px; }

.card-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.link { color: var(--primary); font-size: 0.85rem; font-weight: 600; text-decoration: none; }

.goal-item { margin-top: 20px; }
.goal-info { display: flex; justify-content: space-between; font-size: 0.85rem; margin-bottom: 8px; font-weight: 600; }
.progress { height: 8px; background: #edf2f7; border-radius: 10px; overflow: hidden; }
.progress .bar { height: 100%; background: var(--primary); border-radius: 10px; }

@media (max-width: 1200px) {
  .stats-row, .content-row { grid-template-columns: 1fr; }
}
</style>

<style scoped>
.welcome-section { margin-bottom: 32px; }
.welcome-section h1 { font-size: 2rem; margin-bottom: 4px; }
.welcome-section h1 span { color: var(--primary); font-weight: 800; }
.subtitle { color: var(--text-muted); font-size: 0.95rem; }

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 24px;
  margin-bottom: 32px;
}

.stat-card.primary-border { border-left: 4px solid var(--primary); }
.text-glow { text-shadow: 0 0 20px rgba(0, 242, 255, 0.3); color: var(--primary); }

.label { color: var(--text-muted); font-size: 0.85rem; text-transform: uppercase; letter-spacing: 1px; }
.value { font-size: 2.2rem; margin: 8px 0; }
.trend { font-size: 0.8rem; font-weight: 500; }
.trend.positive { color: var(--success); }

.progress-bar { height: 6px; background: rgba(255,255,255,0.05); border-radius: 10px; margin-top: 12px; }
.progress-bar .fill { height: 100%; background: var(--primary); border-radius: 10px; box-shadow: 0 0 10px var(--primary); }

.main-grid { display: grid; grid-template-columns: 1.5fr 1fr; gap: 24px; }

.visual-chart {
  height: 200px;
  display: flex;
  align-items: flex-end;
  justify-content: space-around;
  padding: 20px 0;
}

.bar-wrapper { width: 30px; height: 100%; display: flex; align-items: flex-end; }
.bar { 
  width: 100%; 
  background: linear-gradient(to top, var(--primary), transparent); 
  border-radius: 4px 4px 0 0;
  transition: height 1s ease;
}

.activity-list { list-style: none; margin-top: 16px; }
.activity-item { 
  display: flex; 
  align-items: center; 
  gap: 16px; 
  padding: 12px 0; 
  border-bottom: 1px solid var(--border);
}
.status-dot { width: 10px; height: 10px; border-radius: 50%; }
.status-dot.badge-success { background: var(--success); box-shadow: 0 0 8px var(--success); }
.status-dot.badge-warning { background: var(--warning); box-shadow: 0 0 8px var(--warning); }
.status-dot.badge-danger { background: var(--accent); box-shadow: 0 0 8px var(--accent); }

.main-text { font-size: 0.95rem; font-weight: 500; }
.sub-text { font-size: 0.8rem; color: var(--text-muted); }

@media (max-width: 1024px) {
  .main-grid { grid-template-columns: 1fr; }
}
</style>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import api from '../services/api';

const stats = ref({
  totalClientes: 0,
  totalOrdensAtivas: 0,
  faturamentoMensal: 0
});

const recentOS = ref([
  { id: 1024, cliente: 'João Silva', veiculo: 'Toyota Corolla', status: 'Em Execução' },
  { id: 1023, cliente: 'Maria Oliveira', veiculo: 'Honda Civic', status: 'Aguardando Peças' },
  { id: 1022, cliente: 'Carlos Souza', veiculo: 'VW Golf', status: 'Concluído' },
]);

onMounted(async () => {
  try {
    const response = await api.get('/dashboard/stats');
    stats.value = response.data;
  } catch (error) {
    console.error("Erro ao carregar stats", error);
    // Dados mockados caso a API falhe
    stats.value = { totalClientes: 150, totalOrdensAtivas: 8, faturamentoMensal: 25400.50 };
  }
});

const getStatusClass = (status: string) => {
  if (status === 'Concluído') return 'badge-success';
  if (status === 'Em Execução') return 'badge-warning';
  return 'badge-danger';
};
</script>

<style scoped>
.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.stat-info .label {
  color: var(--text-muted);
  font-size: 0.85rem;
  margin-bottom: 5px;
}

.stat-info h3 {
  margin-bottom: 0;
  font-size: 1.8rem;
}

.price {
  color: var(--success) !important;
}

.stat-icon {
  font-size: 2rem;
  opacity: 0.8;
  background: rgba(255, 255, 255, 0.05);
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 12px;
}

.dashboard-grid {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 20px;
}

.chart-placeholder {
  height: 200px;
  display: flex;
  align-items: flex-end;
  gap: 15px;
  padding: 20px;
}

.bar {
  flex: 1;
  background: linear-gradient(to top, var(--primary), transparent);
  border-radius: 4px 4px 0 0;
}

@media (max-width: 1024px) {
  .dashboard-grid {
    grid-template-columns: 1fr;
  }
}
</style>
