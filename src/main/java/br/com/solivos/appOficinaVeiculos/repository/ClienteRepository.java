package br.com.solivos.appOficinaVeiculos.repository;

import br.com.solivos.appOficinaVeiculos.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    // Busca exata para validação de cadastro
    boolean findByDocumento(String documento);

    // Busca fonética ou parcial para filtros no Vue
    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    // Filtro para marketing ou prioridade
    List<Cliente> findAllByVipTrue();
}