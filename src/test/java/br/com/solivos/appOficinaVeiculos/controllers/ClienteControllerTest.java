package br.com.solivos.appOficinaVeiculos.controllers;

import br.com.solivos.appOficinaVeiculos.dtos.ClienteRequestDTO;
import br.com.solivos.appOficinaVeiculos.dtos.ClienteResponseDTO;
import br.com.solivos.appOficinaVeiculos.servicos.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
@AutoConfigureMockMvc
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve listar todos os clientes com sucesso")
    void deveListarClientes() throws Exception {
        ClienteResponseDTO cliente = new ClienteResponseDTO(UUID.randomUUID(), "João Silva", "12345678901", "11999999999", false, "{}");
        when(clienteService.listarTodos()).thenReturn(List.of(cliente));

        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João Silva"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve criar um novo cliente com sucesso")
    void deveCriarCliente() throws Exception {
        ClienteRequestDTO request = new ClienteRequestDTO("João Silva", "12345678901", "11999999999", false, "{}");
        ClienteResponseDTO response = new ClienteResponseDTO(UUID.randomUUID(), "João Silva", "12345678901", "11999999999", false, "{}");
        
        when(clienteService.criar(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/clientes")
                        .with(csrf()) // Necessário pois o CSRF está ativo por padrão no WebMvcTest
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    @DisplayName("Deve retornar 401 ao tentar acessar sem autenticação")
    void deveRetornar401SemLogin() throws Exception {
        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isUnauthorized());
    }
}
