package br.com.solivos.appOficinaVeiculos.servicos;

import br.com.solivos.appOficinaVeiculos.dtos.OrdemServicoRequestDTO;
import br.com.solivos.appOficinaVeiculos.dtos.OrdemServicoResponseDTO;
import br.com.solivos.appOficinaVeiculos.enumerated.StatusOS;
import br.com.solivos.appOficinaVeiculos.enumerated.TipoServico;
import br.com.solivos.appOficinaVeiculos.models.OrdemServico;
import br.com.solivos.appOficinaVeiculos.models.Veiculo;
import br.com.solivos.appOficinaVeiculos.repository.OrdemServicoRepository;
import br.com.solivos.appOficinaVeiculos.repository.UsuarioRepository;
import br.com.solivos.appOficinaVeiculos.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdemServicoServiceTest {

    @Mock
    private OrdemServicoRepository repository;
    @Mock
    private VeiculoRepository veiculoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private OrdemServicoService service;

    private Veiculo veiculo;
    private UUID veiculoId;

    @BeforeEach
    void setUp() {
        veiculoId = UUID.randomUUID();
        veiculo = new Veiculo();
        veiculo.setId(veiculoId);
        veiculo.setModelo("Civic");
        veiculo.setPlaca("ABC1234");
    }

    @Test
    @DisplayName("Deve abrir uma ordem de serviço de pintura com fotos com sucesso")
    void deveAbrirOrdemPinturaComSucesso() {
        // Arrange
        OrdemServicoRequestDTO dto = new OrdemServicoRequestDTO(
                veiculoId,
                null,
                "Pintura da porta esquerda",
                new BigDecimal("500.00"),
                null,
                TipoServico.PINTURA,
                "['foto1.jpg', 'foto2.jpg']"
        );

        OrdemServico osSalva = new OrdemServico();
        osSalva.setId(UUID.randomUUID());
        osSalva.setVeiculo(veiculo);
        osSalva.setDescricaoProblema(dto.descricaoProblema());
        osSalva.setTipoServico(TipoServico.PINTURA);
        osSalva.setFotosPintura(dto.fotosPintura());
        osSalva.setStatus(StatusOS.ORCAMENTO);
        osSalva.setValorMaoObra(dto.valorMaoObra());

        when(veiculoRepository.findById(veiculoId)).thenReturn(Optional.of(veiculo));
        when(repository.save(any(OrdemServico.class))).thenReturn(osSalva);

        // Act
        OrdemServicoResponseDTO response = service.abrirOrdem(dto);

        // Assert
        assertNotNull(response);
        assertEquals(TipoServico.PINTURA, response.tipoServico());
        assertEquals("['foto1.jpg', 'foto2.jpg']", response.fotosPintura());
        verify(repository, times(1)).save(any(OrdemServico.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao abrir ordem para veículo inexistente")
    void deveLancarExcecaoVeiculoInexistente() {
        // Arrange
        OrdemServicoRequestDTO dto = new OrdemServicoRequestDTO(
                UUID.randomUUID(), null, "Erro", BigDecimal.ZERO, null, TipoServico.MECANICA, null
        );
        when(veiculoRepository.findById(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.abrirOrdem(dto));
        verify(repository, never()).save(any());
    }
}
