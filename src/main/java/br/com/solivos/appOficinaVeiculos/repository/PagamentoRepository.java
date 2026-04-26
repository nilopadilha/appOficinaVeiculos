package br.com.solivos.appOficinaVeiculos.repository;


import br.com.solivos.appOficinaVeiculos.enumerated.StatusPagamento;
import br.com.solivos.appOficinaVeiculos.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {
    // Busca o pagamento de uma OS específica
    Optional<Pagamento> findByOrdemServicoId(UUID osId);

    // Relatório financeiro por status
    List<Pagamento> findByStatus(StatusPagamento status);

    @Query("SELECT SUM(p.valorTotal) FROM Pagamento p WHERE p.status = br.com.solivos.appOficinaVeiculos.enumerated.StatusPagamento.PAGO AND p.dataPagamento >= :data")
    BigDecimal sumFaturamentoDesde(LocalDateTime data);
}
