package br.com.solivos.appOficinaVeiculos.enumerated;

public enum StatusPagamento {
    PENDENTE(1, "Pendente"),
    PAGO(2, "Pago"),
    CANCELADO(3, "Cancelado");

    private final int codigo;
    private final String rotulo;

    public int getCodigo() {
        return codigo;
    }

    public String getRotulo() {
        return rotulo;
    }

    StatusPagamento(int codigo, String rotulo) {
        this.codigo = codigo;
        this.rotulo = rotulo;
    }
}
