package br.com.solivos.appOficinaVeiculos.dtos;

import java.util.UUID;

public record ClienteResponseDTO (
        UUID id,
        String nome,
        String documento,
        String telefone,
        String email,
        Boolean isVip,
        String endereco
) {}