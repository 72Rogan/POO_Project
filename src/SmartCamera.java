package src;

public class SmartCamera extends SmartDevice{
    // private int res;
    private int[2] res; // resolução é em pixeis ou em por exemplo 1280x720 ?
    private double espaco;

    public SmartCamera(){
        super();
        //this.res = 0;
        this.res = [0,0];
        this.espaco = 0;
    }

    public SmartCamera(int id, double custoInstalacao) {
        super(id, custoInstalacao);
    }

    @Override
    public double consumoDiario() {
        //Consumo em funçao do tamanho do ficheiro que geram * a resoluçao da imagem
        return -1;
    }
}
