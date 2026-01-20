package br.com.solivos.appOficinaVeiculos.servicos;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdemServicoService {

    private  OrdemServicoRepository repository;
    private  VeiculoRepository veiculoRepository;
    private  UsuarioRepository usuarioRepository;

    @Transactional
    public OrdemServicoResponseDTO abrirOrdem(OrdemServicoRequestDTO dto) {
        Veiculo veiculo = veiculoRepository.findById(dto.veiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        OrdemServico os = new OrdemServico();
        os.setVeiculo(veiculo);
        os.setDescricaoProblema(dto.descricaoProblema());
        os.setValorMaoObra(dto.valorMaoObra() != null ? dto.valorMaoObra() : java.math.BigDecimal.ZERO);
        os.setStatus(StatusOS.ORCAMENTO); // Status inicial padrão
        os.setChecklistEntrada(dto.checklistEntrada());

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
                os.getDataAbertura(),
                os.getValorMaoObra(),
                os.getVeiculo().getModelo(),
                os.getVeiculo().getPlaca(),
                os.getResponsavel() != null ? os.getResponsavel().getNome() : "Não atribuído"
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
}