package br.com.solivos.appOficinaVeiculos.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "veiculos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String modelo;
    private String marca;
    private String ano;

    @Column(unique = true, nullable = false, length = 10)
    private String placa;

    private String corCodigo;
    private String vinChassi;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
