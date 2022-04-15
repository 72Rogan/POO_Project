package src;

import java.time.LocalDate;

public class Fatura {
    private LocalDate inicio; //Inicio do periodo de faturaçao
    private LocalDate fim; //Fim do periodo de faturaçao
    private double consumo; //kwh
    private double custo; //euros

    public Fatura(LocalDate inicio, LocalDate fim, double consumo, double custo) {
        this.inicio = inicio;
        this.fim = fim;
        this.consumo = consumo;
        this.custo = custo;
    }

    public Fatura(Fatura fatura) {
        this.inicio = fatura.inicio;
        this.fim = fatura.fim;
        this.consumo = fatura.consumo;
        this.custo = fatura.custo;
    }

    public Fatura clone() {
        return new Fatura(this);
    }
}
