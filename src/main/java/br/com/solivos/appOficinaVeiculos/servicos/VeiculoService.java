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
@RequiredArgsConstructor
public class VeiculoService {

    private VeiculoRepository veiculoRepository;
    private ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public List<VeiculoResponseDTO> listarTodos() {
        return veiculoRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    @Transactional
    public VeiculoResponseDTO criar(VeiculoRequestDTO dto) {
        if (veiculoRepository.existsByPlaca(dto.placa())) {
            throw new RuntimeException("Veículo com esta placa já cadastrado.");
        }

        // Busca o cliente para garantir que a associação existe
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado para o ID fornecido."));

        Veiculo veiculo = new Veiculo();
        updateEntityFromDto(veiculo, dto);
        veiculo.setCliente(cliente); // Associação direta da entidade

        return toResponseDTO(veiculoRepository.save(veiculo));
    }

    @Transactional
    public VeiculoResponseDTO atualizar(UUID id, VeiculoRequestDTO dto) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        if (!veiculo.getPlaca().equals(dto.placa()) && veiculoRepository.existsByPlaca(dto.placa())) {
            throw new RuntimeException("Nova placa já pertence a outro veículo.");
        }

        updateEntityFromDto(veiculo, dto);

        // Permite trocar o dono do veículo se necessário
        if (!veiculo.getCliente().getId().equals(dto.clienteId())) {
            Cliente novoCliente = clienteRepository.findById(dto.clienteId())
                    .orElseThrow(() -> new RuntimeException("Novo cliente não encontrado."));
            veiculo.setCliente(novoCliente);
        }

        return toResponseDTO(veiculoRepository.save(veiculo));
    }

    private void updateEntityFromDto(Veiculo veiculo, VeiculoRequestDTO dto) {
        veiculo.setModelo(dto.modelo());
        veiculo.setMarca(dto.marca());
        veiculo.setAno(dto.ano());
        veiculo.setPlaca(dto.placa());
        veiculo.setCorCodigo(dto.corCodigo());
        veiculo.setVinChassi(dto.vinChassi());
    }

    private VeiculoResponseDTO toResponseDTO(Veiculo v) {
        return new VeiculoResponseDTO(
                v.getId(), v.getModelo(), v.getMarca(), v.getAno(),
                v.getPlaca(), v.getCorCodigo(), v.getVinChassi(),
                v.getCliente().getId(), v.getCliente().getNome()
        );
    }
}