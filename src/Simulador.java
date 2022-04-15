package src;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Set;
import java.util.TreeSet;

public class Simulador {
    private Set<CasaInteligente> casasInteligentes;
    private LocalDate data;

    public Simulador() {
        this.casasInteligentes = new TreeSet<>();
        this.data = LocalDate.now();
    }

    public Simulador(LocalDate date) {
        this.casasInteligentes = new TreeSet<>();
        this.data = date;
    }

    public void startInterface() {
        //while (true) {

        //}
    }

    public static void main(String[] args) {
        Simulador simulador = new Simulador();
        simulador.startInterface();
    }
}
