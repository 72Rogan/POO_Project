package src;

import java.util.Scanner;

import static src.Simulador.construirSimulador;

public class Programa {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Simulador simulador = null;
        System.out.println("Escolha uma opcao");
        System.out.println("1. Carregar Informacao de ficheiro de objeto");
        System.out.println("2. Carregar Informacao de ficheiro de texto");
        System.out.println("3. Criar informacao");
        int escolha = Integer.parseInt(scanner.nextLine());
        if (escolha == 1) {
            System.out.println("Escreva o caminho do ficheiro a ser carregado");
            String caminho = scanner.nextLine();
            simulador = construirSimulador(caminho); //recebe o caminho para um ficheiro onde esta escrito uma entidade da classe Simulador
        } else if (escolha == 2) {
            System.out.println("Escreva o caminho do ficheiro a ser carregado");
            String caminho = scanner.nextLine();
            Parser parser = new Parser(caminho);
            simulador = parser.parse();
        }else if (escolha == 3) {
            simulador = new Simulador();
            simulador.faseInicial(scanner);
        } else {
            System.out.println("Nao escolheu uma opcao valida");
        }
        if (simulador != null) simulador.startInterface(scanner);
    }
}
