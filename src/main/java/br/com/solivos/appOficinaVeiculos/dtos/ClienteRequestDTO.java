package br.com.solivos.appOficinaVeiculos.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100)
        String nome,

        @NotBlank(message = "O documento (CPF/CNPJ) é obrigatório")
        @Size(max = 14)
        String documento,

        String telefone,

        @Email(message = "O e-mail deve ser válido")
        @Size(max = 150)
        String email,

        Boolean isVip,
        String endereco // Recebe o JSON como String ou objeto mapeado
) {}