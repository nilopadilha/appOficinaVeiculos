package br.com.solivos.appOficinaVeiculos.servicos;

import br.com.solivos.appOficinaVeiculos.dtos.OrdemPecaRequestDTO;
import br.com.solivos.appOficinaVeiculos.models.OrdemPeca;
import br.com.solivos.appOficinaVeiculos.models.OrdemServico;
import br.com.solivos.appOficinaVeiculos.models.Peca;
import br.com.solivos.appOficinaVeiculos.repository.OrdemPecaRepository;
import br.com.solivos.appOficinaVeiculos.repository.OrdemServicoRepository;
import br.com.solivos.appOficinaVeiculos.repository.PecaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrdemPecaService {

    private final OrdemPecaRepository ordemPecaRepository;
    private final OrdemServicoRepository osRepository;
    private final PecaRepository pecaRepository;

    public OrdemPecaService(OrdemPecaRepository ordemPecaRepository, 
                            OrdemServicoRepository osRepository, 
                            PecaRepository pecaRepository) {
        this.ordemPecaRepository = ordemPecaRepository;
        this.osRepository = osRepository;
        this.pecaRepository = pecaRepository;
    }

    @Transactional
    public void adicionarPecaAOrdem(UUID osId, OrdemPecaRequestDTO dto) {
        OrdemServico os = osRepository.findById(osId)
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada"));

        Peca peca = pecaRepository.findById(dto.pecaId())
                .orElseThrow(() -> new RuntimeException("Peça não encontrada"));

        if (peca.getQuantidadeEstoque() < dto.quantidade()) {
            throw new RuntimeException("Estoque insuficiente para a peça: " + peca.getNome());
        }

        OrdemPeca item = new OrdemPeca();
        item.setOrdemServico(os);
        item.setPeca(peca);
        item.setQuantidade(dto.quantidade());
        item.setPrecoAplicado(peca.getPrecoUnitario());

        peca.setQuantidadeEstoque(peca.getQuantidadeEstoque() - dto.quantidade());

        pecaRepository.save(peca);
        ordemPecaRepository.save(item);
    }
}
