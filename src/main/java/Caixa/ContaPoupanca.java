package Caixa;

import java.util.Calendar;
import java.util.TimeZone;

public class ContaPoupanca extends Conta {
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    int day = calendar.get(Calendar.DATE);
    int month = calendar.get(Calendar.MONTH) + 1;
    int year = calendar.get(Calendar.YEAR);
    boolean contaSalario =false;

    public ContaPoupanca(String nome, String cpf, String dataDeNascimento, String email, String telefone, int senha, String numeroDaConta) {
        super(nome, cpf, dataDeNascimento, email, telefone, senha, numeroDaConta);
    }
}
