import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import src.*;

import java.time.LocalDate;
import java.time.Month;


public class FaturaTest {

    @Test
    void testeConstrutor(){
        LocalDate inicio = LocalDate.of(2022, Month.JANUARY,1);
        LocalDate fim = LocalDate.of(2022,Month.JANUARY,31);
        Fatura fatura = new Fatura("Fat1", inicio,fim,5,5);
        assertNotNull(fatura,"Erro ao criar a fatura1");
        Fatura fatura2 = new Fatura(fatura);
        assertNotNull(fatura2, "Erro ao criar a fatura2");
    }

    @Test
    void testeGetPeriodo (){
        LocalDate inicio = LocalDate.of(2022, Month.JANUARY,1);
        LocalDate fim = LocalDate.of(2022,Month.JANUARY,31);
        Fatura fatura = new Fatura("Fat1", inicio,fim,5,5);
        Periodo p = new Periodo(inicio,fim);
        assertTrue(p.equals(fatura.getPeriodo()),"Periodo não é igual");
    }

    @Test
    void testeGetCusto(){
        LocalDate inicio = LocalDate.of(2022, Month.JANUARY,1);
        LocalDate fim = LocalDate.of(2022,Month.JANUARY,31);
        Fatura fatura = new Fatura("Fat1", inicio,fim,5,5);
        assertEquals(5,fatura.getCusto(),"Custo não é igual");
    }

    @Test
    void testeGetConsumo(){
        LocalDate inicio = LocalDate.of(2022, Month.JANUARY,1);
        LocalDate fim = LocalDate.of(2022,Month.JANUARY,31);
        Fatura fatura = new Fatura("Fat1", inicio,fim,5,5);
        assertEquals(5,fatura.getConsumo(),"Consumo não é igual");
    }

}