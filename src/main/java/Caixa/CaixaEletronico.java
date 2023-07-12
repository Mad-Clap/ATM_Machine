package Caixa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.TimeZone;

public class CaixaEletronico {
    private ArrayList <Conta> contasDoCaixa = new ArrayList<Conta>();
    private ArrayList <String> chaves = new ArrayList<String>();
    private final Scanner sc = new Scanner(System.in);

    private

    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    private int day = calendar.get(Calendar.DATE);
    private int month = calendar.get(Calendar.MONTH) + 1;
    private int year = calendar.get(Calendar.YEAR);

    public int varrerArray(String numeroDaConta){
        int index = -1;
        for(int i=0; i< contasDoCaixa.size(); i++ ){
            if(contasDoCaixa.get(i).numeroDaConta.equals(numeroDaConta)){
                index = i;
                break;
            }
        }

        return index;
    }

    public long formatoDinheiro(){
        System.out.println("Digite o valor (d.dd ou d,dd)");
        String dinheiro = sc.nextLine();
        if(!dinheiro.matches("(^\\d+(,[.])*\\d{2}$)")){
            System.out.println("Digitos inválidos.\n");
            return 0;
        }
        dinheiro = dinheiro.replaceAll("[,.]", "");

        long dinheiroLong = Integer.parseInt(dinheiro);
        if(dinheiroLong <=0)System.out.println("Entrada inválida");
        return dinheiroLong;

    }
    public String formatoData(){
       String data = sc.nextLine();
        if(!data.matches("(^\\d{2}/\\d{2}/\\d{4}$)")){
            System.out.println("Formato de data inválido\n");
            return null;
        }
        return data;
    }

    public void acessarConta(){
        System.out.println("\nNúmero da conta:");
        String numConta = sc.nextLine();

        int index = varrerArray(numConta);
        if(index == -1){
            System.out.println("Essa conta não existe\n");
            return;
        }
        System.out.println("Digite a senha da conta: ");
        int senha=-1;
        if(sc.hasNextInt()){senha = sc.nextInt();}
        sc.nextLine();
        if(senha != contasDoCaixa.get(index).senha){System.out.println("Senha incorreta\n"); return;}


        int menu = -1;
        while (menu!=0){
            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println("                                       Conta " + contasDoCaixa.get(index).numeroDaConta);
            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println("(1) Mostrar saldo      (2) Depósito              (3) Saque                (4) Vincular Salário");
            System.out.println("(5) Pagar boleto       (6) Mostrar Extrato       (7) Configurar Pix       (8) Realizar transferencia");
            System.out.println("(0) Encerrar sessão");
            if (sc.hasNextInt()) {
                menu = sc.nextInt();
            }
            sc.nextLine();


            switch (menu) {
                case 1 -> saldo(index);
                case 2 -> depósito(index);
                case 3 -> saque(index);
                case 4 -> vincularSalario(index);
                case 5 -> pagarBoleto(index);
                case 6 -> imprimirExtrato(index);
                case 7 -> configurarPIX(index);
                case 8 -> transferenciaContas(index);
            }

        }

    }

