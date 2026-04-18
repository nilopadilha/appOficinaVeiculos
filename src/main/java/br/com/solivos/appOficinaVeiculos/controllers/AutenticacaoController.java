package br.com.solivos.appOficinaVeiculos.controllers;


import br.com.solivos.appOficinaVeiculos.config.TokenService;
import br.com.solivos.appOficinaVeiculos.dtos.LoginRequestDTO;
import br.com.solivos.appOficinaVeiculos.dtos.LoginResponseDTO;
import br.com.solivos.appOficinaVeiculos.models.Usuario;
import br.com.solivos.appOficinaVeiculos.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;// Use o Manager
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoController(AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        // Log para ver o que está vindo do Vue
        System.out.println("Tentativa de login: " + dto.email());

        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(dto.email())
                .orElseThrow(() -> {
                    System.out.println("ERRO: Usuário não encontrado no banco: " + dto.email());
                    return new RuntimeException("Usuário inexistente ou senha inválida");
                });

        boolean senhaOk = passwordEncoder.matches(dto.senha(), usuario.getSenha());
        System.out.println("Senha informada bate com o hash? " + senhaOk);

        if (!senhaOk) {
            throw new RuntimeException("Usuário inexistente ou senha inválida");
        }

        String token = tokenService.gerarToken(usuario);
        return ResponseEntity.ok(new LoginResponseDTO(token, usuario.getEmail(), usuario.getRole().name()));
    }
}