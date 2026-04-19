package br.com.solivos.appOficinaVeiculos.controllers;


import br.com.solivos.appOficinaVeiculos.config.TokenService;
import br.com.solivos.appOficinaVeiculos.dtos.LoginRequestDTO;
import br.com.solivos.appOficinaVeiculos.dtos.LoginResponseDTO;
import br.com.solivos.appOficinaVeiculos.models.Usuario;
import br.com.solivos.appOficinaVeiculos.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private static final Logger logger = LoggerFactory.getLogger(AutenticacaoController.class);

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        logger.info("Tentativa de login para o usuário: {}", dto.email());

        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(dto.email())
                .orElseThrow(() -> {
                    logger.warn("Usuário não encontrado: {}", dto.email());
                    return new RuntimeException("Usuário inexistente ou senha inválida");
                });

        boolean senhaOk = passwordEncoder.matches(dto.senha(), usuario.getSenha());

        if (!senhaOk) {
            logger.warn("Senha inválida para o usuário: {}", dto.email());
            throw new RuntimeException("Usuário inexistente ou senha inválida");
        }

        logger.info("Login realizado com sucesso para o usuário: {}", dto.email());
        String token = tokenService.gerarToken(usuario);
        return ResponseEntity.ok(new LoginResponseDTO(token, usuario.getEmail(), usuario.getRole().name()));
    }
}
