package br.com.solivos.appOficinaVeiculos.servicos;

import br.com.solivos.appOficinaVeiculos.dtos.PagamentoRequestDTO;
import br.com.solivos.appOficinaVeiculos.dtos.PagamentoResponseDTO;
import br.com.solivos.appOficinaVeiculos.enumerated.MetodoPagamento;
import br.com.solivos.appOficinaVeiculos.enumerated.StatusPagamento;
import br.com.solivos.appOficinaVeiculos.models.OrdemPeca;
import br.com.solivos.appOficinaVeiculos.models.OrdemServico;
import br.com.solivos.appOficinaVeiculos.models.Pagamento;
import br.com.solivos.appOficinaVeiculos.repository.OrdemServicoRepository;
import br.com.solivos.appOficinaVeiculos.repository.PagamentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;
    @Mock
    private OrdemServicoRepository osRepository;

    @InjectMocks
    private PagamentoService service;

    @Test
    @DisplayName("Deve gerar pagamento com cálculo correto de Mão de Obra + Peças")
    void deveGerarPagamentoComCalculoCorreto() {
        // Arrange
        UUID osId = UUID.randomUUID();
        PagamentoRequestDTO dto = new PagamentoRequestDTO(osId, MetodoPagamento.PIX);

        OrdemServico os = new OrdemServico();
        os.setId(osId);
        os.setValorMaoObra(new BigDecimal("200.00"));

        OrdemPeca item1 = new OrdemPeca();
        item1.setPrecoAplicado(new BigDecimal("100.00"));
        item1.setQuantidade(2); // 200.00 em peças

        os.setPecas(List.of(item1));

        Pagamento pagamentoSalvo = new Pagamento();
        pagamentoSalvo.setId(UUID.randomUUID());
        pagamentoSalvo.setOrdemServico(os);
        pagamentoSalvo.setValorTotal(new BigDecimal("400.00")); // 200 mao obra + 200 pecas
        pagamentoSalvo.setMetodo(MetodoPagamento.PIX);
        pagamentoSalvo.setStatus(StatusPagamento.PENDENTE);

        when(osRepository.findById(osId)).thenReturn(Optional.of(os));
        when(pagamentoRepository.save(any())).thenReturn(pagamentoSalvo);

        // Act
        PagamentoResponseDTO response = service.gerarPagamento(dto);

        // Assert
        assertEquals(new BigDecimal("400.00"), response.valorTotal());
        verify(pagamentoRepository).save(any());
    }
}
