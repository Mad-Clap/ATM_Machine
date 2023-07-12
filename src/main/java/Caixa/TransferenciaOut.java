package Caixa;

public class TransferenciaOut implements TransacaoEmConta {
    private String numeroContaSaida;
    private String dataTransferencia;
    private long valor;
    private String nomeDestino;
    private String cpfDestino;

    public TransferenciaOut(String numeroContaEntrada, String dataTransferencia, long valor, String nomeDestino, String cpfDestino) {
        this.numeroContaSaida = numeroContaEntrada;
        this.dataTransferencia = dataTransferencia;
        this.valor = valor;
        this.nomeDestino = nomeDestino;
        this.cpfDestino = cpfDestino;
    }

    @Override
    public long getValor() {
        return valor;
    }

    @Override
    public String getDescricao() {
        return "Transf.(S) para " + numeroContaSaida;
    }

    @Override
    public String getTipoOperacao() { return "Transferência"; }

    @Override
    public String getData() {
        return dataTransferencia;
    }

    public String getNumeroContaSaida() {
        return numeroContaSaida;
    }

    public String getNomeDestino() {
        return nomeDestino;
    }

    public String getCpfDestino() {
        return cpfDestino;
    }
}
