package br.com.solivos.appOficinaVeiculos.enumerated;

public enum MetodoPagamento {
    DINHEIRO(1, "Dinheiro"),
    DEBITO(2, "Cartão de Débito"),
    CREDITO(3, "Cartão de Crédito"),
    PIX(4, "PIX"),
    BOLETO(5, "Boleto");

    private final int codigo;
    private final String rotulo;

    public int getCodigo() {
        return codigo;
    }

    public String getRotulo() {
        return rotulo;
    }

    MetodoPagamento(int codigo, String rotulo) {
        this.codigo = codigo;
        this.rotulo = rotulo;
    }
}
