package br.com.solivos.appOficinaVeiculos.servicos;

import br.com.solivos.appOficinaVeiculos.dtos.ClienteRequestDTO;
import br.com.solivos.appOficinaVeiculos.dtos.ClienteResponseDTO;
import br.com.solivos.appOficinaVeiculos.models.Cliente;
import br.com.solivos.appOficinaVeiculos.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        if (repository.findByDocumento(dto.documento())) {
            throw new RuntimeException("Documento já cadastrado no sistema.");
        }
        Cliente cliente = new Cliente();
        updateEntityFromDto(cliente, dto);
        return toResponseDTO(repository.save(cliente));
    }

    @Transactional
    public ClienteResponseDTO atualizar(UUID id, ClienteRequestDTO dto) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        updateEntityFromDto(cliente, dto);
        return toResponseDTO(repository.save(cliente));
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }

    private void updateEntityFromDto(Cliente cliente, ClienteRequestDTO dto) {
        cliente.setNome(dto.nome());
        cliente.setDocumento(dto.documento());
        cliente.setTelefone(dto.telefone());
        cliente.setVip(dto.isVip() != null ? dto.isVip() : false);
        cliente.setEndereco(dto.endereco());
    }

    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getDocumento(),
                cliente.getTelefone(),
                cliente.getVip(),
                cliente.getEndereco()
        );
    }
}