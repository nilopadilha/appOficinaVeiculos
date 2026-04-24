<template>
  <div class="login-container">
    <div class="login-card">
      <div class="logo">🚗 Oficina <span>Solivos</span></div>
      <h2>Acesso ao Sistema</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label>E-mail</label>
          <input type="email" v-model="email" required placeholder="admin@solivos.com">
        </div>
        <div class="form-group">
          <label>Senha</label>
          <input type="password" v-model="password" required placeholder="••••••••">
        </div>
        <button type="submit" class="primary full-width">Entrar</button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const email = ref('');
const password = ref('');

const handleLogin = () => {
  // Simulando login
  localStorage.setItem('token', 'fake-jwt-token');
  router.push('/');
};
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--bg-dark);
}
.login-card {
  width: 400px;
  background: var(--bg-card);
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.5);
  text-align: center;
}
.logo { font-size: 1.8rem; font-weight: bold; margin-bottom: 30px; }
.logo span { color: var(--primary); }
.form-group { text-align: left; margin-bottom: 20px; }
label { display: block; margin-bottom: 8px; color: var(--text-muted); }
input { 
  width: 100%; 
  padding: 12px; 
  background: rgba(255,255,255,0.05); 
  border: 1px solid rgba(255,255,255,0.1);
  color: white;
  border-radius: 6px;
}
.full-width { width: 100%; margin-top: 10px; }
</style>
