package br.com.solivos.appOficinaVeiculos.dtos;

import java.util.UUID;

public record ClienteResponseDTO (
        UUID id,
        String nome,
        String documento,
        String telefone,
        Boolean isVip,
        String endereco
) {}