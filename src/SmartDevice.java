package src;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class SmartDevice {
    private String id;
    private double custoInstalacao;
    private Modo modo;
    private LocalDate lastChange;
    private long diasSemPagar;

    public enum Modo{
        ON, OFF;
    }

    public SmartDevice() {
        this.id = "";
        this.Modo = OFF;
        this.custoInstalacao = 0;
        this.lastChange = LocalDate.now();
        this.diasSemPagar = = 0;

    }

    public SmartDevice(String id, double custoInstalacao) {
        this.id = id;
        this.custoInstalacao = custoInstalacao;
        this.modo = Modo.OFF;
        this.lastChange = LocalDate.now();
    }

    public SmartDevice(String id, double custoInstalacao, Modo modo) {
        this.id = id;
        this.custoInstalacao = custoInstalacao;
        this.modo = modo;
        this.lastChange = LocalDate.now();
    }

    public abstract double consumoDiario();

    public double facturar() {
        double res = this.diasSemPagar * consumoDiario();
        this.diasSemPagar = 0;
        return res;
    }

    public void turnOn() {
        if (this.lastChange.until(LocalDate.now(), ChronoUnit.DAYS) >= 1) {
            //so pode modificar se ja passou um dia desde a ultima mudança
            this.modo = Modo.ON;
            this.lastChange = LocalDate.now();
        }
    }

    public void turnOff() {
        if (this.lastChange.until(LocalDate.now(), ChronoUnit.DAYS) >= 1) {
            //so pode modificar se ja passou um dia desde a ultima mudança
            this.modo = Modo.OFF;
            diasSemPagar += this.lastChange.until(LocalDate.now(), ChronoUnit.DAYS);
            this.lastChange = LocalDate.now();
        }
    }

    public int getID() {
        return id;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getCustoInstalacao() {
        return custoInstalacao;
    }

    public void setCustoInstalacao(double custoInstalacao) {
        this.custoInstalacao = custoInstalacao;
    }

    public Modo getModo() {
        return modo;
    }

    public void setModo(Modo modo) {
        this.modo = modo;
    }
}
