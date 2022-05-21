package src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Estatisticas implements Serializable {
    private Map<Integer, CasaInteligente> casasInteligentes;
    private Map<String, Comercializador> comercializadores;
    private List<Periodo> periodos;

    public Estatisticas(Map<Integer, CasaInteligente> cIList, Map<String, Comercializador> cList, List<Periodo> pList) {
        this.casasInteligentes = cIList;
        this.comercializadores = cList;
        this.periodos = pList;
    }

    public void estatistica1() {
        //1. Qual a casa que mais gastou (dinheiro) naquele periodo (assume-se que se refere ao ultimo periodo)
        if (this.periodos.isEmpty()) {
            System.out.println("\n");
            System.out.println("+--------------------------------------------------+");
            System.out.println("| -> Ainda nao ha periodos                         |");
            System.out.println("+--------------------------------------------------+\n");

            return;
        }
        if (this.casasInteligentes.isEmpty()) {
            System.out.println("\n");
            System.out.println("+--------------------------------------------------+");
            System.out.println("| -> Ainda nao ha casas                            |");
            System.out.println("+--------------------------------------------------+\n");

            return;
        }
        Periodo periodo = this.periodos.get(this.periodos.size()-1); //pega no ultimo periodo
        CasaInteligente ret = this.casasInteligentes.values().stream()
                .max((casa1, casa2) -> {
                    double gastoCasa1 = casa1.getFatura(periodo).getCusto();
                    double gastoCasa2 = casa2.getFatura(periodo).getCusto();
                    double resto = gastoCasa1 - gastoCasa2;
                    if (resto < 0) {
                        return -1;
                    } else if (resto > 0) {
                        return 1;
                    } else return 0;
                })
                .orElse(null); //se a lista de casas estiver vazia, entao devolve null
        System.out.println("\n");
        System.out.println("+--------------------------------------------------+");
        System.out.println("A casa que mais gastou neste periodo foi " + ret.toString());
        System.out.println("+--------------------------------------------------+\n");

    }

    public void estatistica2() {
        if (this.comercializadores.isEmpty()) {
            System.out.println("\n");
            System.out.println("+--------------------------------------------------+");
            System.out.println("Nao existem comercializadores                      |");
            System.out.println("+--------------------------------------------------+\n");

            return;
        }
        if (this.periodos.isEmpty()) {
            System.out.println("\n");
            System.out.println("+--------------------------------------------------+");
            System.out.println("Ainda nao ocorreram faturacoes                     |");
            System.out.println("+--------------------------------------------------+\n");

            return;
        }
        Comercializador ret = this.comercializadores.values().stream()
                .max((c1, c2) -> {
                    double volumeC1 = c1.getFaturasEmitidas().stream().map(fatura -> fatura.getCusto()).reduce((double) 0, Double::sum); //soma do custo das faturas do comercializador 1
                    double volumeC2 = c2.getFaturasEmitidas().stream().map(fatura -> fatura.getCusto()).reduce((double) 0, Double::sum); //soma do custo das faturas do comercializador 2
                    double resto = volumeC1 - volumeC2;
                    if (resto < 0) {
                        return -1;
                    } else if (resto > 0) {
                        return 1;
                    } else return 0;
                })
                .orElse(null);
        System.out.println("O comercializador com maior volume de faturacao e " + ret.toString());
    }

    public void estatistica3(Scanner scanner) {
        Comercializador comercializador = Comercializador.escolherComercializador(this.comercializadores, scanner);
        if (comercializador != null) System.out.println(comercializador.getFaturasEmitidas().toString());
    }

    public void estatistica4(Scanner scanner) {
        //ou e um periodo ja existente, ou e um periodo metido pelo utilizador, e que pode englobar varios periodos
        //nesta implementacao, tem que se escolher um dos periodos ja existentes
        Periodo periodo = Periodo.escolherPeriodo(this.periodos, scanner);
        if (periodo == null) {
            System.out.println("Periodo Invalido");
            return;
        }
        List<CasaInteligente> ret = this.casasInteligentes.values().stream()
                .sorted((casa1, casa2) -> {
                    double consumoCasa1 = casa1.getFatura(periodo).getConsumo();
                    double consumoCasa2 = casa2.getFatura(periodo).getConsumo();
                    double resto = consumoCasa2 - consumoCasa1; //queremos em ordem decrescente
                    if (resto < 0) {
                        return -1;
                    } else if (resto > 0) {
                        return 1;
                    } else return 0;
                }).collect(Collectors.toList());
        for (int i=0; i< ret.size(); i++) {
            CasaInteligente casa = ret.get(i);
            double consumo = casa.getFatura(periodo).getConsumo();
            System.out.println((i+1) + " - " + casa.toString() + ", Consumo: " + consumo);
        }
    }

    public void escolherEstatistica(Scanner scanner) {
        System.out.println("\n");
        System.out.println("+--------------------------------------------------------------+");
        System.out.println("| -> Estatisticas:                                             |");
        System.out.println("+--------------------------------------------------------------+");
        System.out.println("| 1. Qual e a casa que mais gastou no ultimo periodo           |");
        System.out.println("|                                                              |");
        System.out.println("|2. Qual o comercializador com maior volume de faturacao       |");
        System.out.println("|                                                              |");
        System.out.println("|3. Listar as faturas emitidas por um comercializador          |");
        System.out.println("|                                                              |");
        System.out.println("|4. Ordenacao dos maior consumidores de energia num periodo    |");
        System.out.println("+--------------------------------------------------------------+\n");
        System.out.println("Insere aqui a tua resposta: ");

        String escolha = scanner.nextLine();
        if (escolha.equals("1")) {
            estatistica1();
        } else if (escolha.equals("2")) {
            estatistica2();
        } else if (escolha.equals("3")) {
            estatistica3(scanner);
        } else if (escolha.equals("4")){
            estatistica4(scanner);
        } else {
            System.out.println("\n");
            System.out.println("+--------------------------------------------------+");
            System.out.println("| -> Escolha invalida                              |");
            System.out.println("+--------------------------------------------------+\n");

        }
    }
}
