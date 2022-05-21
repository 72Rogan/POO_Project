package src;

import java.util.Random;
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
        calcularConsumoDiario();
    }


    public SmartBulb(int tone) {
        super(150);
        this.tone = tone;
        calcularConsumoDiario();
    }

    public SmartBulb(Modo modo, int tone, double tamanho) {
        super(150, modo);
        this.tone = tone;
        this.tamanho = tamanho;
        calcularConsumoDiario();
    }

    public SmartBulb(Modo modo, int tone, double tamanho, double consumoDiario) {
        super(150, modo);
        this.tone = tone;
        this.tamanho = tamanho;
        setConsumoDiario(consumoDiario);
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

    public static SmartBulb criarSmartBulb(Scanner scanner) {
        System.out.println("\n");
        System.out.println("+--------------------------------------------------+");
        System.out.println("| -> Escreve no formato Modo,Tone,Tamanho          |");
        System.out.println("| -> Exemplo: OFF,WARM,6.3                         |");
        System.out.println("+--------------------------------------------------+\n");
        System.out.println("Insere aqui a tua reposta: ");
        String input = scanner.nextLine();
        String[] idCustoModoTone = input.split(",", 3);
        Modo modo = idCustoModoTone[0].equals("OFF") ? Modo.OFF : idCustoModoTone[0].equals("ON") ? Modo.ON : null;
        int tone = idCustoModoTone[1].equals("WARM") ? 2 : idCustoModoTone[1].equals("NEUTRAL") ? 1 : idCustoModoTone[1].equals("COLD") ? 0 : -1;
        double tamanho = Double.valueOf(idCustoModoTone[2]);
        SmartBulb ret = new SmartBulb(modo, tone, tamanho);
        return ret;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Lampada, id: ");
        sb.append(this.getID());
        sb.append(", ");
        sb.append(this.getModo());
        sb.append(", Tone: ");
        sb.append(this.getTone());
        sb.append(", Tamanho: ");
        sb.append(String.format("%.2f", this.getTamanho()));
        return sb.toString();
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

    public static SmartBulb parse(String linha, Random random) {
        String[] linhaPartida = linha.split(",", 3); //no maximo 3 parametros
        String toneStr = linhaPartida[0];
        int tone;
        if (toneStr.equals("Neutral")) tone = 1;
        else if (toneStr.equals("Warm")) tone = 2;
        else if (toneStr.equals("Cold")) tone = 0;
        else {
            System.out.println("Input invalido");
            return null;
        }

        double tamanho = Double.valueOf(linhaPartida[1]);
        double consumo = Double.valueOf(linhaPartida[2]);
        Modo modo = random.nextBoolean() ? Modo.ON : Modo.OFF;

        SmartBulb sB = new SmartBulb(modo, tone, tamanho, consumo);
        return sB;
    }

}
