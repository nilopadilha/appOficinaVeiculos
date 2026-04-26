export interface Cliente {
  id?: string;
  nome: string;
  documento: string;
  telefone: string;
  email: string; // Adicionado novamente
  vip?: boolean;
  endereco?: string;
  veiculosCount?: number;
}

export interface Veiculo {
  id?: number;
  placa: string;
  modelo: string;
  marca: string;
  ano: number;
  cor: string;
  clienteId: number;
  dono?: string;
}

export interface Peca {
  id?: number;
  codigo: string;
  descricao: string;
  categoria: string;
  qtd: number;
  preco: number;
}

export interface OrdemServico {
  id?: number;
  dataAbertura: string;
  clienteNome: string;
  veiculoPlaca: string;
  tecnicoResponsavel: string;
  total: number;
  status: 'ABERTA' | 'EM_EXECUCAO' | 'AGUARDANDO_PECAS' | 'CONCLUIDA' | 'CANCELADA';
  descricao: string;
}

export interface DashboardStats {
  totalClientes: number;
  totalOrdensAtivas: number;
  faturamentoMensal: number;
}
