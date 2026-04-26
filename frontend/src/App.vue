<template>
  <div class="app-container">
    <Sidebar v-if="!isLoginPage" />
    <main class="main-content">
      <header class="top-bar" v-if="!isLoginPage">
        <div class="breadcrumb">{{ currentRouteName }}</div>
        <div class="actions">
          <button class="btn-icon">🔔</button>
          <button class="btn-logout" @click="handleLogout">Sair</button>
        </div>
      </header>
      <router-view />
    </main>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import Sidebar from './components/Sidebar.vue';

const route = useRoute();
const router = useRouter();

const isLoginPage = computed(() => route.name === 'Login');
const currentRouteName = computed(() => route.name || 'Dashboard');

const handleLogout = () => {
  localStorage.removeItem('token');
  router.push('/login');
};
</script>

<style>
.top-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding-bottom: 15px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.breadcrumb {
  font-size: 1.2rem;
  font-weight: 600;
  color: var(--text-main);
}

.actions {
  display: flex;
  gap: 15px;
}

.btn-icon {
  background: none;
  font-size: 1.2rem;
}

.btn-logout {
  background: rgba(231, 76, 60, 0.1);
  color: var(--danger);
  border: 1px solid var(--danger);
}

.btn-logout:hover {
  background: var(--danger);
  color: white;
}
</style>
