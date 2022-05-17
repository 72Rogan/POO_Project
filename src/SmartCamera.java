package src;

import java.util.Scanner;

public class SmartCamera extends SmartDevice{
    // private int res;
    private int width;
    private int height;
    private double tamanhoFicheiro;

    public SmartCamera(){
        super();
        //this.res = 0;
        this.width = 0;
        this.height = 0;
        this.tamanhoFicheiro = 0;
    }

    public SmartCamera(Simulador simulador, String id, double custoInstalacao) {
        super(simulador, id, custoInstalacao);
        this.width = 0;
        this.height = 0;
        this.tamanhoFicheiro = 0;
    }

    public SmartCamera(Simulador simulador, String id, double custoInstalacao, Modo modo,
                       int width, int height, double tamanhoFicheiro) {
        super(simulador, id,custoInstalacao,modo);
        this.width = width;
        this.height = height;
        this.tamanhoFicheiro = tamanhoFicheiro;
    }

    public SmartCamera(SmartCamera c) {
        super(c);
        this.width = c.width;
        this.height = c.height;
        this.tamanhoFicheiro = c.tamanhoFicheiro;
    }

    @Override
    public void calcularConsumoDiario() {
        //Consumo em funcao do tamanho do ficheiro que geram * a resolucao da imagem
        double fator = (this.width * this.height) / 5000000; // /1000 para tornar o numero mais pequeno
        double consumo = 0.5 + fator * (tamanhoFicheiro / 100.0);
        setConsumoDiario(consumo);
    }

    public static SmartCamera criarSmartCamera(Simulador simulador, Scanner scanner) {
        System.out.println("Escreve no formato ID-Custo-Modo-Largura-Altura-TamanhoFicheiro / Exemplo: Cam1-700-ON-1920-1080-50");
        String input = scanner.next();
        String[] idCustoModoLarguraAlturaTamanho = input.split("-", 6);
        String id = idCustoModoLarguraAlturaTamanho[0];
        int custo = Integer.valueOf(idCustoModoLarguraAlturaTamanho[1]);
        Modo modo = idCustoModoLarguraAlturaTamanho[2].equals("OFF") ? Modo.OFF : idCustoModoLarguraAlturaTamanho[2].equals("ON") ? Modo.ON : null;
        int largura = Integer.valueOf(idCustoModoLarguraAlturaTamanho[3]);
        int altura = Integer.valueOf(idCustoModoLarguraAlturaTamanho[4]);
        double tamanho = Double.valueOf(idCustoModoLarguraAlturaTamanho[5]);
        SmartCamera ret = new SmartCamera(simulador, id, custo, modo, largura, altura, tamanho);
        return ret;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Camera, id: ")
                .append(this.getID())
                .append(", ")
                .append(this.getModo())
                .append(", ")
                .append(this.width)
                .append("x")
                .append(this.height);
        return sb.toString();
    }

    @Override
    public SmartDevice clone() {
        return new SmartCamera(this);
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
