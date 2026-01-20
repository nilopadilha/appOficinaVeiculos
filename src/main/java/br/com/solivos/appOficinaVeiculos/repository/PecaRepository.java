package br.com.solivos.appOficinaVeiculos.repository;

import br.com.solivos.appOficinaVeiculos.models.Peca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PecaRepository extends JpaRepository<Peca, UUID> {

    Optional<Peca> findBySkuIgnoreCase(String sku);

    // Busca simplificada para o auto-complete do frontend
    List<Peca> findByNomeContainingIgnoreCase(String nome);

    // Consulta customizada para peças que precisam de compra (Abaixo do estoque mínimo)
    @Query("SELECT p FROM Peca p WHERE p.quantidadeEstoque <= p.estoqueMinimo")
    List<Peca> findPecasComEstoqueBaixo();
}