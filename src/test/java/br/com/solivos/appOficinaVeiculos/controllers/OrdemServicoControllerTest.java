package br.com.solivos.appOficinaVeiculos.controllers;

import br.com.solivos.appOficinaVeiculos.dtos.OrdemServicoRequestDTO;
import br.com.solivos.appOficinaVeiculos.dtos.OrdemServicoResponseDTO;
import br.com.solivos.appOficinaVeiculos.enumerated.StatusOS;
import br.com.solivos.appOficinaVeiculos.enumerated.TipoServico;
import br.com.solivos.appOficinaVeiculos.servicos.OrdemServicoService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdemServicoController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrdemServicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdemServicoService service;

    @MockBean
    private br.com.solivos.appOficinaVeiculos.config.TokenService tokenService;

    @MockBean
    private br.com.solivos.appOficinaVeiculos.repository.UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "MECANICO")
    @DisplayName("Deve abrir uma nova ordem de serviço via API")
    void deveAbrirOSViaAPI() throws Exception {
        UUID veiculoId = UUID.randomUUID();
        OrdemServicoRequestDTO request = new OrdemServicoRequestDTO(
                veiculoId, null, "Pintura completa", new BigDecimal("1000.00"), null, TipoServico.PINTURA, "['link1']"
        );

        OrdemServicoResponseDTO response = new OrdemServicoResponseDTO(
                UUID.randomUUID(), 1, "Pintura completa", StatusOS.ORCAMENTO, TipoServico.PINTURA,
                LocalDateTime.now(), new BigDecimal("1000.00"), "Civic", "ABC1234", "Não atribuído", "['link1']"
        );

        when(service.abrirOrdem(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/ordens-servico")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoServico").value("PINTURA"))
                .andExpect(jsonPath("$.fotosPintura").value("['link1']"));
    }
}
