package Caixa;

public class Salario implements TransacaoEmConta{
    private long valor;
    private String dataPagamento;
    private String tipoOperacao;
    private String descricao;

    public Salario(long valor, String dataPagamento, String tipoOperacao, String descricao) {
        this.valor = valor;
        this.dataPagamento = dataPagamento;
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
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
