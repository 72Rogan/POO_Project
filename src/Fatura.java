package src;

import java.io.Serializable;
import java.time.LocalDate;

public class Fatura implements Serializable {
    private String nome; //nome do dono da casa
    private Periodo periodo; //guarda o inicio e fim da fatura
    private double consumo; //kwh
    private double custo; //euros

    public Fatura(String nome, LocalDate inicio, LocalDate fim, double consumo, double custo) {
        this.nome = nome;
        this.consumo = consumo;
        this.custo = custo;
        this.periodo = new Periodo(inicio,fim);
    }

    public Fatura(Fatura fatura) {
        this.nome = fatura.nome;
        this.consumo = fatura.consumo;
        this.custo = fatura.custo;
        this.periodo = fatura.periodo;
    }

    public Fatura clone() {
        return new Fatura(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fatura para a casa de ");
        sb.append(this.nome);
        sb.append("\n");
        sb.append(periodo.toString());
        sb.append("\nConsumo: ");
        sb.append(consumo);
        sb.append("Kwh, Custo: ");
        sb.append(custo);
        sb.append("\n");
        return sb.toString();
    }

    public Periodo getPeriodo() {
        return this.periodo;
    }

    public double getCusto() {
        return this.custo;
    }

    public double getConsumo() {
        return this.consumo;
    }
}
