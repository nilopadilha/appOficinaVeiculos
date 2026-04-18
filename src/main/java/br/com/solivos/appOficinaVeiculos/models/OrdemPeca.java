package br.com.solivos.appOficinaVeiculos.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;
@Entity
@Table(name= "ordem_pecas")
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoAplicado() {
        return precoAplicado;
    }

    public void setPrecoAplicado(BigDecimal precoAplicado) {
        this.precoAplicado = precoAplicado;
    }
}
