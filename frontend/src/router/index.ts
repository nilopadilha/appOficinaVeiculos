import { createRouter, createWebHistory } from 'vue-router';
import Dashboard from '../views/Dashboard.vue';

const routes = [
  {
    path: '/',
    name: 'Dashboard',
    component: Dashboard,
  },
  {
    path: '/clientes',
    name: 'Clientes',
    component: () => import('../views/Clientes.vue'),
  },
  {
    path: '/veiculos',
    name: 'Veiculos',
    component: () => import('../views/Veiculos.vue'),
  },
  {
    path: '/ordens-servico',
    name: 'OrdensServico',
    component: () => import('../views/OrdensServico.vue'),
  },
  {
    path: '/pecas',
    name: 'Pecas',
    component: () => import('../views/Pecas.vue'),
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Proteção de rotas
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  if (to.name !== 'Login' && !token) {
    next({ name: 'Login' });
  } else {
    next();
  }
});

export default router;
