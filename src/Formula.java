package src;

import java.io.Serializable;
import java.util.Random;

public interface Formula extends Serializable {
    int numFormulas = 4;
    double precoDiario(SmartDevice smartDevice, double custoDiarioKwh, double fatorImpostos);

    static Formula formula0() {
        return (sD, custo, impostos) -> {
            return custo * sD.getConsumoDiario() * (1+impostos) * 0.9;
        };
    }

    static Formula formula1() {
        return (sD, custo, impostos) -> {
            return custo * sD.getConsumoDiario() + (impostos*0.05);
        };
    }

    static Formula formula2() {
        return (sD, custo, impostos) -> {
            return 1 + custo * sD.getConsumoDiario();
        };
    }

    static Formula formula3() {
        return (sD, custo, impostos) -> {
            double custoBase = custo * sD.getConsumoDiario();
            return impostos > 5 ? custoBase : custoBase + 0.5;
        };
    }

    static Formula getFormula(Random random) {
        int numFormula = random.nextInt(numFormulas);
        if (numFormula == 0) return formula0();
        else if (numFormula == 1) return formula1();
        else if (numFormula == 2) return formula2();
        else return formula3();
    }
}