    public void abrirConta(){
        System.out.println("Digite seu CPF:");
        String cpf = sc.nextLine();
        if(!cpf.matches("(^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$)")){
            System.out.println("Formato de CPF inválido\n");
            return;
        }
        cpf = cpf.replaceAll("[-.]", "");
        String nome = null, dataDeNascimento = null, email = null, telefone = null;

        boolean existente = false;
        Conta mirror = null;
        for(Conta n : contasDoCaixa){
            if(n.cpf.equals(cpf)){
                nome = n.nome;
                dataDeNascimento = n.dataDeNascimento;
                email = n.email;
                telefone = n.telefone;
                existente = true;
                mirror = n;
                break;
            }
        }
        if(!existente){
            System.out.println("Digite seu nome completo:");
            nome = sc.nextLine();
            System.out.println("Digite sua data de nascimento: (dd/mm/aaaa)");
            dataDeNascimento = formatoData();
            if(dataDeNascimento==null) return;


            int diaVencimento = Integer.parseInt(dataDeNascimento.substring(0,2));
            int mesVencimento = Integer.parseInt(dataDeNascimento.substring(3,5));
            int anoVencimento = Integer.parseInt(dataDeNascimento.substring(6,10));
            if(dataErrada(diaVencimento,mesVencimento,anoVencimento)){System.out.println("Essa data não existe\n");return;}

            System.out.println("Digite seu e-mail:");
            email = sc.nextLine();
            if(!email.matches("(.+@.+\\.com\\.?.*$)")){
                System.out.println("E-mail inválido.");
                return;
            }
            System.out.println("Digite seu telefone:");
            telefone = sc.nextLine();
            if(!telefone.matches("([0-9]?[0-9]{4}-?[0-9]{4})")){
                System.out.println("Telefone inválido.");
                return;
            }
        }

        if(existente) {System.out.println("Bem vindo "+ nome+".\nVamos criar sua nova conta:");}

        System.out.println("Que tipo de conta você quer criar?\n(1) Conta-corrente      (2) Conta-poupança");
        int escolha=-1;
        do{
            if(sc.hasNextInt()){escolha = sc.nextInt();}
            sc.nextLine();
            if(mirror!=null){
                if(mirror instanceof ContaCorrente && escolha==1) {System.out.println("Você não pode criar duas contas do mesmo tipo\n");return;}
                if(mirror instanceof ContaPoupanca && escolha==2) {System.out.println("Você não pode criar duas contas do mesmo tipo\n");return;}
            }

            if(escolha != 1 && escolha !=2){System.out.println("Número inválido, tente novamente;\nDigite (1) para Conta-corrente  ou (2) para Conta-poupança");}
        }while(escolha != 1 && escolha !=2);

        int senha =-1;
        do{
            System.out.println("Crie uma senha não octal de 4 dígitos para sua conta:");
            if(sc.hasNextInt()){senha = sc.nextInt();}
            sc.nextLine();
        }while (senha<1000);

        String numeroDaConta = null;

        boolean numeroExistente = true;
        while(numeroExistente) {
            numeroExistente = false;
            long x = (long) (1 + (Math.random() * 9999));
            int y = 1 + (int) (Math.random() * 9);
            numeroDaConta = x + "-" + y;
            for (Conta n : contasDoCaixa) {
                if (n.numeroDaConta.equals(numeroDaConta)) {
                    numeroExistente = true; break;
                }
            }
        }
        String tipoConta;
        if(escolha == 1){
            contasDoCaixa.add(new ContaCorrente(nome, cpf, dataDeNascimento, email, telefone, senha, numeroDaConta));
            tipoConta="corrente";
        }else{
            contasDoCaixa.add(new ContaPoupanca(nome, cpf, dataDeNascimento, email, telefone, senha, numeroDaConta));
            tipoConta="poupança";

        }

        System.out.println("Você criou a conta "+tipoConta+" de número "+numeroDaConta+" com sucesso\n");

    }

    public void saldo(int index){
        long valor = contasDoCaixa.get(index).capital;
        long inteiro = valor/100;
        long decimal = valor%100;
        if(valor<1) decimal*=-1;
        System.out.printf("Saldo da conta: R$ %d,%02d\n\n", inteiro, decimal);
    }

    public void depósito(int index){

        long depósito = formatoDinheiro();
        if(depósito <=0) return;
        contasDoCaixa.get(index).setCapital(depósito);

        String data = printarData();
        contasDoCaixa.get(index).extrato.add(new Deposito(depósito, data));
        System.out.println("Depósito realizado com sucesso.\n");
    }

