package Caixa;


public class ContaCorrente extends Conta {
    long chequeEspecial = 300000;

    public ContaCorrente(String nome, String cpf, String dataDeNascimento, String email, String telefone, int senha, String numeroDaConta) {
        super(nome, cpf, dataDeNascimento, email, telefone, senha, numeroDaConta);

    }

}
