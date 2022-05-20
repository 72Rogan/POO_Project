package src;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static src.Simulador.construirSimulador;

public class Programa {
    static String caminhoFicheirosObjeto = "presets_obj/";
    static String caminhoFicheirosTexto = "presets_txt/";
    static String caminhoEventosAutomaticos = "simular/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Simulador simulador = null;
        System.out.println("Escolha uma opcao");
        System.out.println("1. Carregar Informacao de ficheiro de objeto");
        System.out.println("2. Carregar Informacao de ficheiro de texto");
        System.out.println("3. Criar informacao");
        int escolha = Integer.parseInt(scanner.nextLine());
        if (escolha == 1) {
            System.out.println("Escolha ficheiro a ser carregado");
            String ficheiro = escolherFicheiro(caminhoFicheirosObjeto, scanner);
            simulador = construirSimulador(caminhoFicheirosObjeto + ficheiro); //recebe o caminho para um ficheiro onde esta escrito uma entidade da classe Simulador
        } else if (escolha == 2) {
            System.out.println("Escolha ficheiro a ser carregado");
            String ficheiro = escolherFicheiro(caminhoFicheirosTexto, scanner);
            Parser parser = new Parser(caminhoFicheirosTexto + ficheiro);
            simulador = parser.parse();
        }else if (escolha == 3) {
            simulador = new Simulador();
            simulador.faseInicial(scanner);
        } else {
            System.out.println("Nao escolheu uma opcao valida");
            return;
        }
        System.out.println("Simular o programa");
        System.out.println("Escolha uma opcao");
        System.out.println("1. Automatizar a simulacao");
        System.out.println("2. Simular manualmente");
        escolha = Integer.parseInt(scanner.nextLine());
        if (escolha == 1) {
            System.out.println("Escolha o ficheiro com os eventos automaticos");
            String ficheiro = escolherFicheiro(caminhoEventosAutomaticos, scanner);
            Parser parser = new Parser(caminhoEventosAutomaticos + ficheiro);
            parser.simular(simulador);
        } else if (escolha == 2) {
            simulador.startInterface(scanner);
        }
        simulador.createStatusFile("output/estado_final.txt");
        System.out.println("Foi colocado no ficheiro 'output/estado_final.txt' o estado final da simulacao");
        System.out.println("Se pretender guardar o estado atual da simulacao num ficheiro de objeto,");
        System.out.println("Escreva o nome do ficheiro a guardar, senao escreva N/A");
        String ficheiroObjeto = scanner.nextLine();
        if (!ficheiroObjeto.equals("N/A")) {
            simulador.guardarEstadoAtual(ficheiroObjeto);
        }
    }

    public static String escolherFicheiro(String path, Scanner scanner){
        File diretoria = new File(path);
        String[] conteudo = diretoria.list();
        System.out.println("Ficheiros disponiveis:");
        for (int i=0; i<conteudo.length; i++) {
            if (!conteudo[i].equals("README.md")) {
                System.out.println(conteudo[i]);
            }
        }
        System.out.println("Escreve o ficheiro que queres abrir");
        String ret = scanner.nextLine();
        return ret;
    }
}
