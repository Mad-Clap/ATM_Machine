package Caixa;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {

        CaixaEletronico caixa = new CaixaEletronico();
        int menu = -1;
        while(menu !=0){
            System.out.println("***                           CAIXA ELETRÔNICO                            ***");
            System.out.println("DATA DO SISTEMA: " + caixa.printarData() + "                                               ***\n");
            System.out.println("(1) Abrir conta     (2) Acessar conta     (3) Andar no tempo     (0) Encerrar");

            if(sc.hasNextInt()){menu = sc.nextInt();}
            sc.nextLine();

            switch (menu){
                case 1 -> caixa.abrirConta();
                case 2 -> caixa.acessarConta();
                case 3 -> caixa.passarTempo();
            }

        }
    }

}

