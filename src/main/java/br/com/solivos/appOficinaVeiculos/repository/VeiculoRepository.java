package br.com.solivos.appOficinaVeiculos.repository;


import br.com.solivos.appOficinaVeiculos.models.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {
    // A placa é o identificador mais comum na oficina
    Optional<Veiculo> findByPlacaIgnoreCase(String placa);

    // Busca todos os veículos de um cliente específico
    List<Veiculo> findAllByClienteId(UUID clienteId);

    // Busca por chassi para vistorias
    Optional<Veiculo> findByVinChassi(String vinChassi);
}