package br.com.solivos.appOficinaVeiculos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record OrdemServicoRequestDTO(
        @NotNull UUID veiculoId,
        UUID responsavelId,
        @NotBlank String descricaoProblema,
        BigDecimal valorMaoObra,
        String checklistEntrada // JSON enviado como String do Vue
) {}
