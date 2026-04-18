package br.com.solivos.appOficinaVeiculos.servicos;

import br.com.solivos.appOficinaVeiculos.dtos.PagamentoRequestDTO;
import br.com.solivos.appOficinaVeiculos.dtos.PagamentoResponseDTO;
import br.com.solivos.appOficinaVeiculos.enumerated.StatusPagamento;
import br.com.solivos.appOficinaVeiculos.models.OrdemServico;
import br.com.solivos.appOficinaVeiculos.models.Pagamento;
import br.com.solivos.appOficinaVeiculos.repository.OrdemServicoRepository;
import br.com.solivos.appOficinaVeiculos.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final OrdemServicoRepository osRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository, OrdemServicoRepository osRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.osRepository = osRepository;
    }

    @Transactional
    public PagamentoResponseDTO gerarPagamento(PagamentoRequestDTO dto) {
        OrdemServico os = osRepository.findById(dto.osId())
                .orElseThrow(() -> new RuntimeException("Ordem de Serviço não encontrada"));

        // Cálculo dinâmico: Mão de Obra + (Quantidade * Preço Aplicado de cada peça)
        BigDecimal totalPecas = os.getPecas().stream()
                .map(item -> item.getPrecoAplicado().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorFinal = os.getValorMaoObra().add(totalPecas);

        Pagamento pagamento = new Pagamento();
        pagamento.setOrdemServico(os);
        pagamento.setValorTotal(valorFinal);
        pagamento.setMetodo(dto.metodo());
        pagamento.setStatus(StatusPagamento.PENDENTE);

        return toResponseDTO(pagamentoRepository.save(pagamento));
    }

    @Transactional(readOnly = true)
    public List<PagamentoResponseDTO> listarPorStatus(StatusPagamento status) {
        // Uso do método refinado findByStatus do Repository
        return pagamentoRepository.findByStatus(status).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public PagamentoResponseDTO confirmarPagamento(UUID id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        pagamento.setStatus(StatusPagamento.PAGO);
        pagamento.setDataPagamento(LocalDateTime.now());

        return toResponseDTO(pagamentoRepository.save(pagamento));
    }

    private PagamentoResponseDTO toResponseDTO(Pagamento p) {
        return new PagamentoResponseDTO(
                p.getId(), p.getOrdemServico().getId(), p.getValorTotal(),
                p.getMetodo(), p.getStatus(), p.getDataPagamento()
        );
    }
}