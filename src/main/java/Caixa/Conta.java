package Caixa;


import java.util.ArrayList;

public abstract class Conta {
    protected String nome;
    protected String cpf;
    protected String dataDeNascimento;
    protected String email;
    protected String telefone;
    protected int senha;
    protected String numeroDaConta;
    protected String agencia = "0001";
    protected long capital =0;
    ArrayList<TransacaoEmConta> extrato = new ArrayList<TransacaoEmConta>();
    protected String PIX=null;

    protected boolean contaSalario = false;
    protected int diaPagamento = 0;
    protected long salario = 0;


    public Conta(String nome, String cpf, String dataDeNascimento, String email, String telefone, int senha, String numeroDaConta) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataDeNascimento = dataDeNascimento;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.numeroDaConta = numeroDaConta;
    }


    public void setCapital(long dinheiro) {
        this.capital = capital+dinheiro;

    }

    public void setPIX(String PIX) {
        this.PIX = PIX;
    }
    public boolean isNullPix(){return PIX==null;}

    public String randomPix(){return PIX;}
}
