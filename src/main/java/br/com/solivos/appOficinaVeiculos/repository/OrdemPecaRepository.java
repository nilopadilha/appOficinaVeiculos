package br.com.solivos.appOficinaVeiculos.repository;

import br.com.solivos.appOficinaVeiculos.models.OrdemPeca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrdemPecaRepository extends JpaRepository<OrdemPeca, UUID> {
    // Busca todas as peças de uma OS específica
    List<OrdemPeca> findAllByOrdemServicoId(UUID osId);
}