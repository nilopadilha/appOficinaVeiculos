<template>
  <div class="pecas-page">
    <div class="page-header">
      <h2>Estoque de Peças</h2>
      <button class="primary">+ Entrada de Peça</button>
    </div>
    <div class="card">
      <table class="data-table">
        <thead>
          <tr>
            <th>Código</th>
            <th>Descrição</th>
            <th>Categoria</th>
            <th>Qtd. Estoque</th>
            <th>Preço Venda</th>
            <th>Status</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="p in pecas" :key="p.id">
            <td>{{ p.codigo }}</td>
            <td>{{ p.descricao }}</td>
            <td>{{ p.categoria }}</td>
            <td>{{ p.qtd }}</td>
            <td>R$ {{ p.preco.toFixed(2) }}</td>
            <td>
              <span :class="['badge', p.qtd < 5 ? 'badge-danger' : 'badge-success']">
                {{ p.qtd < 5 ? 'Estoque Baixo' : 'Ok' }}
              </span>
            </td>
            <td>
               <button class="btn-sm">✏️</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
const pecas = ref([
  { id: 1, codigo: 'FIL-001', descricao: 'Filtro de Óleo - Corolla', categoria: 'Filtros', qtd: 15, preco: 45.90 },
  { id: 2, codigo: 'PAS-042', descricao: 'Pastilha de Freio Dianteira', categoria: 'Freios', qtd: 3, preco: 189.00 },
]);
</script>
