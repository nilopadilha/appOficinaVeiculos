package br.com.solivos.appOficinaVeiculos.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record VeiculoRequestDTO(
        @NotBlank @Size(max = 100) String modelo,
        @NotBlank @Size(max = 50) String marca,
        @NotBlank @Size(max = 4) String ano,
        @NotBlank @Size(max = 10) String placa,
        String corCodigo,
        @Size(max = 17) String vinChassi,
        @NotNull(message = "O ID do cliente é obrigatório para associar o veículo")
        UUID clienteId
) {}