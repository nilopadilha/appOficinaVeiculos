<template>
  <div class="page-container">
    <div class="header-row">
      <div>
        <h1>Clientes</h1>
        <p class="subtitle">Gerencie sua base de clientes e contatos.</p>
      </div>
      <button class="primary" @click="showModal = true">+ Novo Cliente</button>
    </div>

    <div class="card table-card">
      <div v-if="loading" class="loading-state">Carregando clientes...</div>
      
      <table class="data-table" v-else>
        <thead>
          <tr>
            <th>Nome</th>
            <th>CPF/CNPJ</th>
            <th>Telefone</th>
            <th>E-mail</th>
            <th>Status</th>
            <th class="text-right">Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="c in clientes" :key="c.id">
            <td>
              <div class="user-info">
                <div class="user-initials">{{ c.nome.charAt(0) }}</div>
                <strong>{{ c.nome }}</strong>
              </div>
            </td>
            <td>{{ c.documento }}</td>
            <td>{{ c.telefone }}</td>
            <td>{{ c.email }}</td>
            <td><span :class="['badge', c.vip ? 'badge-success' : 'badge-warning']">{{ c.vip ? 'VIP' : 'Comum' }}</span></td>
            <td class="text-right">
               <button class="btn-icon" @click="editCliente(c)" title="Editar">✏️</button>
               <button class="btn-icon danger" @click="deleteCliente(c.id!)" title="Excluir">🗑️</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <Modal :show="showModal" :title="editingId ? 'Editar Cliente' : 'Novo Cliente'" @close="closeModal">
      <form @submit.prevent="saveCliente" class="modern-form">
        <div class="form-group">
          <label>Nome Completo</label>
          <input type="text" v-model="form.nome" required placeholder="Ex: João Silva">
        </div>
        <div class="form-row">
          <div class="form-group">
            <label>CPF ou CNPJ</label>
            <input type="text" v-model="form.documento" required maxlength="14" placeholder="000.000.000-00">
          </div>
          <div class="form-group">
            <label>Telefone</label>
            <input type="text" v-model="form.telefone" placeholder="(00) 00000-0000">
          </div>
        </div>
        <div class="form-group">
          <label>E-mail</label>
          <input type="email" v-model="form.email" placeholder="cliente@email.com">
        </div>
        <div class="form-group">
          <label class="toggle">
            <input type="checkbox" v-model="form.vip">
            <span class="slider"></span>
            Cliente VIP
          </label>
        </div>
        <div class="form-footer">
          <button type="button" class="btn-secondary" @click="closeModal">Cancelar</button>
          <button type="submit" class="primary">Salvar Alterações</button>
        </div>
      </form>
    </Modal>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue';
import { ClienteService } from '../services/appService';
import type { Cliente } from '../types';
import Modal from '../components/Modal.vue';

const clientes = ref<Cliente[]>([]);
const loading = ref(true);
const showModal = ref(false);
const editingId = ref<string | null>(null);

const form = reactive<Cliente>({
  nome: '',
  documento: '',
  telefone: '',
  email: '',
  vip: false,
  endereco: '{}'
});

const fetchClientes = async () => {
  try {
    loading.value = true;
    const response = await ClienteService.getAll();
    clientes.value = response.data;
  } catch (err) {
    console.error(err);
  } finally {
    loading.value = false;
  }
};

const saveCliente = async () => {
  try {
    const payload = { ...form };
    if (editingId.value) {
      await ClienteService.update(editingId.value as any, payload);
    } else {
      await ClienteService.create(payload);
    }
    await fetchClientes();
    closeModal();
  } catch (err) {
    console.error(err);
  }
};

const editCliente = (cliente: Cliente) => {
  editingId.value = cliente.id!;
  Object.assign(form, {
    nome: cliente.nome,
    documento: cliente.documento,
    telefone: cliente.telefone,
    email: cliente.email,
    vip: cliente.vip,
    endereco: cliente.endereco || '{}'
  });
  showModal.value = true;
};

const deleteCliente = async (id: string) => {
  if (confirm('Deseja realmente excluir este cliente?')) {
    try {
      await ClienteService.delete(id as any);
      await fetchClientes();
    } catch (err) {
      alert('Erro ao excluir cliente.');
    }
  }
};

const closeModal = () => {
  showModal.value = false;
  editingId.value = null;
  form.nome = '';
  form.documento = '';
  form.telefone = '';
  form.email = '';
  form.vip = false;
  form.endereco = '{}';
};

onMounted(fetchClientes);
</script>

<style scoped>
.page-container { max-width: 1200px; margin: 0 auto; }
.header-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 32px; }
.subtitle { color: var(--text-muted); font-size: 0.9rem; margin-top: 4px; }

.table-card { padding: 0; overflow: hidden; border: 1px solid #edf2f7; }

.user-info { display: flex; align-items: center; gap: 12px; }
.user-initials { 
  width: 32px; height: 32px; background: var(--primary-light); color: var(--primary);
  border-radius: 8px; display: flex; align-items: center; justify-content: center;
  font-weight: 700; font-size: 0.8rem;
}

.text-right { text-align: right; }
.btn-icon { 
  background: transparent; padding: 8px; border-radius: 6px; 
  color: var(--text-muted); font-size: 1.1rem; border: none; cursor: pointer;
}
.btn-icon:hover { background: #f1f4f8; color: var(--primary); }
.btn-icon.danger:hover { background: #fff5f5; color: var(--danger); }

.modern-form .form-group { margin-bottom: 20px; }
.modern-form label { display: block; font-size: 0.85rem; font-weight: 600; margin-bottom: 8px; color: var(--text-heading); }
.modern-form input { 
  width: 100%; padding: 12px; border: 1px solid #e2e8f0; border-radius: 8px;
  background: #f8fafc; transition: all 0.2s; font-size: 0.95rem;
}
.modern-form input:focus { border-color: var(--primary); outline: none; background: white; box-shadow: 0 0 0 3px rgba(0, 97, 242, 0.1); }

.form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
.form-footer { display: flex; justify-content: flex-end; gap: 12px; margin-top: 30px; }

.btn-secondary { background: #f1f4f8; color: var(--text-body); }

.loading-state { padding: 40px; text-align: center; color: var(--text-muted); }

/* Toggle Switch */
.toggle { display: flex; align-items: center; gap: 12px; cursor: pointer; }
.toggle input { display: none; }
.slider { 
  width: 42px; height: 22px; background: #cbd5e0; border-radius: 20px; position: relative; transition: 0.3s;
}
.slider:before { 
  content: ""; position: absolute; width: 16px; height: 16px; background: white; 
  border-radius: 50%; top: 3px; left: 3px; transition: 0.3s;
}
input:checked + .slider { background: var(--success); }
input:checked + .slider:before { transform: translateX(20px); }
</style>