    public void saque(int index){

        long saque = formatoDinheiro();
        if(saque<=0) return;

        if(saque > contasDoCaixa.get(index).capital){

            if(contasDoCaixa.get(index) instanceof ContaCorrente){
                System.out.println("Você pode usar até R$ 3.000 do cheque especial. Digite o valor que deseja usar do cheque ou 0.00, " +
                        "caso não deseje entrar no cheque especial");
                long cheque;
                do{
                    cheque = formatoDinheiro();
                    if(cheque==0) return;

                    if(cheque<0){
                        System.out.println("Entrada inválida.É necessário uma resposta válida.");
                    }

                }while(cheque<=0);

                if((((ContaCorrente) contasDoCaixa.get(index)).chequeEspecial) < cheque){System.out.println("Você não tem mais essa quantia" +
                        " no cheque especial disponível\n"); return;}

                (((ContaCorrente) contasDoCaixa.get(index)).chequeEspecial) -= cheque;
                saque = contasDoCaixa.get(index).capital+cheque;
                System.out.println("Você usou seu cheque especial");

            }else {System.out.println("Você não tem este dinheiro disponível para saque\n"); return;}
        }

        saque*=-1;

        contasDoCaixa.get(index).setCapital(saque);

        String data = printarData();
        contasDoCaixa.get(index).extrato.add(new Saque(data, saque));
        System.out.println("Saque realizado com sucesso.\n");
    }

    public void imprimirExtrato(int indexMain) {
        ArrayList<TransacaoEmConta> imprimir = contasDoCaixa.get(indexMain).extrato;

        System.out.println("---------------------------------------------------------------------");
        System.out.println("                    Extrato da conta " + contasDoCaixa.get(indexMain).numeroDaConta);
        System.out.println("---------------------------------------------------------------------");
        System.out.println("N. ITEM   DATA       T. OPERAÇÃO   DESCRIÇÃO              VALOR (R$)");
        for (int i = 0; i < imprimir.size(); i++) {
            String data = imprimir.get(i).getData();
            String tipoOperacao = imprimir.get(i).getTipoOperacao();
            String descricao = imprimir.get(i).getDescricao();
            long valor = imprimir.get(i).getValor();

            long inteiro = valor/100;
            long decimal = valor%100;
            if(valor<1) decimal*=-1;

            System.out.printf("%04d      %-10s %-13s %-22s %d,%02d\n", i+1, data, tipoOperacao, descricao, inteiro, decimal);
        }
        System.out.println("---------------------------------------------------------------------");
        saldo(indexMain);
        boolean finaliza = false;
        while (!finaliza) {
            System.out.println("Deseja selecionar algum item do extrato para ver os detalhes? (Sim/Não)");
            String opcao = sc.nextLine();

            if (opcao.equals("Sim") || opcao.equals("sim"))
            {
                int index = -1;
                System.out.println("Qual o número do item do extrato?");
                if(sc.hasNextInt()){index = sc.nextInt();}
                sc.nextLine();
                if(index == -1) {System.out.println("Entrada inválida!");return;}
                index--;

                int limite = imprimir.size() - 1;

                if (index >= 0 && index <= limite) {
                    long valor2 = imprimir.get(index).getValor() * -1;

                    long inteiro2 = valor2/100;
                    long decimal2 = valor2%100;
                    if(valor2<1) decimal2*=-1;

                    if (imprimir.get(index) instanceof Boleto) {
                        long multa = ((Boleto) imprimir.get(index)).getMulta();

                        long inteiroMulta = multa/100;
                        long decimalMulta = multa%100;
                        if(multa<1) decimalMulta*=-1;

                        long valorSemMulta = ((Boleto) imprimir.get(index)).getValorSemMulta();

                        long inteiroSemMulta = valorSemMulta/100;
                        long decimalSemMulta = valorSemMulta%100;
                        if(valorSemMulta<1) decimalSemMulta*=-1;

                        System.out.println("** Detalhes do Boleto **");
                        System.out.printf("Código de barras: %s\n", ((Boleto) imprimir.get(index)).getCodigoDeBarras());
                        System.out.printf("Data de vencimento: %s\n", ((Boleto) imprimir.get(index)).getDataDeVencimento());
                        System.out.printf("Valor: R$ %d,%02d\n", inteiroSemMulta, decimalSemMulta);
                        System.out.printf("Multa: R$ %d,%02d\n", inteiroMulta, decimalMulta);
                        System.out.printf("Total pago: R$ %d,%02d\n\n", inteiro2, decimal2);
                    }
                    else if (imprimir.get(index) instanceof Deposito) {
                        System.out.println("Todas as informações desse depósito já estão no extrato!\n");
                    }
                    else if (imprimir.get(index) instanceof Saque) {
                        System.out.println("Todas as informações desse saque já estão no extrato!\n");
                    }
                    else if (imprimir.get(index) instanceof TransferenciaIn) {
                        System.out.println("** Detalhes da transferência recebida **");
                        System.out.printf("Transferido da conta %s para essa conta (%s)\n", ((TransferenciaIn) imprimir.get(index)).getNumeroContaEntrada(), contasDoCaixa.get(indexMain).numeroDaConta);
                        System.out.printf("CPF da pessoa que transferiu: %s\n", ((TransferenciaIn) imprimir.get(index)).getCpfOrigem());
                        System.out.printf("Nome da pessoa que transferiu: %s\n", ((TransferenciaIn) imprimir.get(index)).getNomeOrigem());
                        System.out.printf("Data da transferência: %s\n", imprimir.get(index).getData());
                        System.out.printf("Valor da transferência: R$ %d,%02d\n\n", inteiro2, decimal2);
                    }
                    else if (imprimir.get(index) instanceof TransferenciaOut){
                        long valor3 = imprimir.get(index).getValor() * -1;

                        long inteiro3 = valor2/100;
                        long decimal3 = valor2%100;
                        if(valor3<1) decimal3*=-1;

                        System.out.println("** Detalhes da transferência enviada **");
                        System.out.printf("Transferido dessa conta (%s) para a conta %s\n", contasDoCaixa.get(indexMain).numeroDaConta, ((TransferenciaOut) imprimir.get(index)).getNumeroContaSaida());
                        System.out.printf("CPF da pessoa que recebeu esse valor: %s\n", ((TransferenciaOut) imprimir.get(index)).getCpfDestino());
                        System.out.printf("Nome da pessoa que recebeu esse valor: %s\n", ((TransferenciaOut) imprimir.get(index)).getNomeDestino());
                        System.out.printf("Data da transferência: %s\n", imprimir.get(index).getData());
                        System.out.printf("Valor da transferência: R$ %d,%02d\n\n", inteiro3, decimal3);
                    }
                    else if (imprimir.get(index) instanceof Salario) {
                        System.out.println("Todas as informações do salário já estão no extrato!");
                    }
                    else{
                        System.out.println("Todas as informações do rendimento da poupança já estão no extrato!");
                    }
                }
                else
                {
                    System.out.println("Esse item do extrato não existe!");
                }
            }
            else if (opcao.equals("Não") || opcao.equals("não")){
                finaliza = true;
            }
            else
            {
                System.out.println("Opção inválida!");
            }
        }
    }

