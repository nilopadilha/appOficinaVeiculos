package br.com.solivos.appOficinaVeiculos.controllers;

import br.com.solivos.appOficinaVeiculos.config.TokenService;
import br.com.solivos.appOficinaVeiculos.dtos.LoginRequestDTO;
import br.com.solivos.appOficinaVeiculos.enumerated.Role;
import br.com.solivos.appOficinaVeiculos.models.Usuario;
import br.com.solivos.appOficinaVeiculos.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AutenticacaoController.class)
@AutoConfigureMockMvc(addFilters = false)
class AutenticacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private UsuarioRepository usuarioRepository;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve realizar login com sucesso e retornar token")
    void deveFazerLoginComSucesso() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO("admin@oficina.com", "senha123");
        Usuario usuario = new Usuario();
        usuario.setEmail("admin@oficina.com");
        usuario.setSenha("hash_senha");
        usuario.setRole(Role.ADMIN);

        when(usuarioRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(tokenService.gerarToken(usuario)).thenReturn("token_jwt_gerado");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token_jwt_gerado"))
                .andExpect(jsonPath("$.email").value("admin@oficina.com"));
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar login com senha inválida")
    void deveFalharLoginSenhaInvalida() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO("admin@oficina.com", "senha_errada");
        
        when(usuarioRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(new Usuario()));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()); // O Handler captura RuntimeException como 400
    }
}
