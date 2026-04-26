package br.com.solivos.appOficinaVeiculos.enumerated;
import lombok.Getter;

@Getter
public enum Role {
    ADMIN("Administrador"),
    MECANICO("Mecânico");

    private final String descricao;

    Role(String descricao) {
        this.descricao = descricao;
    }
}