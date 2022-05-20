package src;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Comercializador implements Serializable, PendingChanges {
    private Simulador simulador;
    private String nome;
    private double custoDiarioKwh;
    private double custoToChange;
    private double fatorImpostos;
    private double fatorToChange;
    private List<Fatura> faturasEmitidas;
    private Formula formula;


    public Comercializador() {
        this.simulador = null;
        this.nome = "N/A";
        this.custoDiarioKwh = -1;
        this.custoToChange = -1;
        this.fatorImpostos = -1;
        this.fatorToChange = -1;
        this.faturasEmitidas = new ArrayList<>();
        this.formula = Formula.formula0();
    }

    public Comercializador(Simulador simulador, String nome, double custoDiarioKwh, double fatorImpostos) {
        this.simulador = simulador;
        this.nome = nome;
        this.custoDiarioKwh = custoDiarioKwh;
        this.custoToChange = -1;
        this.fatorImpostos = fatorImpostos;
        this.fatorToChange = -1;
        this.faturasEmitidas = new ArrayList<>();
        this.formula = Formula.formula0(); //comercializador usa a primeira formula

        simulador.addComercializador(this);
    }

    public Comercializador(Simulador simulador, String nome, double custoDiarioKwh, double fatorImpostos, Random random) {
        this.simulador = simulador;
        this.nome = nome;
        this.custoDiarioKwh = custoDiarioKwh;
        this.custoToChange = -1;
        this.fatorImpostos = fatorImpostos;
        this.fatorToChange = -1;
        this.faturasEmitidas = new ArrayList<>();
        this.formula = Formula.getFormula(random);

        simulador.addComercializador(this);
    }

    public static Comercializador parse(Simulador simulador, String nome, Random random) {
        double custoDiariokwh = 0.05 + random.nextDouble() * 0.10; //da um valor entre 0.05 e 0.15
        double impostos = random.nextDouble() + 1; //valor entre 1 e 2

        Comercializador c = new Comercializador(simulador, nome, custoDiariokwh, impostos, random);
        return c;
    }

    public double precoDiaPorDispositivo(SmartDevice smartDevice) {
        //chama a formula respetiva do comercializador
        return this.formula.precoDiario(smartDevice, custoDiarioKwh, fatorImpostos);
    }

    public double getCustoDiarioKwh() {
        return custoDiarioKwh;
    }

    public void setCustoDiarioKwh(double custoDiarioKwh) {
        this.custoToChange = custoDiarioKwh;
    }

    public void mudarValores(Scanner scanner) {
        System.out.println("Digite os novos valores do comercializador no formato CustoDiarioKwh,FatorImpostos");
        System.out.println("Por exemplo: 0.15,1.3");
        System.out.println("Se quiser manter algum parametro, escreva -1 no parametro respetivo");
        String input = scanner.nextLine();
        String[] nomeNif = input.split(",", 2);
        double custoDiarioKwh = Double.valueOf(nomeNif[0]);
        double fatorImpostos = Double.valueOf(nomeNif[1]);
        if (custoDiarioKwh != -1) setCustoDiarioKwh(custoDiarioKwh);
        if (fatorImpostos != -1) setFatorImpostos(fatorImpostos);
    }

    public double getFatorImpostos() {
        return fatorImpostos;
    }

    public void setFatorImpostos(double fatorImpostos) {
        this.fatorToChange = fatorImpostos;
    }

    public void addFatura(Fatura fatura) {
        this.faturasEmitidas.add(fatura);
    }

    public static Comercializador escolherComercializador(Map<String, Comercializador> c, Scanner scanner) {
        System.out.println("Comercializadores existentes:");
        for (Comercializador comercializador: c.values()) {
            System.out.println(comercializador.toString());
        }
        System.out.println("Escreve o nome do comercializador que queres");
        String nome = scanner.nextLine(); //assume-se que escolheu uma opcao valida
        if (c.containsKey(nome)) {
            return c.get(nome);
        } else {
            System.out.println("Nome de Comercializador nao existente");
            return null;
        }
    }

    public void printFaturas() {
        System.out.println(this.faturasEmitidas.toString());
    }

    @Override
    public void change() {
        if (this.custoToChange != -1) {
            this.custoDiarioKwh = this.custoToChange;
            this.custoToChange = -1;
        }
        if (this.fatorToChange != -1) {
            this.fatorImpostos = this.fatorToChange;
            this.fatorToChange = -1;
        }
    }

    public List<Fatura> getFaturasEmitidas() {
        return faturasEmitidas;
    }

    public void setFaturasEmitidas(List<Fatura> faturasEmitidas) {
        this.faturasEmitidas = faturasEmitidas;
    }

    public String toString() {
        return this.nome + ", CustoKwh: " + this.custoDiarioKwh + ", Fator Impostos: " + this.fatorImpostos;
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