    public void vincularSalario(int index){
        if(contasDoCaixa.get(index).contaSalario){
            System.out.println("Você já tem um salário vinculado a esta conta.");
            return;
        }

        System.out.println("Dia do pagamento:");
        int diaPagamento= 0;
        if(sc.hasNextInt()){diaPagamento = sc.nextInt();}
        sc.nextLine();
        if(diaPagamento<=0) return;

        if(diaPagamento < 1 || diaPagamento > 28){System.out.println("Dia inválido para recebimento de salário.\n");return; }
        contasDoCaixa.get(index).diaPagamento = diaPagamento;

        long salario;
        do{
            salario = formatoDinheiro();
        }while (salario <=0);
        contasDoCaixa.get(index).salario = salario;
        contasDoCaixa.get(index).contaSalario = true;

        if(day == contasDoCaixa.get(index).diaPagamento){
            contasDoCaixa.get(index).capital+=contasDoCaixa.get(index).salario;
            String dataPagamento = printarData();
            String PagamentoDia = String.valueOf(contasDoCaixa.get(index).diaPagamento);
            if (contasDoCaixa.get(index).diaPagamento < 10) PagamentoDia = "0" + diaPagamento;
            dataPagamento = diaPagamento + dataPagamento.substring(2);

            contasDoCaixa.get(index).extrato.add(new Salario(contasDoCaixa.get(index).salario, dataPagamento, "Salário", "Recebimento do salário"));

        }

        System.out.println("Sua conta passará a receber seu salário mensalmente\n");
    }

