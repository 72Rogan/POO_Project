import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import src.*;

import java.time.LocalDate;
import java.time.Month;

class PeriodoTest {

    @Test
    void testeConstrutor(){
        LocalDate inicio = LocalDate.of(2022, Month.JANUARY,1);
        LocalDate fim = LocalDate.of(2022,Month.JANUARY,31);
        Periodo p = new Periodo(inicio,fim);
        assertNotNull(p, "Erro ao criar o periodo");
    }

    @Test
    void testeEquals(){
        LocalDate inicio = LocalDate.of(2022, Month.JANUARY,1);
        LocalDate fim = LocalDate.of(2022,Month.JANUARY,31);
        Periodo p1 = new Periodo(inicio,fim);
        Periodo p2 = new Periodo(inicio,fim);
        assertTrue(p1.equals(p2),"Os objetos não são iguais");
    }

    @Test
    void testeToString(){
        LocalDate inicio = LocalDate.of(2022, Month.JANUARY,1);
        LocalDate fim = LocalDate.of(2022,Month.JANUARY,31);
        Periodo p1 = new Periodo(inicio,fim);
        String s = "Periodo de " + inicio + " a " + fim;
        assertTrue(s.equals(p1.toString()),"Strings não são iguais");
    }


}