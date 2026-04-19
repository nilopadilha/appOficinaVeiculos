package br.com.solivos.appOficinaVeiculos.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name= "ordem_pecas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdemPeca {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "os_id")
    private OrdemServico ordemServico;

    @ManyToOne
    @JoinColumn(name = "peca_id")
    private Peca peca;

    private Integer quantidade;

    @Column(name = "preco_aplicado")
    private BigDecimal precoAplicado;
}
