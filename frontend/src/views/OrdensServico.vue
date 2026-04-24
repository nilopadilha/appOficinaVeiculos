<template>
  <div class="page-container">
    <div class="header-row">
      <div>
        <h1>Ordens de Serviço</h1>
        <p class="subtitle">Acompanhe e gerencie a manutenção dos veículos.</p>
      </div>
      <button class="primary" @click="openModal">+ Abrir Nova OS</button>
    </div>

    <div class="filter-bar card">
      <div class="search-input">
        <i class="icon">🔍</i>
        <input type="text" placeholder="Buscar por cliente ou placa...">
      </div>
      <div class="filter-group">
        <select class="modern-select">
          <option value="">Todos os Status</option>
          <option>ABERTA</option>
          <option>EM_EXECUCAO</option>
          <option>CONCLUIDA</option>
        </select>
      </div>
    </div>

    <div class="card table-card">
      <div v-if="loading" class="loading-state">Atualizando ordens...</div>
      
      <table class="data-table" v-else>
        <thead>
          <tr>
            <th>ID</th>
            <th>Cliente / Veículo</th>
            <th>Data de Abertura</th>
            <th>Técnico</th>
            <th>Status</th>
            <th>Valor Total</th>
            <th class="text-right">Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="os in ordens" :key="os.id">
            <td><span class="os-number">#{{ os.id }}</span></td>
            <td>
              <div class="client-car">
                <strong>{{ os.clienteNome }}</strong>
                <span>{{ os.veiculoPlaca }}</span>
              </div>
            </td>
            <td>{{ os.dataAbertura }}</td>
            <td>{{ os.tecnicoResponsavel }}</td>
            <td><span :class="['status-badge', os.status]">{{ os.status.replace('_', ' ') }}</span></td>
            <td><strong class="price">R$ {{ os.total?.toFixed(2) || '0,00' }}</strong></td>
            <td class="text-right">
              <button class="btn-icon" title="Ver Detalhes">👁️</button>
              <button class="btn-icon" title="Editar">✏️</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Modal Nova OS -->
    <Modal :show="showModal" title="Abertura de Ordem de Serviço" @close="showModal = false">
      <form @submit.prevent="saveOS" class="modern-form">
        <div class="form-group">
          <label>Selecione o Cliente</label>
          <select v-model="form.clienteId" @change="onClienteChange" required class="modern-select">
            <option value="" disabled>Selecione um cliente</option>
            <option v-for="c in clientes" :key="c.id" :value="c.id">{{ c.nome }}</option>
          </select>
        </div>

        <div class="form-group" v-if="form.clienteId">
          <label>Veículo</label>
          <select v-model="form.veiculoId" required class="modern-select">
            <option value="" disabled>Selecione o veículo</option>
            <option v-for="v in veiculosFiltrados" :key="v.id" :value="v.id">
              {{ v.marca }} {{ v.modelo }} ({{ v.placa }})
            </option>
          </select>
        </div>

        <div class="form-group">
          <label>Técnico Responsável</label>
          <input type="text" v-model="form.tecnicoResponsavel" required placeholder="Nome do mecânico">
        </div>

        <div class="form-group">
          <label>Relato do Cliente / Diagnóstico</label>
          <textarea v-model="form.descricao" required rows="3" placeholder="Descreva o problema relatado pelo cliente..."></textarea>
        </div>

        <div class="form-footer">
          <button type="button" class="btn-secondary" @click="showModal = false">Cancelar</button>
          <button type="submit" class="primary">Iniciar Serviço</button>
        </div>
      </form>
    </Modal>
  </div>
</template>

<style scoped>
.page-container { max-width: 1200px; margin: 0 auto; }
.header-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px; }
.subtitle { color: var(--text-muted); font-size: 0.9rem; margin-top: 4px; }

