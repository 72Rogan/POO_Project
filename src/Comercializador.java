package src;

public class Comercializador {
    private double custoDiarioKwh;
    private double fatorImpostos;

    public Comercializador(double custoDiarioKwh, double fatorImpostos) {
        this.custoDiarioKwh = custoDiarioKwh;
        this.fatorImpostos = fatorImpostos;
    }

    public double precoDiaPorDispositivo(SmartDevice smartDevice) {
        return custoDiarioKwh * smartDevice.consumoDiario() * (1+fatorImpostos) * 0.9;
    }

    public double getCustoDiarioKwh() {
        return custoDiarioKwh;
    }

    public void setCustoDiarioKwh(double custoDiarioKwh) {
        this.custoDiarioKwh = custoDiarioKwh;
    }

    public double getFatorImpostos() {
        return fatorImpostos;
    }

    public void setFatorImpostos(double fatorImpostos) {
        this.fatorImpostos = fatorImpostos;
    }
}
