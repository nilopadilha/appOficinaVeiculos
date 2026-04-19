package br.com.solivos.appOficinaVeiculos.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(unique = true, nullable = false, length = 14)
    private String documento;

    private String telefone;
    private Boolean isVip = false;

    @Column(columnDefinition = "jsonb")
    private String endereco;

    @OneToMany(mappedBy = "cliente")
    private List<Veiculo> veiculos;
}
