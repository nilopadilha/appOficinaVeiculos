package br.com.solivos.appOficinaVeiculos.dtos;

import java.util.UUID;

public record VeiculoResponseDTO(
        UUID id,
        String modelo,
        String marca,
        String ano,
        String placa,
        String corCodigo,
        String vinChassi,
        UUID clienteId,
        String nomeCliente
) {
}