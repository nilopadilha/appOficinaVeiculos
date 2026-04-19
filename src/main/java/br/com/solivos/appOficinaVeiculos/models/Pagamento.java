package br.com.solivos.appOficinaVeiculos.models;

import br.com.solivos.appOficinaVeiculos.enumerated.MetodoPagamento;
import br.com.solivos.appOficinaVeiculos.enumerated.StatusPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "os_id")
    private OrdemServico ordemServico;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodo;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status = StatusPagamento.PENDENTE;

    private LocalDateTime dataPagamento;
}
