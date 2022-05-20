package src;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public abstract class SmartDevice implements Serializable, PendingChanges{
    private Simulador simulador;
    private String id;
    private CasaInteligente casa;
    private double custoInstalacao;
    private double consumoDiario;
    private Modo modo;
    private Modo modoToChange;
    private LocalDate lastChange; //data do ultimo periodo de faturacao

    public enum Modo{
        ON, OFF;
    }

    public SmartDevice() {
        super();
        this.simulador = null;
        this.id = "N/A";
        this.modo = Modo.OFF;
        this.modoToChange = null;
        this.custoInstalacao = 0;
        this.consumoDiario = -1;
        this.lastChange = LocalDate.now();
        this.casa = null;

    }

    public SmartDevice(Simulador simulador, double custoInstalacao) {
        super();
        this.simulador = simulador;
        this.id = simulador.getNextId();
        this.custoInstalacao = custoInstalacao;
        this.consumoDiario = -1;
        this.modo = Modo.OFF;
        this.modoToChange = null;
        this.lastChange = LocalDate.now();
        this.casa = null;

        simulador.addDispositivo(this);
    }

    public SmartDevice(Simulador simulador, double custoInstalacao, Modo modo) {
        super();
        this.simulador = simulador;
        this.id = simulador.getNextId();
        this.custoInstalacao = custoInstalacao;
        this.consumoDiario = -1;
        this.modo = modo;
        this.modoToChange = null;
        this.lastChange = LocalDate.now();
        this.casa = null;

        simulador.addDispositivo(this);
    }

    public SmartDevice(SmartDevice smartDevice) {
        this.simulador = smartDevice.simulador;
        this.id = smartDevice.getID();
        this.custoInstalacao = smartDevice.custoInstalacao;
        this.consumoDiario = smartDevice.consumoDiario;
        this.modo = smartDevice.modo;
        this.modoToChange = smartDevice.modoToChange;
        this.lastChange = smartDevice.lastChange;
        this.casa = smartDevice.casa;
    }

    public double consumoAte(LocalDate date) {
        if (modo == Modo.ON) {
            int diasPassados = this.lastChange.until(date).getDays();
            return diasPassados * this.consumoDiario;
        } else return 0;
    }

    public double custoAte(Comercializador comercializador, LocalDate date) {
        if (this.modo == Modo.ON) {
            int diasPassados = this.lastChange.until(date).getDays();
            return comercializador.precoDiaPorDispositivo(this) * diasPassados;
        } else return 0;
    }

    public void turnOn() {
        this.modoToChange = Modo.ON; //coloca a mudanca de maneira a ser executada no fim do periodo de simulacao
        this.lastChange = LocalDate.now();
    }

    public void turnOff() {
        this.modoToChange = Modo.OFF; //coloca a mudanca de maneira a ser executada no fim do periodo de simulacao
        this.lastChange = LocalDate.now();
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

    public abstract void calcularConsumoDiario();

    public void setCustoInstalacao(double custoInstalacao) {
        this.custoInstalacao = custoInstalacao;
    }

    public Modo getModo() {
        return modo;
    }

    public boolean isOn() {
        return this.modo == Modo.ON;
    }

    public LocalDate getLastChange() {
        return lastChange;
    }

    public void setLastChange(LocalDate lastChange) {
        this.lastChange = lastChange;
    }

    public CasaInteligente getCasa() {
        return casa;
    }

    public void setCasa(CasaInteligente casa) {
        this.casa = casa;
    }

    public void setConsumoDiario(double consumo) {
        this.consumoDiario = consumo;
    }

    public double getConsumoDiario() {
        return this.consumoDiario;
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

    public static SmartDevice escolherDispositivo(Map<String, SmartDevice> disp, Scanner scanner) {
        for (SmartDevice sD: disp.values()) {
            System.out.println(sD);
        }
        System.out.println("Escolhe o id do dispositivo que queres");
        String id = scanner.nextLine(); //assume-se que escolheu uma opcao valida
        if (disp.containsKey(id)) {
            return disp.get(id);
        } else {
            System.out.println("Id nao existente");
            return null;
        }
    }

    public abstract SmartDevice clone();

    @Override
    public void change() {
        if (this.modoToChange != null) {
            this.modo = this.modoToChange;
            this.modoToChange = null;
        }
    }
}
