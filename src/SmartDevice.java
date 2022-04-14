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
        this.modo = Modo.OFF;
        this.custoInstalacao = 0;
        this.lastChange = LocalDate.now();
        this.diasSemPagar = 0;

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

    public SmartDevice(SmartDevice smartDevice) {
        this.id = smartDevice.getID();
        this.custoInstalacao = smartDevice.custoInstalacao;
        this.modo = smartDevice.modo;
        this.lastChange = smartDevice.lastChange;
        this.diasSemPagar = smartDevice.diasSemPagar;
    }

    public abstract double consumoDiario();

    public double facturar() {
        double res = this.diasSemPagar * consumoDiario();
        this.diasSemPagar = 0;
        return res;
    }

    public void turnOn() {
        if (this.lastChange.until(LocalDate.now(), ChronoUnit.DAYS) >= 1 && this.modo == Modo.OFF) {
            //so pode modificar se ja passou um dia desde a ultima mudança
            this.modo = Modo.ON;
            this.lastChange = LocalDate.now();
        } //se o modo for ON, nao e preciso redefini-lo como ON nem mudar a lastChange
    }

    public void turnOff() {
        if (this.lastChange.until(LocalDate.now(), ChronoUnit.DAYS) >= 1 && this.modo == Modo.ON) {
            //so pode modificar se ja passou um dia desde a ultima mudança
            this.modo = Modo.OFF;
            diasSemPagar += this.lastChange.until(LocalDate.now(), ChronoUnit.DAYS);
            this.lastChange = LocalDate.now();
        }
    }

    public void setOn(boolean b) {
        if (b) turnOn();
        else turnOff();
    }

    public String getID() {
        return id;
    }

    public void setID(String ID) {
        this.id = ID;
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

    public LocalDate getLastChange() {
        return lastChange;
    }

    public void setLastChange(LocalDate lastChange) {
        this.lastChange = lastChange;
    }

    public long getDiasSemPagar() {
        return diasSemPagar;
    }

    public void setDiasSemPagar(long diasSemPagar) {
        this.diasSemPagar = diasSemPagar;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().toString());
        sb.append(": Custo Instalaçao - ");
        sb.append(this.custoInstalacao);
        sb.append(", modo - ");
        sb.append(this.modo);
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        SmartDevice smartDevice = (SmartDevice) o;
        return this.id == smartDevice.getID() &&
                this.custoInstalacao == smartDevice.getCustoInstalacao() &&
                this.modo.equals(smartDevice.getModo()) &&
                this.lastChange.equals(smartDevice.lastChange) &&
                this.diasSemPagar == smartDevice.diasSemPagar;
    }

    public abstract SmartDevice clone();
}
