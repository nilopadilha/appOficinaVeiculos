<template>
  <div class="page-container">
    <div class="header-row">
      <div>
        <h1>Veículos</h1>
        <p class="subtitle">Frota cadastrada vinculada aos clientes.</p>
      </div>
      <button class="primary" @click="openModal">+ Novo Veículo</button>
    </div>

    <div class="card table-card">
      <div v-if="loading" class="loading-state">Buscando veículos...</div>
      
      <table class="data-table" v-else>
        <thead>
          <tr>
            <th>Placa</th>
            <th>Marca / Modelo</th>
            <th>Ano</th>
            <th>Cor</th>
            <th>Proprietário</th>
            <th class="text-right">Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="v in veiculos" :key="v.id">
            <td>
              <div class="plate-badge">
                <div class="plate-top">BRASIL</div>
                <div class="plate-code">{{ v.placa }}</div>
              </div>
            </td>
            <td>
              <div class="car-info">
                <strong>{{ v.marca }}</strong>
                <span>{{ v.modelo }}</span>
              </div>
            </td>
            <td>{{ v.ano }}</td>
            <td><div class="color-dot" :style="{ backgroundColor: getColorHex(v.cor) }"></div> {{ v.cor }}</td>
            <td>
              <div class="owner-info">
                <i class="owner-icon">👤</i> {{ v.dono }}
              </div>
            </td>
            <td class="text-right">
               <button class="btn-icon danger" @click="deleteVeiculo(v.id!)">🗑️</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <Modal :show="showModal" title="Cadastrar Novo Veículo" @close="showModal = false">
      <form @submit.prevent="saveVeiculo" class="modern-form">
        <div class="form-group">
          <label>Proprietário</label>
          <select v-model="form.clienteId" required class="modern-select">
            <option value="" disabled>Selecione o dono do veículo</option>
            <option v-for="c in clientes" :key="c.id" :value="c.id">{{ c.nome }}</option>
          </select>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label>Placa</label>
            <input type="text" v-model="form.placa" required placeholder="ABC1D23" style="text-transform: uppercase">
          </div>
          <div class="form-group">
            <label>Cor</label>
            <input type="text" v-model="form.cor" required placeholder="Ex: Prata">
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label>Marca</label>
            <input type="text" v-model="form.marca" required placeholder="Ex: Toyota">
          </div>
          <div class="form-group">
            <label>Modelo</label>
            <input type="text" v-model="form.modelo" required placeholder="Ex: Corolla">
          </div>
        </div>
        <div class="form-group">
          <label>Ano de Fabricação</label>
          <input type="number" v-model="form.ano" required>
        </div>
        <div class="form-footer">
          <button type="button" class="btn-secondary" @click="showModal = false">Cancelar</button>
          <button type="submit" class="primary">Concluir Cadastro</button>
        </div>
      </form>
    </Modal>
  </div>
</template>

<style scoped>
.page-container { max-width: 1200px; margin: 0 auto; }
.header-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 32px; }
.subtitle { color: var(--text-muted); font-size: 0.9rem; margin-top: 4px; }

.table-card { padding: 0; overflow: hidden; border: 1px solid #edf2f7; }

/* Estilo Placa Mercosul */
.plate-badge {
  width: 90px; height: 35px; border: 2px solid #333; border-radius: 4px;
  background: white; overflow: hidden; display: flex; flex-direction: column;
}
.plate-top { 
  background: #003399; color: white; font-size: 6px; font-weight: 800; 
  text-align: center; padding: 1px; letter-spacing: 1px;
}
.plate-code { 
  color: #333; font-weight: 800; font-size: 0.9rem; text-align: center; 
  flex: 1; display: flex; align-items: center; justify-content: center;
}

.car-info { display: flex; flex-direction: column; }
.car-info strong { font-size: 1rem; color: var(--text-heading); }
.car-info span { font-size: 0.85rem; color: var(--text-muted); }

.color-dot { width: 12px; height: 12px; border-radius: 50%; display: inline-block; margin-right: 8px; border: 1px solid #eee; }

.owner-info { display: flex; align-items: center; gap: 8px; font-weight: 500; font-size: 0.9rem; }
.owner-icon { font-style: normal; opacity: 0.5; }

.text-right { text-align: right; }
.btn-icon { background: transparent; padding: 8px; border-radius: 6px; border: none; cursor: pointer; }
.btn-icon.danger:hover { background: #fff5f5; color: var(--danger); }

.modern-form .form-group { margin-bottom: 18px; }
.modern-form label { display: block; font-size: 0.85rem; font-weight: 600; margin-bottom: 6px; color: var(--text-heading); }
.modern-form input, .modern-select { 
  width: 100%; padding: 10px 12px; border: 1px solid #e2e8f0; border-radius: 8px;
  background: #f8fafc; font-size: 0.95rem;
}
.form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
.form-footer { display: flex; justify-content: flex-end; gap: 12px; margin-top: 24px; }
.btn-secondary { background: #f1f4f8; color: var(--text-body); }
.loading-state { padding: 40px; text-align: center; color: var(--text-muted); }
</style>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue';
import { VeiculoService, ClienteService } from '../services/appService';
import type { Veiculo, Cliente } from '../types';
import Modal from '../components/Modal.vue';

const veiculos = ref<Veiculo[]>([]);
const clientes = ref<Cliente[]>([]);
const loading = ref(true);
const showModal = ref(false);

const form = reactive<Veiculo>({
  placa: '',
  marca: '',
  modelo: '',
  ano: new Date().getFullYear(),
  cor: '',
  clienteId: 0
});

const fetchData = async () => {
  try {
    loading.value = true;
    const [vRes, cRes] = await Promise.all([
      VeiculoService.getAll(),
      ClienteService.getAll()
    ]);
    veiculos.value = vRes.data;
    clientes.value = cRes.data;
  } catch (err) {
    console.error(err);
  } finally {
    loading.value = false;
  }
};

const getColorHex = (cor: string) => {
  const colors: any = { 'Prata': '#c0c0c0', 'Preto': '#000', 'Branco': '#fff', 'Vermelho': '#ff0000', 'Azul': '#0000ff' };
  return colors[cor] || '#ddd';
};

const openModal = () => {
  showModal.value = true;
};

const saveVeiculo = async () => {
  try {
    await VeiculoService.create({ ...form });
    await fetchData();
    showModal.value = false;
  } catch (err) {
    alert('Erro ao cadastrar veículo.');
  }
};

const deleteVeiculo = async (id: number) => {
  if (confirm('Excluir veículo?')) {
    await VeiculoService.delete(id);
    await fetchData();
  }
};

onMounted(fetchData);
</script>
