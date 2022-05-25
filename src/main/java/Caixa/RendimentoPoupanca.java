package Caixa;

public class RendimentoPoupanca implements TransacaoEmConta{
    private long valor;
    private String dataRendimento;
    private String tipoOperacao;
    private String descricao;

    public RendimentoPoupanca(long valor, String dataRendimento, String tipoOperacao, String descricao) {
        this.valor = valor;
        this.dataRendimento = dataRendimento;
        this.tipoOperacao = tipoOperacao;
        this.descricao = descricao;
    }

    @Override
    public long getValor() {
        return valor;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public String getTipoOperacao() {
        return tipoOperacao;
    }

    @Override
    public String getData() {
        return dataRendimento;
    }
}
