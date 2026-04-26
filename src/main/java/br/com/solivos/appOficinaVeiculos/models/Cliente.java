package br.com.solivos.appOficinaVeiculos.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(unique = true, nullable = false, length = 14)
    private String documento;

    private String telefone;
    
    private Boolean vip = false;

    @Column(length = 150)
    private String email;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String endereco;

    @OneToMany(mappedBy = "cliente")
    private List<Veiculo> veiculos;

    public Cliente() {}

    public Cliente(UUID id, String nome, String documento, String telefone, Boolean vip, String email, String endereco, List<Veiculo> veiculos) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.telefone = telefone;
        this.vip = vip;
        this.email = email;
        this.endereco = endereco;
        this.veiculos = veiculos;
    }
}
