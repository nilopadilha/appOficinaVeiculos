package br.com.solivos.appOficinaVeiculos.dtos;

import br.com.solivos.appOficinaVeiculos.enumerated.MetodoPagamento;
import br.com.solivos.appOficinaVeiculos.enumerated.StatusPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PagamentoResponseDTO(UUID id,
                                   UUID osId,
                                   BigDecimal valorTotal,
                                   MetodoPagamento metodo,
                                   StatusPagamento status,
                                   LocalDateTime dataPagamento
) {}