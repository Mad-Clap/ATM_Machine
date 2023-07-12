package Caixa;

public class TransferenciaIn implements TransacaoEmConta {
    private String numeroContaEntrada;
    private String dataTransferencia;
    private long valor;
    private String nomeOrigem;
    private String cpfOrigem;

    public TransferenciaIn(String numeroContaEntrada, String dataTransferencia, long valor, String nomeOrigem, String cpfOrigem) {
        this.numeroContaEntrada = numeroContaEntrada;
        this.dataTransferencia = dataTransferencia;
        this.valor = valor;
        this.nomeOrigem = nomeOrigem;
        this.cpfOrigem = cpfOrigem;
    }

    @Override
    public long getValor() {
        return valor;
    }

    @Override
    public String getDescricao() {
        return "Transf.(E) de " + numeroContaEntrada;
    }

    @Override
    public String getTipoOperacao() {
        return "Transferência";
    }

    @Override
    public String getData() {
        return dataTransferencia;
    }

    public String getNomeOrigem() {
        return nomeOrigem;
    }

    public String getCpfOrigem() {
        return cpfOrigem;
    }

    public String getNumeroContaEntrada() {
        return numeroContaEntrada;
    }
}
