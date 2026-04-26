package br.com.solivos.appOficinaVeiculos.dtos;

import br.com.solivos.appOficinaVeiculos.enumerated.MetodoPagamento;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PagamentoRequestDTO(@NotNull(message = "O ID da Ordem de Serviço é obrigatório")
                                  UUID osId,
                                  @NotNull(message = "O método de pagamento deve ser informado")
                                  MetodoPagamento metodo
) {}