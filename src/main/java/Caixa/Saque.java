package Caixa;

public class Saque implements TransacaoEmConta {
    private String dataSaque;
    private long valor;
    public Saque(String dataSaque, long valor) {
        this.dataSaque = dataSaque;
        this.valor = valor;
    }

    @Override
    public long getValor() {
        return valor;
    }

    @Override
    public String getDescricao() {
        return "Saque";
    }

    @Override
    public String getTipoOperacao() {
        return "Saque";
    }

    @Override
    public String getData() {
        return dataSaque;
    }
}
