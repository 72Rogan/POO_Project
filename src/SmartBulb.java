package src;

import java.util.Scanner;

public class SmartBulb extends SmartDevice {
    public static final int WARM = 2;
    public static final int NEUTRAL = 1;
    public static final int COLD = 0;

    private int tone;
    private double tamanho;

    /**
     * Constructor for objects of class SmartBulb
     */
    public SmartBulb() {
        super();
        this.tone = NEUTRAL;
    }


    public SmartBulb(Simulador simulador, String id, int tone) {
        super(simulador,id, 150);
        this.tone = tone;
    }

    public SmartBulb(Simulador simulador,String id, Modo modo, int tone, double tamanho) {
        super(simulador, id, 150, modo);
        this.tone = tone;
        this.tamanho = tamanho;
    }

    public SmartBulb(SmartBulb s) {
        super(s);
        this.tone = s.tone;
        this.tamanho = s.tamanho;
    }

    public void setTone(int t) {
        if (t>WARM) this.tone = WARM;
        else if (t<COLD) this.tone = COLD;
        else this.tone = t;
    }

    public int getTone() {
        return this.tone;
    }

    public double getTamanho() {
        return tamanho;
    }

    public void setTamanho(double tamanho) {
        this.tamanho = tamanho;
    }

    public static SmartBulb criarSmartBulb(Simulador simulador, Scanner scanner) {
        System.out.println("Escreve no formato ID-Modo-Tone-Tamanho / Exemplo: Lamp1-OFF-WARM-6.3");
        String input = scanner.next();
        String[] idCustoModoTone = input.split("-", 5);
        String id = idCustoModoTone[0];
        Modo modo = idCustoModoTone[1].equals("OFF") ? Modo.OFF : idCustoModoTone[2].equals("ON") ? Modo.ON : null;
        int tone = idCustoModoTone[2].equals("WARM") ? 2 : idCustoModoTone[3].equals("NEUTRAL") ? 1 : idCustoModoTone[3].equals("COLD") ? 0 : -1;
        double tamanho = Double.valueOf(idCustoModoTone[3]);
        SmartBulb ret = new SmartBulb(simulador, id, modo, tone, tamanho);
        return ret;
    }

    public String toString(){
        return "Lampada, id: " + this.getID() + ", " + this.getModo() + ", Tone: " + this.getTone();
    }

    @Override
    public SmartDevice clone() {
        return new SmartBulb(this);
    }


    @Override
    public void calcularConsumoDiario() {
        double consumo = 0.5 + (this.tone * 2.5); //valor varia entre 0.5 e 5.5
        setConsumoDiario(consumo);
    }

}
