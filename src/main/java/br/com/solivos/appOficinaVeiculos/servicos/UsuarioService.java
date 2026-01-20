package br.com.solivos.appOficinaVeiculos.servicos;


import br.com.solivos.appOficinaVeiculos.dtos.UsuarioRequestDTO;

import br.com.solivos.appOficinaVeiculos.models.Usuario;

import br.com.solivos.appOficinaVeiculos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private UsuarioRepository repository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

        usuario.setRole(dto.role() != null ? dto.role().toUpperCase() : "USER");

        repository.save(usuario);
    }
}