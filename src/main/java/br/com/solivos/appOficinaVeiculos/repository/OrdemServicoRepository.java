package br.com.solivos.appOficinaVeiculos.repository;

import br.com.solivos.appOficinaVeiculos.enumerated.StatusOS;
import br.com.solivos.appOficinaVeiculos.models.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, UUID> {

    // Busca pelo número sequencial gerado pelo banco
    Optional<OrdemServico> findByNumeroOs(Integer numeroOs);

    // Filtro essencial para o painel (Board) do Vue
    List<OrdemServico> findAllByStatusOrderByDataAberturaDesc(StatusOS status);

    // Busca todas as OS de um veículo específico
    List<OrdemServico> findAllByVeiculoIdOrderByDataAberturaDesc(UUID veiculoId);

    // Busca ordens sob responsabilidade de um funcionário
    List<OrdemServico> findAllByResponsavelIdAndStatusNot(UUID responsavelId, StatusOS status);

    long countByStatusNot(StatusOS status);

    @org.springframework.data.jpa.repository.Query("SELECT o.status, COUNT(o) FROM OrdemServico o GROUP BY o.status")
    List<Object[]> countByStatusGrouped();
}