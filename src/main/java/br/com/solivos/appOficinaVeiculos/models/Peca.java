package br.com.solivos.appOficinaVeiculos.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "pecas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Peca {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(unique = true)
    private String sku;

    @Column(nullable = false)
    private BigDecimal precoUnitario;

    @Column(nullable = false)
    private Integer quantidadeEstoque = 0;

    private Integer estoqueMinimo = 5;
}
