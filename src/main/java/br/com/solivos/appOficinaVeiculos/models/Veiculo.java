package br.com.solivos.appOficinaVeiculos.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "veiculos")

@NoArgsConstructor
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

    public Veiculo() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCorCodigo() {
        return corCodigo;
    }

    public void setCorCodigo(String corCodigo) {
        this.corCodigo = corCodigo;
    }

    public String getVinChassi() {
        return vinChassi;
    }

    public void setVinChassi(String vinChassi) {
        this.vinChassi = vinChassi;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}