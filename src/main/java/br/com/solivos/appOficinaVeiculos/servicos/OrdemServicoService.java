package br.com.solivos.appOficinaVeiculos.servicos;

import br.com.solivos.appOficinaVeiculos.dtos.OrdemPecaDetalheDTO;
import br.com.solivos.appOficinaVeiculos.dtos.OrdemServicoDetalhadaDTO;
import br.com.solivos.appOficinaVeiculos.dtos.OrdemServicoRequestDTO;
import br.com.solivos.appOficinaVeiculos.dtos.OrdemServicoResponseDTO;
import br.com.solivos.appOficinaVeiculos.enumerated.StatusOS;
import br.com.solivos.appOficinaVeiculos.models.OrdemServico;
import br.com.solivos.appOficinaVeiculos.models.Usuario;
import br.com.solivos.appOficinaVeiculos.models.Veiculo;
import br.com.solivos.appOficinaVeiculos.repository.OrdemServicoRepository;
import br.com.solivos.appOficinaVeiculos.repository.UsuarioRepository;
import br.com.solivos.appOficinaVeiculos.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrdemServicoService {

    private static final Logger logger = LoggerFactory.getLogger(OrdemServicoService.class);

    private final OrdemServicoRepository repository;
    private final VeiculoRepository veiculoRepository;
    private final UsuarioRepository usuarioRepository;

    public OrdemServicoService(OrdemServicoRepository repository, 
                               VeiculoRepository veiculoRepository, 
                               UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.veiculoRepository = veiculoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public OrdemServicoResponseDTO abrirOrdem(OrdemServicoRequestDTO dto) {
        logger.info("Abrindo nova ordem de serviço para o veículo ID: {}", dto.veiculoId());
        
        Veiculo veiculo = veiculoRepository.findById(dto.veiculoId())
                .orElseThrow(() -> {
                    logger.error("Falha ao abrir OS: Veículo {} não encontrado", dto.veiculoId());
                    return new RuntimeException("Veículo não encontrado");
                });

        OrdemServico os = new OrdemServico();
        os.setVeiculo(veiculo);
        os.setDescricaoProblema(dto.descricaoProblema());
        os.setValorMaoObra(dto.valorMaoObra() != null ? dto.valorMaoObra() : java.math.BigDecimal.ZERO);
        os.setStatus(StatusOS.ORCAMENTO);
        os.setTipoServico(dto.tipoServico() != null ? dto.tipoServico() : br.com.solivos.appOficinaVeiculos.enumerated.TipoServico.MECANICA);
        os.setChecklistEntrada(dto.checklistEntrada());
        os.setFotosPintura(dto.fotosPintura());

        if (dto.responsavelId() != null) {
            Usuario resp = usuarioRepository.findById(dto.responsavelId()).orElse(null);
            os.setResponsavel(resp);
        }

        return toResponseDTO(repository.save(os));
    }

    @Transactional
    public OrdemServicoResponseDTO atualizarStatus(UUID id, StatusOS novoStatus) {
        OrdemServico os = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("OS não encontrada"));

        os.setStatus(novoStatus);
        if (novoStatus == StatusOS.FINALIZADO) {
            os.setDataFinalizacao(java.time.LocalDateTime.now());
        }

        return toResponseDTO(repository.save(os));
    }

    private OrdemServicoResponseDTO toResponseDTO(OrdemServico os) {
        return new OrdemServicoResponseDTO(
                os.getId(),
                os.getNumeroOs(),
                os.getDescricaoProblema(),
                os.getStatus(),
                os.getTipoServico(),
                os.getDataAbertura(),
                os.getValorMaoObra(),
                os.getVeiculo().getModelo(),
                os.getVeiculo().getPlaca(),
                os.getResponsavel() != null ? os.getResponsavel().getNome() : "Não atribuído",
                os.getFotosPintura()
        );
    }

    @Transactional(readOnly = true)
    public OrdemServicoResponseDTO buscarPorNumero(Integer numeroOs) {
        return repository.findByNumeroOs(numeroOs)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Ordem de Serviço não encontrada."));
    }

    @Transactional(readOnly = true)
    public List<OrdemServicoResponseDTO> listarComFiltros(StatusOS status, UUID clienteId) {
        if (status != null) {
            return repository.findAllByStatusOrderByDataAberturaDesc(status)
                    .stream().map(this::toResponseDTO).toList();
        }
        return repository.findAll().stream().map(this::toResponseDTO).toList();
    }

    @Transactional(readOnly = true)
    public OrdemServicoDetalhadaDTO buscarDetalhesCompletos(UUID id) {
        OrdemServico os = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordem de Serviço não encontrada"));

        List<OrdemPecaDetalheDTO> itensDto = os.getPecas().stream()
                .map(item -> new OrdemPecaDetalheDTO(
                        item.getPeca().getId(),
                        item.getPeca().getNome(),
                        item.getQuantidade(),
                        item.getPrecoAplicado(),
                        item.getPrecoAplicado().multiply(BigDecimal.valueOf(item.getQuantidade()))
                )).toList();

        BigDecimal totalPecas = itensDto.stream()
                .map(OrdemPecaDetalheDTO::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalGeral = totalPecas.add(os.getValorMaoObra());

        return new OrdemServicoDetalhadaDTO(
                os.getId(),
                os.getNumeroOs(),
                os.getStatus(),
                os.getTipoServico(),
                os.getDataAbertura(),
                os.getDescricaoProblema(),
                os.getLaudoTecnico(),
                os.getVeiculo().getPlaca(),
                os.getVeiculo().getModelo(),
                os.getVeiculo().getCliente().getNome(),
                os.getVeiculo().getCliente().getTelefone(),
                os.getResponsavel() != null ? os.getResponsavel().getNome() : "Não atribuído",
                itensDto,
                os.getValorMaoObra(),
                totalPecas,
                totalGeral,
                os.getFotosPintura()
        );
    }
}
