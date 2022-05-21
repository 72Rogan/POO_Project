package src;

import java.io.*;
import java.util.Scanner;

import static src.Simulador.construirSimulador;

public class Programa {
    private static String caminhoFicheirosObjeto = "presets_obj/";
    private static String caminhoFicheirosTexto = "presets_txt/";
    private static String caminhoEventosAutomaticos = "simular/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Simulador simulador = null;
        // ----- FASE INICIAL -----
        System.out.println("+--------------------------------------------------+");
        System.out.println("|                                                  |");
        System.out.println("|                    Bem vindo!                    |");
        System.out.println("|                                                  |");
        System.out.println("+--------------------------------------------------+");
        System.out.println("|                                                  |");
        System.out.println("| -> Escolha uma opção:                            |");
        System.out.println("|                                                  |");
        System.out.println("| 1. Carregar Informacao de ficheiro de objeto     |");
        System.out.println("|                                                  |");
        System.out.println("| 2. Carregar Informacao de ficheiro de texto      |");
        System.out.println("|                                                  |");
        System.out.println("| 3. Criar informação                              |");
        System.out.println("|                                                  |");
        System.out.println("+--------------------------------------------------+\n");
        System.out.println("Insira aqui a sua resposta: ");
        String escolha = scanner.nextLine();
        if (escolha.equals("1")) {
            System.out.println("\n+--------------------------------------------------+");
            System.out.println("| -> Escolha ficheiro a ser carregado:             |");
            System.out.println("+--------------------------------------------------+");

            String ficheiro = escolherFicheiro(caminhoFicheirosObjeto, scanner);
            simulador = construirSimulador(caminhoFicheirosObjeto + ficheiro); //recebe o caminho para um ficheiro onde esta escrito uma entidade da classe Simulador
        } else if (escolha.equals("2")) {
            System.out.println("\n+--------------------------------------------------+");
            System.out.println("| -> Escolha ficheiro a ser carregado:             |");
            System.out.println("+--------------------------------------------------+");
            String ficheiro = escolherFicheiro(caminhoFicheirosTexto, scanner);
            Parser parser = new Parser(caminhoFicheirosTexto + ficheiro);
            simulador = parser.parse();
        } else if (escolha.equals("3")) {
            simulador = new Simulador();
            simulador.faseInicial(scanner);
        } else {
            System.out.println("\n+--------------------------------------------------+");
            System.out.println("| -> Não escolheu uma opção válida!                |");
            System.out.println("+--------------------------------------------------+");
            return;
        }
        if (simulador == null) {
            System.out.println("+--------------------------------------------------+");
            System.out.println("|              Terminando o programa!              |");
            System.out.println("+--------------------------------------------------+");

            return;
        }

        // ----- FASE DE SIMULACAO -----
        System.out.println("\n");
        System.out.println("+--------------------------------------------------+");
        System.out.println("| -> Simular o programa                            |");
        System.out.println("+--------------------------------------------------+");
        System.out.println("|                                                  |");
        System.out.println("| -> Escolha uma opção:                            |");
        System.out.println("|                                                  |");
        System.out.println("| 1. Automatizar a simulação                       |");
        System.out.println("|                                                  |");
        System.out.println("| 2. Simular manualmente                           |");
        System.out.println("|                                                  |");
        System.out.println("+--------------------------------------------------+\n");
        System.out.println("Insira aqui a sua resposta: ");
        escolha = scanner.nextLine();
        System.out.println("\n");
        if (escolha.equals("1")) {
            System.out.println("+--------------------------------------------------+");
            System.out.println("| -> Escolha o ficheiro com os eventos automaticos |");
            System.out.println("+--------------------------------------------------+");
            String ficheiro = escolherFicheiro(caminhoEventosAutomaticos, scanner);
            Parser parser = new Parser(caminhoEventosAutomaticos + ficheiro);
            parser.simular(simulador);
        } else if (escolha.equals("2")) {
            simulador.startInterface(scanner);
        }
        simulador.createStatusFile("output/estado_final.txt");
        System.out.println("\n");
        System.out.println("+--------------------------------------------------------------------------------------+");
        System.out.println("| -> Foi colocado no ficheiro 'output/estado_final.txt' o estado final da simulação    |");
        System.out.println("+--------------------------------------------------------------------------------------+\n");
    }

    public static void guardarEstadoAtual(Simulador s, Scanner scanner) {
        System.out.println("Escreva o nome do ficheiro objeto onde vai ser gravado o estado atual");
        String nome = scanner.nextLine();
        guardarEstadoAtual(s, caminhoFicheirosObjeto + nome);
    }

    public static void guardarEstadoAtual(Simulador s, String ficheiroObjeto) {
        try {
            File ficheiro = new File(ficheiroObjeto);

            FileOutputStream fo = new FileOutputStream(ficheiro);
            ObjectOutputStream oo = new ObjectOutputStream(fo);

            oo.writeObject(s);

            fo.close();
            oo.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro nao encontrado");
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erro a inicializar a stream");
            //e.printStackTrace();
        }
    }

    public static String escolherFicheiro(String path, Scanner scanner){
        File diretoria = new File(path);
        String[] conteudo = diretoria.list();
        System.out.println(" -> Ficheiros disponiveis:                        ");
        for (int i=0; i<conteudo.length; i++) {
            if (!conteudo[i].equals("README.md")) {
                System.out.println("    -> "+conteudo[i]);
            }
        }
        System.out.println("+--------------------------------------------------+\n");
        System.out.println("Escreve o ficheiro que queres abrir: ");
        String ret = scanner.nextLine();
        return ret;
    }
}
