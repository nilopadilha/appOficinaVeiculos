package br.com.solivos.appOficinaVeiculos.models;

import br.com.solivos.appOficinaVeiculos.enumerated.StatusOS;
import br.com.solivos.appOficinaVeiculos.enumerated.TipoServico;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ordens_servico")
@NoArgsConstructor
public class OrdemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "numero_os", insertable = false, updatable = false)
    private Integer numeroOs;

    @Column(nullable = false)
    private String descricaoProblema;

    private String laudoTecnico;
    private BigDecimal valorMaoObra = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private StatusOS status = StatusOS.ORCAMENTO;

    @Enumerated(EnumType.STRING)
    private TipoServico tipoServico = TipoServico.MECANICA;

    private LocalDateTime dataAbertura = LocalDateTime.now();
    private LocalDateTime dataFinalizacao;

    @Column(columnDefinition = "jsonb")
    private String checklistEntrada;

    @Column(columnDefinition = "jsonb")
    private String fotosPintura;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel;

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL)
    private List<OrdemPeca> pecas;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getNumeroOs() {
        return numeroOs;
    }

    public void setNumeroOs(Integer numeroOs) {
        this.numeroOs = numeroOs;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public String getLaudoTecnico() {
        return laudoTecnico;
    }

    public void setLaudoTecnico(String laudoTecnico) {
        this.laudoTecnico = laudoTecnico;
    }

    public BigDecimal getValorMaoObra() {
        return valorMaoObra;
    }

    public void setValorMaoObra(BigDecimal valorMaoObra) {
        this.valorMaoObra = valorMaoObra;
    }

    public StatusOS getStatus() {
        return status;
    }

    public void setStatus(StatusOS status) {
        this.status = status;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(LocalDateTime dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    public String getChecklistEntrada() {
        return checklistEntrada;
    }

    public void setChecklistEntrada(String checklistEntrada) {
        this.checklistEntrada = checklistEntrada;
    }

    public String getFotosPintura() {
        return fotosPintura;
    }

    public void setFotosPintura(String fotosPintura) {
        this.fotosPintura = fotosPintura;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public List<OrdemPeca> getPecas() {
        return pecas;
    }

    public void setPecas(List<OrdemPeca> pecas) {
        this.pecas = pecas;
    }
}