package br.com.solivos.appOficinaVeiculos.dtos;

import java.math.BigDecimal;
import java.util.Map;

public record DashboardStatsDTO(
        long totalClientes,
        long totalOrdensAtivas,
        Map<String, Long> ordensPorStatus,
        BigDecimal faturamentoMensal,
        long veiculosNaOficina
) {
}
