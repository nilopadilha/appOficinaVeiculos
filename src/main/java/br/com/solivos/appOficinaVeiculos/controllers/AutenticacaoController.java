package br.com.solivos.appOficinaVeiculos.controllers;


import br.com.solivos.appOficinaVeiculos.config.TokenService;
import br.com.solivos.appOficinaVeiculos.dtos.LoginRequestDTO;
import br.com.solivos.appOficinaVeiculos.dtos.LoginResponseDTO;
import br.com.solivos.appOficinaVeiculos.models.Usuario;
import br.com.solivos.appOficinaVeiculos.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private UsuarioRepository usuarioRepository;
    private TokenService tokenService;
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        // 1. Busca o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário ou senha inválidos"));

        // 2. Verifica se a senha coincide com o hash BCrypt
        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }

        // 3. Gera o token JWT
        String token = tokenService.gerarToken(usuario);

        return ResponseEntity.ok(new LoginResponseDTO(
                token,
                usuario.getEmail(),
                usuario.getRole()
        ));
    }
}