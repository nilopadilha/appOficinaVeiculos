package br.com.solivos.appOficinaVeiculos.servicos;

import br.com.solivos.appOficinaVeiculos.dtos.OrdemPecaRequestDTO;
import br.com.solivos.appOficinaVeiculos.models.OrdemPeca;
import br.com.solivos.appOficinaVeiculos.models.OrdemServico;
import br.com.solivos.appOficinaVeiculos.models.Peca;
import br.com.solivos.appOficinaVeiculos.repository.OrdemPecaRepository;
import br.com.solivos.appOficinaVeiculos.repository.OrdemServicoRepository;
import br.com.solivos.appOficinaVeiculos.repository.PecaRepository;
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
class OrdemPecaServiceTest {

    @Mock
    private OrdemPecaRepository ordemPecaRepository;
    @Mock
    private OrdemServicoRepository osRepository;
    @Mock
    private PecaRepository pecaRepository;

    @InjectMocks
    private OrdemPecaService service;

    @Test
    @DisplayName("Deve adicionar peça à OS, baixar estoque e congelar preço unitário")
    void deveAdicionarPecaComSucesso() {
        // Arrange
        UUID osId = UUID.randomUUID();
        UUID pecaId = UUID.randomUUID();
        OrdemPecaRequestDTO dto = new OrdemPecaRequestDTO(pecaId, 2);

        OrdemServico os = new OrdemServico();
        os.setId(osId);

        Peca peca = new Peca();
        peca.setId(pecaId);
        peca.setNome("Óleo 5W30");
        peca.setPrecoUnitario(new BigDecimal("50.00"));
        peca.setQuantidadeEstoque(10);

        when(osRepository.findById(osId)).thenReturn(Optional.of(os));
        when(pecaRepository.findById(pecaId)).thenReturn(Optional.of(peca));

        // Act
        service.adicionarPecaAOrdem(osId, dto);

        // Assert
        assertEquals(8, peca.getQuantidadeEstoque()); // Baixou estoque (10 - 2)
        verify(ordemPecaRepository).save(argThat(item -> 
            item.getPrecoAplicado().equals(new BigDecimal("50.00")) && // Preço congelado
            item.getQuantidade().equals(2)
        ));
        verify(pecaRepository).save(peca);
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar adicionar peça sem estoque suficiente")
    void deveLancarErroEstoqueInsuficiente() {
        // Arrange
        UUID osId = UUID.randomUUID();
        UUID pecaId = UUID.randomUUID();
        OrdemPecaRequestDTO dto = new OrdemPecaRequestDTO(pecaId, 20); // Pede 20

        Peca peca = new Peca();
        peca.setQuantidadeEstoque(5); // Só tem 5

        when(osRepository.findById(any())).thenReturn(Optional.of(new OrdemServico()));
        when(pecaRepository.findById(any())).thenReturn(Optional.of(peca));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.adicionarPecaAOrdem(osId, dto));
        verify(ordemPecaRepository, never()).save(any());
    }
}
