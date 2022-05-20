package src;

import java.util.Scanner;

public class SmartCamera extends SmartDevice{
    private int width;
    private int height;
    private double tamanhoFicheiro;

    public SmartCamera(){
        super();
        //this.res = 0;
        this.width = 0;
        this.height = 0;
        this.tamanhoFicheiro = 0;
        calcularConsumoDiario();
    }

    public SmartCamera(Simulador simulador) {
        super(simulador, 700);
        this.width = 0;
        this.height = 0;
        this.tamanhoFicheiro = 0;
        calcularConsumoDiario();
    }

    public SmartCamera(Simulador simulador, Modo modo,
                       int width, int height, double tamanhoFicheiro) {
        super(simulador, 700,modo);
        this.width = width;
        this.height = height;
        this.tamanhoFicheiro = tamanhoFicheiro;
        calcularConsumoDiario();
    }

    public SmartCamera(Simulador simulador, Modo modo,
                       int width, int height, double tamanhoFicheiro, double consumoDiario) {
        super(simulador, 700,modo);
        this.width = width;
        this.height = height;
        this.tamanhoFicheiro = tamanhoFicheiro;
        setConsumoDiario(consumoDiario);
    }

    public SmartCamera(SmartCamera c) {
        this(c, c.getSimulador());
    }

    public SmartCamera(SmartCamera c, Simulador s) {
        super(c, s);
        this.width = c.width;
        this.height = c.height;
        this.tamanhoFicheiro = c.tamanhoFicheiro;
    }

    @Override
    public void calcularConsumoDiario() {
        //Consumo em funcao do tamanho do ficheiro que geram * a resolucao da imagem
        double fator = (this.width * this.height) / 5000000.0; // /1000 para tornar o numero mais pequeno
        double consumo = 0.5 + fator * (tamanhoFicheiro / 100.0);
        setConsumoDiario(consumo);
    }

    public static SmartCamera parse(Simulador simulador, String linha) {
        String[] linhaPartida = linha.split(",", 3); //no maximo 3 parametros
        String resolucaoStr = linhaPartida[0];
        String resolucao = resolucaoStr.substring(1, resolucaoStr.length()-1);
        String[] resPartida = resolucao.split("x", 2);
        int width = Integer.valueOf(resPartida[0]);
        int height = Integer.valueOf(resPartida[1]);

        double tamanhoFicheiro = Double.valueOf(linhaPartida[1]);
        double consumo = Double.valueOf(linhaPartida[2]);

        SmartCamera sC = new SmartCamera(simulador, Modo.ON, width, height, tamanhoFicheiro, consumo);
        return sC;
    }

    public static SmartCamera criarSmartCamera(Simulador simulador, Scanner scanner) {
        System.out.println("Escreve no formato Modo,Largura,Altura,TamanhoFicheiro / Exemplo: ON,1920,1080,50");
        String input = scanner.nextLine();
        String[] idCustoModoLarguraAlturaTamanho = input.split(",", 4);
        Modo modo = idCustoModoLarguraAlturaTamanho[0].equals("OFF") ? Modo.OFF : idCustoModoLarguraAlturaTamanho[0].equals("ON") ? Modo.ON : null;
        int largura = Integer.valueOf(idCustoModoLarguraAlturaTamanho[1]);
        int altura = Integer.valueOf(idCustoModoLarguraAlturaTamanho[2]);
        double tamanho = Double.valueOf(idCustoModoLarguraAlturaTamanho[3]);
        SmartCamera ret = new SmartCamera(simulador, modo, largura, altura, tamanho);
        return ret;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Camera, id: ")
                .append(this.getID())
                .append(", ")
                .append(this.getModo())
                .append(", (")
                .append(this.width)
                .append("x")
                .append(this.height)
                .append(")");
        return sb.toString();
    }

    @Override
    public SmartDevice clone() {
        return new SmartCamera(this);
    }

    public SmartDevice clone(Simulador s) {
        return new SmartCamera(this, s);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getTamanhoFicheiro() {
        return tamanhoFicheiro;
    }

    public void setTamanhoFicheiro(double tamanhoFicheiro) {
        this.tamanhoFicheiro = tamanhoFicheiro;
    }

}
