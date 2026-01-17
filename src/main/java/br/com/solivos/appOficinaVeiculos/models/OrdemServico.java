package br.com.solivos.appOficinaVeiculos.models;

import br.com.solivos.appOficinaVeiculos.enumerated.StatusOS;
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

    @Enumerated(EnumType.STRING) // Mapeia o nome do Enum para o tipo status_os do banco
    private StatusOS status = StatusOS.ORCAMENTO;

    private LocalDateTime dataAbertura = LocalDateTime.now();
    private LocalDateTime dataFinalizacao;

    @Column(columnDefinition = "jsonb")
    private String checklistEntrada;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel;

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL)
    private List<OrdemPeca> pecas;
}