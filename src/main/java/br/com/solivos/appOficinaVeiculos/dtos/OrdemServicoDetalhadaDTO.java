package br.com.solivos.appOficinaVeiculos.dtos;

import br.com.solivos.appOficinaVeiculos.enumerated.StatusOS;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrdemServicoDetalhadaDTO(
        UUID id,
        Integer numeroOs,
        StatusOS status,
        LocalDateTime dataAbertura,
        String descricaoProblema,
        String laudoTecnico,

        // Dados do Veículo e Cliente
        String placaVeiculo,
        String modeloVeiculo,
        String nomeCliente,
        String telefoneCliente,

        // Dados do Responsável
        String nomeResponsavel,

        // Itens e Financeiro
        List<OrdemPecaDetalheDTO> itens,
        BigDecimal valorMaoObra,
        BigDecimal valorTotalPecas,
        BigDecimal valorTotalGeral
) {}