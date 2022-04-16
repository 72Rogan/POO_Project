package src;

import java.io.Serializable;

public class Comercializador extends Change<Comercializador> implements Serializable {
    private double custoDiarioKwh;
    private double fatorImpostos;

    public Comercializador() {
        super();
        this.custoDiarioKwh = -1;
        this.fatorImpostos = -1;
    }

    public Comercializador(double custoDiarioKwh, double fatorImpostos) {
        super();
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
        if (this.toChange == null) createToChange();
        this.toChange.custoDiarioKwh = custoDiarioKwh;
    }

    public double getFatorImpostos() {
        return fatorImpostos;
    }

    public void setFatorImpostos(double fatorImpostos) {
        if (this.toChange == null) createToChange();
        this.toChange.fatorImpostos = fatorImpostos;
    }

    @Override
    public void createToChange() {
        Comercializador comercializador = new Comercializador();
        super.setToChange(comercializador);
    }

    @Override
    public void change() {
        Comercializador comToChange = super.getToChange();
        if (comToChange != null) {
            if (comToChange.fatorImpostos != -1) this.fatorImpostos = comToChange.fatorImpostos;
            if (comToChange.custoDiarioKwh != -1) this.custoDiarioKwh = comToChange.custoDiarioKwh;
            super.toChange = null;
        }
    }
}