    public void configurarPIX(int index){
        System.out.println("Como você deseja que seja sua chave PIX?");
       int escolha = -1;
       while(escolha == -1){
           System.out.println("(1) CPF      (2) Email      (3) Telefone      (4) Chave aleatória");
           if (sc.hasNextInt()) { escolha = sc.nextInt(); }
           sc.nextLine();
           if(escolha == -1) {System.out.println("Escolha uma opção válida");}
       }
        String PIX = null;
       switch (escolha){
           case 1 -> {
               contasDoCaixa.get(index).setPIX(contasDoCaixa.get(index).cpf);
               PIX = "o número do seu CPF";
           }
           case 2 -> {
               contasDoCaixa.get(index).setPIX(contasDoCaixa.get(index).email);
               PIX = "o seu e-mail";
           }
           case 3 -> {
               contasDoCaixa.get(index).setPIX(contasDoCaixa.get(index).telefone);
               PIX = "o número do seu telefone";
           }
           case 4 -> {
               boolean existe = false;
               String chave;
               do{
                   long chavelong = (long) (1 + (Math.random() * 9999));
                   chave = String.valueOf(chavelong);
                   for(String n: chaves){
                       if(chave.equals(n)) {existe=true; break;}
                   }
               }while (existe);

               if(!contasDoCaixa.get(index).isNullPix()){
                   String pix = contasDoCaixa.get(index).randomPix();
                   for(int i=0;i<chaves.size();i++){
                       if(pix.equals(chaves.get(i))) chaves.remove(i);
                   }
               }

               contasDoCaixa.get(index).setPIX(chave);

               chaves.add(chave);
               PIX = " a chave aleatória: "+chave;
           }
       }
       System.out.println("Seu PIX agora é "+ PIX+"\n");
       for(int i =0;i<chaves.size();i++){
           System.out.print(chaves.get(i)+ " ");
       }
        System.out.println('\n');


    }

    public void transferenciaContas(int index){
        System.out.println("Irá transferir por agência ou PIX?\n 1- Agência 2-Pix");
        int escolha =-1;
        if(sc.hasNext()) escolha= sc.nextInt();
        sc.nextLine();
        if(escolha==-1 && escolha!=1 && escolha!=2){System.out.println("Resposta inválida\n");return;}

        if(escolha==1){System.out.println("Agência: 0001");}
        System.out.println("Para que conta pretende transferir?");
        String contaTranferir= sc.nextLine();
        if(contaTranferir.equals(contasDoCaixa.get(index).numeroDaConta)){System.out.println("Não há como tranferir para a mesma conta.\n");return;}

        int index2 = varrerArray(contaTranferir);
        if(index2==-1){System.out.println("Essa conta não existe\n");return;}

        if(escolha==2){
            if(contasDoCaixa.get(index2).PIX == null){System.out.println("Essa conta não possui chave PIX\n");return;}

            System.out.println("Digite a chave PIX da conta para qual deseja realizar a tranferência");
            String PIXTranferir = sc.nextLine();
            if(!PIXTranferir.equals(contasDoCaixa.get(index2).PIX)){System.out.println("A chave PIX está incorreta\n");return;}
        }
        long quantia = formatoDinheiro();

        if(quantia>contasDoCaixa.get(index).capital){System.out.println("Não há essa quantia para ser tranferida em sua conta\n");return;}
        contasDoCaixa.get(index2).setCapital(quantia);
        quantia*=-1;
        contasDoCaixa.get(index).setCapital(quantia);

        String nomeDestino = contasDoCaixa.get(index2).nome;
        String cpfDestino = contasDoCaixa.get(index2).cpf;

        String data = printarData();
        contasDoCaixa.get(index).extrato.add(new TransferenciaOut(contaTranferir, data, quantia, nomeDestino, cpfDestino));
        quantia*=-1;
        contasDoCaixa.get(index2).extrato.add(new TransferenciaIn(contasDoCaixa.get(index).numeroDaConta, data, quantia, contasDoCaixa.get(index).nome, contasDoCaixa.get(index).cpf));
        System.out.println("Transferência realizada com sucesso.\n");

    }


