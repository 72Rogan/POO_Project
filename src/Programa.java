package src;

import static src.Simulador.construirSimulador;

public class Programa {

    public static void main(String[] args) {
        Simulador simulador = construirSimulador("presets/simulador1.txt"); //recebe o caminho para um ficheiro onde esta escrito uma entidade da classe Simulador
        simulador.startInterface();
    }
}
