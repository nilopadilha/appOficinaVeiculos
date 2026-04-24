package br.com.solivos.appOficinaVeiculos.models;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.List;
import java.util.UUID;

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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String endereco;

    @OneToMany(mappedBy = "cliente")
    private List<Veiculo> veiculos;

    public Cliente() {}

    public Cliente(UUID id, String nome, String documento, String telefone, Boolean vip, String endereco) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.telefone = telefone;
        this.vip = vip;
        this.endereco = endereco;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Boolean getVip() { return vip; }
    public void setVip(Boolean vip) { this.vip = vip; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public List<Veiculo> getVeiculos() { return veiculos; }
    public void setVeiculos(List<Veiculo> veiculos) { this.veiculos = veiculos; }
}
