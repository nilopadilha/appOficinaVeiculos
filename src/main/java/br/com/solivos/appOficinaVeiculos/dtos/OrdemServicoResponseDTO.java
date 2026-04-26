package br.com.solivos.appOficinaVeiculos.dtos;

import br.com.solivos.appOficinaVeiculos.enumerated.StatusOS;
import br.com.solivos.appOficinaVeiculos.enumerated.TipoServico;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrdemServicoResponseDTO(UUID id,
                                      Integer numeroOs,
                                      String descricaoProblema,
                                      StatusOS status,
                                      TipoServico tipoServico,
                                      LocalDateTime dataAbertura,
                                      BigDecimal valorMaoObra,
                                      String modeloVeiculo,
                                      String placaVeiculo,
                                      String nomeResponsavel,
                                      String fotosPintura
) {
}
