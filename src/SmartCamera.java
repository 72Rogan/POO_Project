package src;

public class SmartCamera extends SmartDevice{
    public SmartCamera(int codigo, double custoInstalacao) {
        super(codigo, custoInstalacao);
    }

    @Override
    public double consumoDiario() {
        //Consumo em funçao do tamanho do ficheiro que geram * a resoluçao da imagem
        return -1;
    }
}
