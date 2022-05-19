package src;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Comercializador extends Change<Comercializador> implements Serializable {
    private Simulador simulador;
    private String nome;
    private double custoDiarioKwh;
    private double fatorImpostos;
    private List<Fatura> faturasEmitidas;
    private int formulaNum;

    private static int quantidadeFormulas = 4;

    public Comercializador() {
        super();
        this.simulador = null;
        this.nome = "N/A";
        this.custoDiarioKwh = -1;
        this.fatorImpostos = -1;
        this.faturasEmitidas = new ArrayList<>();
        this.formulaNum = 0; //comercializador usa a primeira formula
    }

    public Comercializador(Simulador simulador, String nome, double custoDiarioKwh, double fatorImpostos) {
        super();
        this.simulador = simulador;
        this.nome = nome;
        this.custoDiarioKwh = custoDiarioKwh;
        this.fatorImpostos = fatorImpostos;
        this.faturasEmitidas = new ArrayList<>();
        this.formulaNum = 0; //comercializador usa a primeira formula

        simulador.addComercializador(this);
    }

    public Comercializador(Simulador simulador, String nome, double custoDiarioKwh, double fatorImpostos, int formulaNum) {
        super();
        this.simulador = simulador;
        this.nome = nome;
        this.custoDiarioKwh = custoDiarioKwh;
        this.fatorImpostos = fatorImpostos;
        this.faturasEmitidas = new ArrayList<>();
        this.formulaNum = formulaNum % quantidadeFormulas;

        simulador.addComercializador(this);
    }

    public static Comercializador parse(Simulador simulador, String nome, Random random) {
        double custoDiariokwh = 0.05 + random.nextDouble() * 0.10; //da um valor entre 0.05 e 0.15
        double impostos = random.nextDouble() + 1; //valor entre 1 e 2
        int numformula = random.nextInt(quantidadeFormulas);

        Comercializador c = new Comercializador(simulador, nome, custoDiariokwh, impostos, numformula);
        return c;
    }

    public double precoDiaPorDispositivo(SmartDevice smartDevice) {
        double custoBase = custoDiarioKwh * smartDevice.getConsumoDiario();
        if (this.formulaNum == 0) {
            return custoBase * (1+fatorImpostos) * 0.9;
        } else if (this.formulaNum == 1) {
            return custoBase + (fatorImpostos*0.05);
        } else if (this.formulaNum == 2) {
            return 1 + custoBase;
        }
        else {
            return fatorImpostos > 5 ? custoBase : custoBase + 0.5;
        }
    }

    public double getCustoDiarioKwh() {
        return custoDiarioKwh;
    }

    public void setCustoDiarioKwh(double custoDiarioKwh) {
        if (this.toChange == null) createToChange();
        this.toChange.custoDiarioKwh = custoDiarioKwh;
    }

    public double getFatorImpostos() {
        return fatorImpostos;
    }

    public void setFatorImpostos(double fatorImpostos) {
        if (this.toChange == null) createToChange();
        this.toChange.fatorImpostos = fatorImpostos;
    }

    public void addFatura(Fatura fatura) {
        this.faturasEmitidas.add(fatura);
    }

    public static Comercializador escolherComercializador(Map<String, Comercializador> c, Scanner scanner) {
        System.out.println("Escolhe um comercializador");
        List<Comercializador> comercializadorList = c.values().stream().collect(Collectors.toList());
        for (int i=0; i< comercializadorList.size(); i++) {
            System.out.println(i + " - " + comercializadorList.get(i));
        }
        int escolha = scanner.nextInt(); //assume-se que escolheu uma opcao valida
        return comercializadorList.get(escolha);
    }

    public void printFaturas() {
        System.out.println(this.faturasEmitidas.toString());
    }

    @Override
    public void createToChange() {
        Comercializador comercializador = new Comercializador();
        super.setToChange(comercializador);
    }

    @Override
    public void change() {
        Comercializador comToChange = super.getToChange();
        if (comToChange != null) {
            if (comToChange.fatorImpostos != -1) this.fatorImpostos = comToChange.fatorImpostos;
            if (comToChange.custoDiarioKwh != -1) this.custoDiarioKwh = comToChange.custoDiarioKwh;
            super.toChange = null;
        }
    }

    public List<Fatura> getFaturasEmitidas() {
        return faturasEmitidas;
    }

    public void setFaturasEmitidas(List<Fatura> faturasEmitidas) {
        this.faturasEmitidas = faturasEmitidas;
    }

    public String toString() {
        return "Comercializador " + this.nome;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o==null || this.getClass() != o.getClass()) return false;
        Comercializador comercializador = (Comercializador) o;
        return this.nome == comercializador.nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