    public String printarData() {
        String dayString;
        String monthString;
        if(day<10)  dayString = "0"+day;
        else dayString = String.valueOf(day);

        if(month<10) monthString="0"+month;
        else monthString = String.valueOf(month);

        String data = dayString+"/"+monthString+"/"+year;

        return data;
    }

    public boolean eh_bissexto(int ano)
    {
        if ((ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0)) {
            return true;

        } else return false;
    }

    public boolean dataErrada(int diaVencimento, int mesVencimento, int anoVencimento){
        if(eh_bissexto(anoVencimento) && mesVencimento==2){
            if(diaVencimento>29) return true;
        } else if (mesVencimento==2){
            if(diaVencimento>28) return true;
        }

        if(((mesVencimento < 7 && mesVencimento % 2 == 0) || (mesVencimento > 8 && mesVencimento % 2 != 0)) && diaVencimento>30){
            return true;
        }else if (diaVencimento>31) return true;

        if(mesVencimento>12) return true;

        return false;
    }


    public void pagarBoleto(int index){
        /*Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        */

        int diaVencimento;
        int mesVencimento;
        int anoVencimento;
        System.out.println("Código de barras: ");
        String codigoDeBarras = sc.nextLine();
        if(!codigoDeBarras.matches("\\d{48}")){
            System.out.println("Formato de código de barras inválido\n");
            return;
        }

        long valor = formatoDinheiro();
        if(valor<=0) return;

        if(valor>contasDoCaixa.get(index).capital){System.out.println("Você não tem dinheiro suficiente para pagar o boleto\n"); return;}

        System.out.println("Digite a data de vencimento (formato dd/mm/aaaa)");

        String dataDeVencimento = formatoData();
        if(dataDeVencimento == null)return;


        diaVencimento = Integer.parseInt(dataDeVencimento.substring(0,2));
        mesVencimento = Integer.parseInt(dataDeVencimento.substring(3,5));
        anoVencimento = Integer.parseInt(dataDeVencimento.substring(6,10));

        boolean data_errada = dataErrada(diaVencimento, mesVencimento, anoVencimento);
        if (data_errada) {
            System.out.println("Data inválida.\n");
            return;
        }

        String dataDePagamento = printarData();

        boolean prazo;
        if(year < anoVencimento) prazo=true;

        if(year == anoVencimento && month < mesVencimento){
            prazo=true;
        }else if(day <= diaVencimento && month == mesVencimento && year == anoVencimento){
            prazo=true;
        } else{
            prazo=false;
        }

        if(prazo){
            System.out.println("Pagamento realizado com sucesso no prazo!");
            valor*=-1;
            contasDoCaixa.get(index).setCapital(valor);
            contasDoCaixa.get(index).extrato.add(new Boleto(codigoDeBarras,valor,dataDeVencimento,dataDePagamento,0, valor));
            return;
        }

        int multa_por_ano,multa_por_mes=0,multa_por_dia, ano_bissexto=0;
        long multa;

        multa_por_ano= year - anoVencimento;

        for(int i= anoVencimento; i<=year;i++){
            if(i == year && month<=2) continue;
            if(eh_bissexto(i)) ano_bissexto+=1;
        }
        if(mesVencimento>2 && eh_bissexto(anoVencimento)) ano_bissexto-=1;


        if(month > mesVencimento){
            for(int i=mesVencimento;i<month;i++){
                if(i==2){
                    multa_por_mes+=28;
                }else if((i < 7 && i % 2 == 0) || (i > 8 &&i % 2 != 0)){
                    multa_por_mes+=30;
                } else multa_por_mes+=31;
            }
        }
        if(month < mesVencimento){
            for(int i=mesVencimento;i>month;i--){
                if(i==3){
                    multa_por_mes-=28;
                }else if((i < 7 && i % 2 == 0) || (i > 8 &&i % 2 != 0)){
                    multa_por_mes-=30;
                } else multa_por_mes-=31;
            }
        }

        multa_por_dia = day - diaVencimento;

        int contagemDeDiasMulta= multa_por_dia+multa_por_mes+365*multa_por_ano+ano_bissexto;

        float valorFloat = (float) valor;
        float precoMulta = (valorFloat*0.001f*contagemDeDiasMulta);
        float multaFloat = valorFloat + precoMulta;
        multa = (long) (multaFloat);

        long valorMulta = valor - multa;
        if (valorMulta < 0) valorMulta *= -1;

        if(precoMulta*contagemDeDiasMulta < 1){System.out.println("Seu atraso é irrisório pela taxa de multa para ser cobrado");}

        System.out.printf("Pagamento realizado com atraso de: %d dia",contagemDeDiasMulta);
        if(contagemDeDiasMulta>1) System.out.println("s");
        else System.out.print("\n");

        System.out.printf("A pagar: R$ %d,%02d\n", multa/100, multa%100);
        if(multa>contasDoCaixa.get(index).capital){System.out.println("Você não tem dinheiro suficiente para pagar o boleto\n"); return;}

        multa*=-1;
        contasDoCaixa.get(index).setCapital(multa);
        contasDoCaixa.get(index).extrato.add(new Boleto(codigoDeBarras,multa,dataDeVencimento,dataDePagamento, valorMulta, valor));
        System.out.println("Pagamento realizado com sucesso.\n");

    }

