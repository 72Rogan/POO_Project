package src;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class SmartDevice extends Change<SmartDevice> implements Serializable {
    private Simulador simulador;
    private String id;
    private double custoInstalacao;
    private Modo modo;
    private LocalDate lastChange; //data do ultimo periodo de faturacao

    public enum Modo{
        ON, OFF;
    }

    public SmartDevice() {
        super();
        this.simulador = null;
        this.id = "N/A";
        this.modo = Modo.OFF;
        this.custoInstalacao = 0;
        this.lastChange = LocalDate.now();

    }

    public SmartDevice(Simulador simulador, String id, double custoInstalacao) {
        super();
        this.simulador = simulador;
        this.id = id;
        this.custoInstalacao = custoInstalacao;
        this.modo = Modo.OFF;
        this.lastChange = LocalDate.now();

        simulador.addDispositivo(this);
    }

    public SmartDevice(Simulador simulador, String id, double custoInstalacao, Modo modo) {
        super();
        this.simulador = simulador;
        this.id = id;
        this.custoInstalacao = custoInstalacao;
        this.modo = modo;
        this.lastChange = LocalDate.now();

        simulador.addDispositivo(this);
    }

    public SmartDevice(SmartDevice smartDevice) {
        super.toChange = smartDevice.toChange;
        this.simulador = smartDevice.simulador;
        this.id = smartDevice.getID();
        this.custoInstalacao = smartDevice.custoInstalacao;
        this.modo = smartDevice.modo;
        this.lastChange = smartDevice.lastChange;
    }

    public double consumoAte(LocalDate date) {
        if (modo == Modo.ON) {
            int diasPassados = this.lastChange.until(date).getDays();
            return diasPassados * this.consumoDiario();
        } else return 0;
    }

    public double custoAte(Comercializador comercializador, LocalDate date) {
        if (this.modo == Modo.ON) {
            int diasPassados = this.lastChange.until(date).getDays();
            return comercializador.precoDiaPorDispositivo(this) * diasPassados;
        } else return 0;
    }

    public abstract double consumoDiario();

    public void turnOn() {
        if (this.lastChange.until(this.simulador.getData(), ChronoUnit.DAYS) >= 1 && this.modo == Modo.OFF) {
            //so pode modificar se ja passou um dia desde a ultima mudanca
            if (this.toChange == null) createToChange();
            this.toChange.modo = Modo.ON; //coloca a mudanca de maneira a ser executada no fim do periodo de simulacao
            this.lastChange = LocalDate.now();
        } //se o modo for ON, nao e preciso redefini-lo como ON nem mudar a lastChange
    }

    public void turnOff() {
        if (this.lastChange.until(this.simulador.getData(), ChronoUnit.DAYS) >= 1 && this.modo == Modo.ON) {
            //so pode modificar se ja passou um dia desde a ultima mudanca
            System.out.println("A colocar off");
            if (this.toChange == null) createToChange();
            this.toChange.modo = Modo.OFF; //coloca a mudanca de maneira a ser executada no fim do periodo de simulacao
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

    public LocalDate getLastChange() {
        return lastChange;
    }

    public void setLastChange(LocalDate lastChange) {
        this.lastChange = lastChange;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().toString());
        sb.append(", ");
        sb.append(this.id);
        sb.append(", ");
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
                this.lastChange.equals(smartDevice.lastChange);
    }

    public abstract SmartDevice clone();

    @Override
    public void createToChange() {
        SmartDevice smartDevice = new SmartSpeaker(); //nao importa ser smartSpeaker, dado que so importa o modo
        super.setToChange(smartDevice);
    }

    @Override
    public void change() {
        SmartDevice smartDevice = getToChange();
        if (smartDevice != null) {
            if (smartDevice.modo != null) this.modo = smartDevice.modo;
            super.toChange = null;
        }
    }
}
