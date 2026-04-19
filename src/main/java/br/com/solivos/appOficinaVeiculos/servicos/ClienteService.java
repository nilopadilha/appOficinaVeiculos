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
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toResponseDTO).toList();
    }

    @Transactional
    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setDocumento(dto.documento());
        cliente.setTelefone(dto.telefone());
        cliente.setVip(dto.isVip());
        cliente.setEndereco(dto.endereco());
        return toResponseDTO(repository.save(cliente));
    }

    @Transactional
    public ClienteResponseDTO atualizar(UUID id, ClienteRequestDTO dto) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());
        cliente.setVip(dto.isVip());
        cliente.setEndereco(dto.endereco());
        return toResponseDTO(repository.save(cliente));
    }

    @Transactional
    public void deletar(UUID id) {
        repository.deleteById(id);
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
