package br.com.solivos.appOficinaVeiculos.repository;


import br.com.solivos.appOficinaVeiculos.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    // Nome mais claro para autenticação
    Optional<Usuario> findByEmailIgnoreCase(String email);

    // Útil para carregar listas de seleção no frontend (Vue) por função
    List<Usuario> findAllByRoleOrderByNomeAsc(String role);

    boolean existsByEmail(String email);
}
