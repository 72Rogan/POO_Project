import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Scanner;
import java.time.Month;
import java.util.Scanner;

import src.Simulador;

public class SimuladorTest {

    @Test
    void testeConstrutor() {
        LocalDate time = LocalDate.now();
        Simulador sim = new Simulador();
        assertNotNull(sim, "Erro ao criar o Simulador");
        sim = new Simulador(true);
        assertNotNull(sim, "Erro ao criar o Simulador");
        sim = new Simulador(time);
        assertNotNull(sim, "Erro ao criar o Simulador");
    }
}