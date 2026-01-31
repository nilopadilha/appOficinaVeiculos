package br.com.solivos.appOficinaVeiculos.servicos;


import br.com.solivos.appOficinaVeiculos.dtos.UsuarioRequestDTO;
import br.com.solivos.appOficinaVeiculos.enumerated.Role;
import br.com.solivos.appOficinaVeiculos.models.Usuario;
import br.com.solivos.appOficinaVeiculos.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UsuarioService {

    // Adicione 'final' para que o Lombok injete o repositório corretamente
    private final UsuarioRepository repository;

    // Injete o passwordEncoder em vez de dar 'new' (melhor prática de segurança)
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void criarUsuario(UsuarioRequestDTO dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new RuntimeException("E-mail já cadastrado no sistema.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());

        // Criptografando a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        // Como agora usamos Enum Role, passamos o valor diretamente do DTO
        // O UsuarioRequestDTO já deve receber o Role do tipo Enum
        usuario.setRole(dto.role() != null ? dto.role() : Role.MECANICO);

        repository.save(usuario);
    }
}