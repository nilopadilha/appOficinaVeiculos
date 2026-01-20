package br.com.solivos.appOficinaVeiculos.dtos;

public record LoginResponseDTO(
        String token,
        String email,
        String role
) {
}