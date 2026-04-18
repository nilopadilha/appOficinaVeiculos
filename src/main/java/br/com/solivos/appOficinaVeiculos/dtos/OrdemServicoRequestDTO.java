package br.com.solivos.appOficinaVeiculos.dtos;

import br.com.solivos.appOficinaVeiculos.enumerated.TipoServico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record OrdemServicoRequestDTO(
        @NotNull UUID veiculoId,
        UUID responsavelId,
        @NotBlank String descricaoProblema,
        BigDecimal valorMaoObra,
        String checklistEntrada, // JSON enviado como String do Vue
        TipoServico tipoServico,
        String fotosPintura // JSON contendo URLs ou metadados das fotos
) {}
