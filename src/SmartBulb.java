package src;

public class SmartBulb extends SmartDevice{
    public SmartBulb(int codigo, double custoInstalacao) {
        super(codigo, custoInstalacao);
    }

    @Override
    public double consumoDiario() {
        //Consumo em funçao de um valor fixo + factor em funçao do tipo de luz que esta a ser emitida
        return -1;
    }
}
