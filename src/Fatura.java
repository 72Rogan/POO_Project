package src;

import java.io.Serializable;
import java.time.LocalDate;

public class Fatura implements Serializable {
    private String nome; //nome do dono da casa
    private LocalDate inicio; //Inicio do periodo de faturaçao
    private LocalDate fim; //Fim do periodo de faturaçao
    private double consumo; //kwh
    private double custo; //euros

    public Fatura(String nome, LocalDate inicio, LocalDate fim, double consumo, double custo) {
        this.nome = nome;
        this.inicio = inicio;
        this.fim = fim;
        this.consumo = consumo;
        this.custo = custo;
    }

    public Fatura(Fatura fatura) {
        this.nome = fatura.nome;
        this.inicio = fatura.inicio;
        this.fim = fatura.fim;
        this.consumo = fatura.consumo;
        this.custo = fatura.custo;
    }

    public Fatura clone() {
        return new Fatura(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fatura para a casa de ");
        sb.append(this.nome);
        sb.append("\nPeriodo de ");
        sb.append(inicio.toString());
        sb.append(" a ");
        sb.append(fim.toString());
        sb.append("\nConsumo: ");
        sb.append(consumo);
        sb.append("Kwh, Custo: ");
        sb.append(custo);
        sb.append("\n");
        return sb.toString();
    }
}
