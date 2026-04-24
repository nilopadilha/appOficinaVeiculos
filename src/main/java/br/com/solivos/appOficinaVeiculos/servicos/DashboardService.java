package br.com.solivos.appOficinaVeiculos.servicos;

import br.com.solivos.appOficinaVeiculos.dtos.DashboardStatsDTO;
import br.com.solivos.appOficinaVeiculos.enumerated.StatusOS;
import br.com.solivos.appOficinaVeiculos.repository.ClienteRepository;
import br.com.solivos.appOficinaVeiculos.repository.OrdemServicoRepository;
import br.com.solivos.appOficinaVeiculos.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final OrdemServicoRepository osRepository;
    private final ClienteRepository clienteRepository;
    private final PagamentoRepository pagamentoRepository;

    public DashboardService(OrdemServicoRepository osRepository, 
                            ClienteRepository clienteRepository, 
                            PagamentoRepository pagamentoRepository) {
        this.osRepository = osRepository;
        this.clienteRepository = clienteRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    @Transactional(readOnly = true)
    public DashboardStatsDTO getStats() {
        long totalClientes = clienteRepository.count();
        long totalOrdensAtivas = osRepository.countByStatusNot(StatusOS.FINALIZADO);
        
        List<Object[]> statusCounts = osRepository.countByStatusGrouped();
        Map<String, Long> ordensPorStatus = new HashMap<>();
        for (Object[] result : statusCounts) {
            ordensPorStatus.put(result[0].toString(), (Long) result[1]);
        }

        LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0);
        BigDecimal faturamento = pagamentoRepository.sumFaturamentoDesde(inicioMes);
        if (faturamento == null) faturamento = BigDecimal.ZERO;

        return new DashboardStatsDTO(
                totalClientes,
                totalOrdensAtivas,
                ordensPorStatus,
                faturamento,
                totalOrdensAtivas // Na prática, pode ser um filtro diferente
        );
    }
}
