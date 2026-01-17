package br.com.solivos.appOficinaVeiculos.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

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
    private BigDecimal precoAplicado; // Mantém o preço da peça no momento da venda
}
