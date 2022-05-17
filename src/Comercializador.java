package src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Comercializador extends Change<Comercializador> implements Serializable {
    private Simulador simulador;
    private String nome;
    private double custoDiarioKwh;
    private double fatorImpostos;
    private List<Fatura> faturasEmitidas;

    public Comercializador() {
        super();
        this.simulador = null;
        this.nome = "N/A";
        this.custoDiarioKwh = -1;
        this.fatorImpostos = -1;
        this.faturasEmitidas = new ArrayList<>();
    }

    public Comercializador(Simulador simulador, String nome, double custoDiarioKwh, double fatorImpostos) {
        super();
        this.simulador = simulador;
        this.nome = nome;
        this.custoDiarioKwh = custoDiarioKwh;
        this.fatorImpostos = fatorImpostos;
        this.faturasEmitidas = new ArrayList<>();

        simulador.addComercializador(this);
    }

    public static Comercializador parse(String linha) {
        return null;
    }

    public double precoDiaPorDispositivo(SmartDevice smartDevice) {
        return custoDiarioKwh * smartDevice.getConsumoDiario() * (1+fatorImpostos) * 0.9;
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

    public static Comercializador escolherComercializador(List<Comercializador> listaComercializador, Scanner scanner) {
        System.out.println("Escolhe um comercializador");
        for (int i=0; i<listaComercializador.size(); i++) {
            System.out.println(i + " - " + listaComercializador.get(i).toString());
        }
        int escolha = scanner.nextInt(); //assume-se que escolheu uma opcao valida
        return listaComercializador.get(escolha);
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
