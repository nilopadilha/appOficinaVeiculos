package br.com.solivos.appOficinaVeiculos.enumerated;

public enum StatusOS {
    ORCAMENTO(1, "Orçamento"),
    APROVADO(2, "Aprovado"),
    MECANICA(3, "Em Manutenção Mecânica"),
    PINTURA(4, "Em Pintura/Funilaria"),
    FINALIZADO(5, "Finalizado/Pronto"),
    CANCELADO(6, "Cancelado");

    private final int codigo;
    private final String rotulo;

    public int getCodigo() {
        return codigo;
    }

    public String getRotulo() {
        return rotulo;
    }

    StatusOS(int codigo, String rotulo) {
        this.codigo = codigo;
        this.rotulo = rotulo;
    }

    // Método útil para quando você receber o ID do frontend
    public static StatusOS fromCodigo(int codigo) {
        for (StatusOS status : StatusOS.values()) {
            if (status.getCodigo() == codigo) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de Status OS inválido: " + codigo);
    }
}