.filter-bar { display: flex; gap: 20px; padding: 15px 24px; margin-bottom: 24px; align-items: center; }
.search-input { flex: 1; position: relative; display: flex; align-items: center; gap: 10px; }
.search-input input { border: none; background: transparent; width: 100%; outline: none; font-size: 0.9rem; }
.search-input .icon { opacity: 0.4; }

.table-card { padding: 0; overflow: hidden; border: 1px solid #edf2f7; }

.os-number { font-weight: 700; color: var(--primary); background: var(--primary-light); padding: 4px 8px; border-radius: 6px; }

.client-car { display: flex; flex-direction: column; }
.client-car strong { color: var(--text-heading); font-size: 0.95rem; }
.client-car span { color: var(--text-muted); font-size: 0.8rem; }

/* Status Badges Custom */
.status-badge { 
  padding: 6px 12px; border-radius: 20px; font-size: 0.7rem; font-weight: 800; 
  text-transform: uppercase; letter-spacing: 0.5px;
}
.status-badge.ABERTA { background: #e0f2fe; color: #0369a1; }
.status-badge.EM_EXECUCAO { background: #fef3c7; color: #92400e; }
.status-badge.CONCLUIDA { background: #dcfce7; color: #166534; }
.status-badge.AGUARDANDO_PECAS { background: #fee2e2; color: #991b1b; }

.price { color: var(--success); }
.text-right { text-align: right; }
.btn-icon { background: transparent; padding: 8px; border: none; cursor: pointer; color: var(--text-muted); }
.btn-icon:hover { color: var(--primary); }

.modern-form .form-group { margin-bottom: 18px; }
.modern-form label { display: block; font-size: 0.85rem; font-weight: 600; margin-bottom: 6px; color: var(--text-heading); }
.modern-form input, .modern-select, .modern-form textarea { 
  width: 100%; padding: 10px 12px; border: 1px solid #e2e8f0; border-radius: 8px;
  background: #f8fafc; font-size: 0.95rem; outline: none;
}
.modern-form textarea { resize: none; }
.form-footer { display: flex; justify-content: flex-end; gap: 12px; margin-top: 24px; }
.btn-secondary { background: #f1f4f8; color: var(--text-body); }
.loading-state { padding: 40px; text-align: center; color: var(--text-muted); }
</style>

<script setup lang="ts">
import { ref, onMounted, reactive, computed } from 'vue';
import { OSService, ClienteService, VeiculoService } from '../services/appService';
import type { OrdemServico, Cliente, Veiculo } from '../types';
import Modal from '../components/Modal.vue';

const ordens = ref<OrdemServico[]>([]);
const clientes = ref<Cliente[]>([]);
const veiculos = ref<Veiculo[]>([]);
const loading = ref(true);
const showModal = ref(false);

const form = reactive({
  clienteId: '',
  veiculoId: '',
  tecnicoResponsavel: '',
  descricao: '',
  status: 'ABERTA'
});

const veiculosFiltrados = computed(() => {
  if (!form.clienteId) return [];
  return veiculos.value.filter(v => v.clienteId === Number(form.clienteId));
});

const fetchData = async () => {
  try {
    loading.value = true;
    const [osRes, cRes, vRes] = await Promise.all([
      OSService.getAll(),
      ClienteService.getAll(),
      VeiculoService.getAll()
    ]);
    ordens.value = osRes.data;
    clientes.value = cRes.data;
    veiculos.value = vRes.data;
  } catch (error) {
    console.error("Erro ao carregar dados", error);
  } finally {
    loading.value = false;
  }
};

const openModal = () => {
  showModal.value = true;
};

const onClienteChange = () => {
  form.veiculoId = '';
};

const saveOS = async () => {
  try {
    await OSService.create(form);
    await fetchData();
    showModal.value = false;
    // Reset form
    Object.assign(form, { clienteId: '', veiculoId: '', tecnicoResponsavel: '', descricao: '', status: 'ABERTA' });
  } catch (err) {
    alert('Erro ao abrir Ordem de Serviço.');
  }
};

onMounted(fetchData);
</script>