    public int timeMachine(int diasCorridos, boolean passarDia, boolean contaSalario, int index){
        int dayNow =day;
        int monthNow = month;
        int yearNow = year;


        int anos = diasCorridos/365;

        int bissextos=0;
        for(int i = year; i<=year+anos; i++) {
            if(eh_bissexto(i)) bissextos+=1;
        }

        year = year + anos;

        int restoDia = (diasCorridos%365)-bissextos;
        int pagarPorMes = 0;

        while(restoDia >= 28){
            if(month==2 ){
                if(eh_bissexto(year)){
                    if(restoDia>=29){pagarPorMes+=1;restoDia -=29;month+=1;}
                }else if(restoDia>=28){pagarPorMes+=1;restoDia -=28;month+=1;}

                if(contaSalario){
                    String dataPagamento = printarData();
                    String diaPagamento = String.valueOf(contasDoCaixa.get(index).diaPagamento);
                    if (contasDoCaixa.get(index).diaPagamento < 10) diaPagamento = "0" + diaPagamento;
                    dataPagamento = diaPagamento + dataPagamento.substring(2);

                    contasDoCaixa.get(index).extrato.add(new Salario(contasDoCaixa.get(index).salario, dataPagamento, "Salário", "Recebimento do salário"));
                }

            }else if((month < 7 && month % 2 == 0) || (month > 8 && month % 2 != 0)){
                if(restoDia>=30){pagarPorMes+=1;restoDia -=30;month+=1;}

                if(contaSalario){
                    String dataPagamento = printarData();
                    String diaPagamento = String.valueOf(contasDoCaixa.get(index).diaPagamento);
                    if (contasDoCaixa.get(index).diaPagamento < 10) diaPagamento = "0" + diaPagamento;
                    dataPagamento = diaPagamento + dataPagamento.substring(2);

                    contasDoCaixa.get(index).extrato.add(new Salario(contasDoCaixa.get(index).salario, dataPagamento, "Salário", "Recebimento do salário"));
                }

            } else {
                if(restoDia>=31){pagarPorMes+=1;restoDia -=31;month+=1;}

                if(contaSalario){
                    String dataPagamento = printarData();
                    String diaPagamento = String.valueOf(contasDoCaixa.get(index).diaPagamento);
                    if (contasDoCaixa.get(index).diaPagamento < 10) diaPagamento = "0" + diaPagamento;
                    dataPagamento = diaPagamento + dataPagamento.substring(2);

                    contasDoCaixa.get(index).extrato.add(new Salario(contasDoCaixa.get(index).salario, dataPagamento, "Salário", "Recebimento do salário"));
                }

                if(month>=13) {
                    month = (month%13)+1;
                    year +=1;
                    if (eh_bissexto(year) && month > 2) restoDia-=1;
                    continue;
                }

                if(restoDia <= 30) {break;}
            }


            if(restoDia <= 29 && month != 2 && eh_bissexto(year)){break;}
            if(restoDia <= 28 && month != 2){break;}
        }

        if(passarDia) return restoDia;

        day = dayNow;
        month = monthNow;
        year = yearNow;

        int contagemFinal = anos*12 + pagarPorMes;
        return contagemFinal;
    }

