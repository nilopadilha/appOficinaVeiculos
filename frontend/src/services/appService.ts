import api from './api';
import type { Cliente, Veiculo, Peca, OrdemServico, DashboardStats } from '../types';

// AJUSTE AQUI: Mude para 'false' quando quiser conectar ao backend real
const USE_MOCKS = true; 

// Dados de Simulação (Mocks)
const mockStats: DashboardStats = { totalClientes: 42, totalOrdensAtivas: 12, faturamentoMensal: 15750.80 };
const mockClientes: Cliente[] = [
  { id: '1', nome: 'João da Silva', documento: '12345678901', telefone: '(11) 99999-8888', email: 'joao@email.com', vip: true, endereco: '{}' },
  { id: '2', nome: 'Maria Oliveira', documento: '98765432100', telefone: '(11) 77777-6666', email: 'maria@web.com', vip: false, endereco: '{}' }
];
const mockVeiculos: Veiculo[] = [
  { id: 1, placa: 'ABC-1234', marca: 'Toyota', modelo: 'Corolla', ano: 2022, cor: 'Prata', clienteId: 1, dono: 'João da Silva' },
  { id: 2, placa: 'XYZ-9876', marca: 'Honda', modelo: 'Civic', ano: 2021, cor: 'Preto', clienteId: 2, dono: 'Maria Oliveira' }
];
const mockOS: OrdemServico[] = [
  { id: 101, dataAbertura: '24/04/2026', clienteNome: 'João da Silva', veiculoPlaca: 'ABC-1234', tecnicoResponsavel: 'Ricardo', total: 1250.00, status: 'EM_EXECUCAO', descricao: 'Troca de óleo' },
  { id: 102, dataAbertura: '23/04/2026', clienteNome: 'Maria Oliveira', veiculoPlaca: 'XYZ-9876', tecnicoResponsavel: 'André', total: 450.00, status: 'CONCLUIDA', descricao: 'Alinhamento' }
];

// Helper Inteligente
const handleCall = async <T>(apiCall: () => Promise<{ data: T }>, mockData: T): Promise<{ data: T }> => {
  if (USE_MOCKS) {
    return new Promise(resolve => setTimeout(() => resolve({ data: mockData }), 300));
  }
  try {
    return await apiCall();
  } catch (error) {
    console.error("Erro na API, retornando Mocks...", error);
    return { data: mockData };
  }
};

export const ClienteService = {
  getAll: () => handleCall(() => api.get<Cliente[]>('/clientes'), mockClientes),
  create: (data: Cliente) => USE_MOCKS ? Promise.resolve({ data }) : api.post('/clientes', data),
  update: (id: string, data: Cliente) => api.put(`/clientes/${id}`, data),
  delete: (id: string) => api.delete(`/clientes/${id}`),
};

export const VeiculoService = {
  getAll: () => handleCall(() => api.get<Veiculo[]>('/veiculos'), mockVeiculos),
  create: (data: Veiculo) => USE_MOCKS ? Promise.resolve({ data }) : api.post('/veiculos', data),
  delete: (id: number) => api.delete(`/veiculos/${id}`),
};

export const OSService = {
  getAll: () => handleCall(() => api.get<OrdemServico[]>('/ordens-servico'), mockOS),
  getStats: () => handleCall(() => api.get<DashboardStats>('/dashboard/stats'), mockStats),
  create: (data: any) => USE_MOCKS ? Promise.resolve({ data }) : api.post('/ordens-servico', data),
};

export const PecaService = {
  getAll: () => api.get<Peca[]>('/pecas'),
};
