package br.com.solivos.appOficinaVeiculos.dtos;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record OrdemPecaRequestDTO(
        @NotNull UUID pecaId,
        @NotNull @Min(1) Integer quantidade
) {}