package br.com.solivos.appOficinaVeiculos.servicos;

import br.com.solivos.appOficinaVeiculos.dtos.VeiculoRequestDTO;
import br.com.solivos.appOficinaVeiculos.dtos.VeiculoResponseDTO;
import br.com.solivos.appOficinaVeiculos.models.Cliente;
import br.com.solivos.appOficinaVeiculos.models.Veiculo;
import br.com.solivos.appOficinaVeiculos.repository.ClienteRepository;
import br.com.solivos.appOficinaVeiculos.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ClienteRepository clienteRepository;

    public VeiculoService(VeiculoRepository veiculoRepository, ClienteRepository clienteRepository) {
        this.veiculoRepository = veiculoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<VeiculoResponseDTO> listarTodos() {
        return veiculoRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    @Transactional
    public VeiculoResponseDTO criar(VeiculoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Veiculo v = new Veiculo();
        v.setModelo(dto.modelo());
        v.setMarca(dto.marca());
        v.setAno(dto.ano());
        v.setPlaca(dto.placa());
        v.setCorCodigo(dto.corCodigo());
        v.setVinChassi(dto.vinChassi());
        v.setCliente(cliente);

        return toResponseDTO(veiculoRepository.save(v));
    }

    @Transactional
    public VeiculoResponseDTO atualizar(UUID id, VeiculoRequestDTO dto) {
        Veiculo v = veiculoRepository.findById(id).orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        v.setModelo(dto.modelo());
        v.setMarca(dto.marca());
        v.setAno(dto.ano());
        v.setCorCodigo(dto.corCodigo());
        return toResponseDTO(veiculoRepository.save(v));
    }

    private VeiculoResponseDTO toResponseDTO(Veiculo v) {
        return new VeiculoResponseDTO(
                v.getId(), v.getModelo(), v.getMarca(), v.getAno(),
                v.getPlaca(), v.getCorCodigo(), v.getVinChassi(),
                v.getCliente().getId(), v.getCliente().getNome()
        );
    }
}
