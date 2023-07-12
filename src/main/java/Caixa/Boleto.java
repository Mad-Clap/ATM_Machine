package Caixa;

public class Boleto implements TransacaoEmConta {
    private String codigoDeBarras;
    private long valor;
    private String dataDeVencimento;
    private String dataDePagamento;
    private long multa;
    private long valorSemMulta;

    public Boleto(String codigoDeBarras, long valor, String dataDeVencimento, String dataDePagamento, long multa, long valorSemMulta) {
        this.codigoDeBarras = codigoDeBarras;
        this.valor = valor;
        this.dataDeVencimento = dataDeVencimento;
        this.dataDePagamento = dataDePagamento;
        this.multa = multa;
        this.valorSemMulta = valorSemMulta;
    }

    @Override
    public long getValor() {
        return valor;
    }

    @Override
    public String getDescricao() {
        return "Boleto";
    }

    @Override
    public String getTipoOperacao() {
        return "Pagamento";
    }

    @Override
    public String getData() {
        return dataDePagamento;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public String getDataDeVencimento() {
        return dataDeVencimento;
    }

    public long getMulta() {return multa; }

    public long getValorSemMulta() {
        return valorSemMulta;
    }

}
