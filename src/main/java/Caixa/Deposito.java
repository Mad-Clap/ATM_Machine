package Caixa;

public class Deposito implements TransacaoEmConta {
    private long valor;
    private String dataDeposito;

    public Deposito(long valor, String dataTransferencia) {
        this.valor = valor;
        this.dataDeposito = dataTransferencia;
    }

    @Override
    public long getValor() {
        return valor;
    }

    @Override
    public String getDescricao() {
        return "Depósito na conta";
    }

    @Override
    public String getTipoOperacao() {
        return "Depósito";
    }

    @Override
    public String getData() {
        return dataDeposito;
    }
}