    public void passarTempo() {

        System.out.println("Digite os dias que se passarão:");
        int diasCorridos = 0;
        if(sc.hasNextInt()){diasCorridos = sc.nextInt();}
        sc.nextLine();
        if(diasCorridos<=0) {System.out.println("Número de dias inválidos");return;}

        for (int i = 0; i < contasDoCaixa.size(); i++) {

            if (contasDoCaixa.get(i) instanceof ContaPoupanca) {
                int contagemPoupança = diasCorridos;
                if (day < ((ContaPoupanca) contasDoCaixa.get(i)).day) {
                    contagemPoupança = contagemPoupança - (((ContaPoupanca) contasDoCaixa.get(i)).day - day);
                } else if (day > ((ContaPoupanca) contasDoCaixa.get(i)).day) {
                    contagemPoupança = contagemPoupança + (day - ((ContaPoupanca) contasDoCaixa.get(i)).day);
                }
                int contagemFinal = timeMachine(contagemPoupança, false,false, 0);

                double taxa = Math.pow(1.003, contagemFinal);
                double capitalFinal = Math.round(contasDoCaixa.get(i).capital * taxa);
                long capitalFinalLong = (long) capitalFinal;
                long rendimento = capitalFinalLong - contasDoCaixa.get(i).capital;

                 contasDoCaixa.get(i).extrato.add(new RendimentoPoupanca(rendimento,"C. Mês", "Rendimento", "Rendimento da poupança"));

                 contasDoCaixa.get(i).capital = capitalFinalLong ;
            }
            if (contasDoCaixa.get(i).contaSalario) {

                int contagemSalario = diasCorridos;
                if (day < contasDoCaixa.get(i).diaPagamento) {
                    contagemSalario = contagemSalario - (contasDoCaixa.get(i).diaPagamento - day);
                } else if (day > contasDoCaixa.get(i).diaPagamento) {
                    contagemSalario = contagemSalario + (day - contasDoCaixa.get(i).diaPagamento);
                }
                int contagemFinal = timeMachine(contagemSalario, false, true, i);
                contasDoCaixa.get(i).capital += contasDoCaixa.get(i).salario * contagemFinal;
            }
        }
        int plusday = timeMachine(diasCorridos,true, false, 0);
        this.day = this.day + plusday;

        if (month == 2) {
            if (eh_bissexto(year)) {
                if (this.day > 29) {
                    this.day = this.day - 29;
                    month += 1;
                }
            } else if (this.day > 28) {
                this.day = this.day - 28;
                month += 1;
            }

        } else if ((month < 7 && month % 2 == 0) || (month > 8 && month % 2 != 0)) {
            if (this.day > 30) {
                this.day = this.day - 30;
                month += 1;
            }

        } else {
            if (this.day > 31) {
                this.day = this.day - 31;
                month += 1;
            }
        }
        if(month>=13) {
            month = (month%13)+1;
            year +=1;
            if (eh_bissexto(year) && month >2){
                this.day-=1;
                if(day==0){
                    month -=1;
                    if((month < 7 && month % 2 == 0) || (month > 8 && month % 2 != 0)){
                        day = 30;
                    }if(month ==2){
                        day = 29;
                    }else{
                        day = 31;
                    }
                }
            }
        }
    }
}
