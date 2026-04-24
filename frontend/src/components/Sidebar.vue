<template>
  <aside class="sidebar">
    <div class="brand">
      <div class="logo-icon">S</div>
      <div class="brand-text">
        <span class="main">Solivos</span>
        <span class="sub">Oficina Pro</span>
      </div>
    </div>
    
    <nav class="nav-menu">
      <router-link to="/" class="nav-link">
        <i class="icon">📊</i> Dashboard
      </router-link>
      <router-link to="/clientes" class="nav-link">
        <i class="icon">👥</i> Clientes
      </router-link>
      <router-link to="/veiculos" class="nav-link">
        <i class="icon">🚘</i> Veículos
      </router-link>
      <router-link to="/ordens-servico" class="nav-link">
        <i class="icon">🛠️</i> Ordens de Serviço
      </router-link>
      <router-link to="/pecas" class="nav-link">
        <i class="icon">📦</i> Peças & Estoque
      </router-link>
    </nav>

    <div class="user-card">
      <div class="user-avatar">AD</div>
      <div class="user-info">
        <p class="u-name">Admin</p>
        <p class="u-role">Gerente</p>
      </div>
    </div>
  </aside>
</template>

<style scoped>
.sidebar {
  width: var(--sidebar-width);
  background: #0e1420; /* Marinho Escuro Profissional */
  color: white;
  display: flex;
  flex-direction: column;
  height: 100vh;
  position: sticky;
  top: 0;
}

.brand {
  padding: 32px 24px;
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo-icon {
  width: 40px;
  height: 40px;
  background: var(--primary);
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 800;
  font-size: 1.5rem;
  box-shadow: 0 4px 12px rgba(0, 97, 242, 0.4);
}

.brand-text { display: flex; flex-direction: column; }
.brand-text .main { font-weight: 700; font-size: 1.2rem; letter-spacing: -0.5px; }
.brand-text .sub { font-size: 0.7rem; color: #69707a; text-transform: uppercase; letter-spacing: 1px; }

.nav-menu { flex: 1; padding: 0 12px; }

.nav-link {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 14px 16px;
  color: #94a3b8;
  text-decoration: none;
  border-radius: 8px;
  margin-bottom: 4px;
  font-weight: 500;
  transition: all 0.2s;
}

.nav-link:hover {
  background: rgba(255, 255, 255, 0.05);
  color: white;
}

.router-link-active {
  background: var(--primary) !important;
  color: white !important;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.icon { font-style: normal; font-size: 1.2rem; }

.user-card {
  margin: 24px 12px;
  padding: 16px;
  background: rgba(255, 255, 255, 0.03);
  border-radius: 12px;
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar {
  width: 36px;
  height: 36px;
  background: #344054;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.8rem;
  font-weight: 700;
}

.u-name { font-size: 0.85rem; font-weight: 600; }
.u-role { font-size: 0.7rem; color: #69707a; }
</style>
