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
@RequiredArgsConstructor
public class OrdemPecaService {

    private OrdemPecaRepository ordemPecaRepository;
    private OrdemServicoRepository osRepository;
    private PecaRepository pecaRepository;

    @Transactional
    public void adicionarPecaAOrdem(UUID osId, OrdemPecaRequestDTO dto) {
        // 1. Validar a existência da OS
        OrdemServico os = osRepository.findById(osId)
                .orElseThrow(() -> new RuntimeException("Ordem de Serviço não encontrada"));

        // 2. Validar e buscar a peça
        Peca peca = pecaRepository.findById(dto.pecaId())
                .orElseThrow(() -> new RuntimeException("Peça não encontrada no catálogo"));

        // 3. Validar estoque
        if (peca.getQuantidadeEstoque() < dto.quantidade()) {
            throw new RuntimeException("Estoque insuficiente para a peça: " + peca.getNome());
        }

        // 4. Criar o vínculo e congelar o preço
        OrdemPeca item = new OrdemPeca();
        item.setOrdemServico(os);
        item.setPeca(peca);
        item.setQuantidade(dto.quantidade());
        item.setPrecoAplicado(peca.getPrecoUnitario()); // Garante o histórico financeiro

        // 5. Baixar estoque
        peca.setQuantidadeEstoque(peca.getQuantidadeEstoque() - dto.quantidade());

        pecaRepository.save(peca);
        ordemPecaRepository.save(item);
    }
}