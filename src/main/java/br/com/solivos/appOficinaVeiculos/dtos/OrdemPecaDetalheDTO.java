package br.com.solivos.appOficinaVeiculos.dtos;
import java.math.BigDecimal;
import java.util.UUID;

public record OrdemPecaDetalheDTO(
        UUID pecaId,
        String nomePeca,
        Integer quantidade,
        BigDecimal precoAplicado,
        BigDecimal subtotal // Quantidade * PrecoAplicado
) {}