import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;


public class SmartBulbTest{


    @BeforeEach
    public void setUp(){}

    @AfterEach
    public void tearDown(){}


    Simulador simulador = new Simulador();

    @Test
    public void testeConstrutor(){
        SmartBulb SmartBulb1 = new SmartBulb();
        assertTrue(SmartBulb1!=null, "Erro ao criar a SmartBulb");
        SmartBulb SmartBulb1 = new SmartBulb(simulador,"SmartBulb1",0);
        assertTrue(SmartBulb1!=null, "Erro ao criar a SmartBulb");
        SmartBulb SmartBulb1 = new SmartBulb(simulador,"SmartBulb1",5,OFF,0,15);
        assertTrue(SmartBulb1!=null, "Erro ao criar a SmartBulb");
        SmartBulb SmartBulb2 = new SmartBulb(SmartBulb1);
        assertTrue(SmartBulb2!=null, "Erro ao criar a SmartBulb");
    }
    /*
    public void testConsumoDiario() {
        SmartBulb SmartBulb1 = new SmartBulb();
        assertEquals(0,SmartBulb1.consumoDiario());
        SmartBulb SmartBulb2 = new SmartBulb(simulador,"SmartBulb1",5);
        assertEquals(0,SmartBulb2.consumoDiario());
        SmartBulb SmartBulb2 = new SmartBulb(simulador,"SmartBulb1",5,OFF,5,15,15);
        assertEquals(1.125, SmartBulb2.consumoDiario());
        SmartBulb SmartBulb3 = new SmartBulb(SmartBulb2);
        assertEquals(1.125, SmartBulb3.consumoDiario());
    }
    */

    @Test
    public void testSetTone(){
        SmartBulb SmartBulb1 = new SmartBulb();
        SmartBulb1.setTone(5);
        assertEquals(2,SmartBulb1.getTone(), "Valor da tonalidade não é o esperado");
        SmartBulb SmartBulb1 = new SmartBulb();
        SmartBulb1.setTone(-1);
        assertEquals(0,SmartBulb1.getTone(), "Valor da tonalidade não é o esperado");
    }

    @Test
    public void testSetTamanho(){
        SmartBulb SmartBulb1 = new SmartBulb();
        SmartBulb1.setTamanho(15);
        assertEquals(15,SmartBulb1.getTamanho(), "Valor do tamanho não é o esperado");
    }


    @Test
    public void testGetTone() {
        SmartBulb SmartBulb1 = new SmartBulb();
        assertEquals(1,SmartBulb1.getTone(), "Valor da tonalidade não é o esperado");
        SmartBulb SmartBulb1 = new SmartBulb(simulador,"SmartBulb1",2);
        assertEquals(2,SmartBulb1.getTone(), "Valor da tonalidade não é o esperado");
        SmartBulb SmartBulb1 = new SmartBulb(simulador,"SmartBulb1",5,OFF,0,15);
        assertEquals(0,SmartBulb1.getTone(), "Valor da tonalidade não é o esperado");
        SmartBulb SmartBulb2 = new SmartBulb(SmartBulb1);
        assertEquals(0,SmartBulb2.getTone(), "Valor da tonalidade não é o esperado");
    }

    @Test
    public void testGetTamanho() {
 //       SmartBulb SmartBulb1 = new SmartBulb();
 //       assertEquals(1,SmartBulb1.getTone());
 //       SmartBulb SmartBulb1 = new SmartBulb(simulador,"SmartBulb1",2);
 //       assertEquals(2,SmartBulb1.getTone());
        SmartBulb SmartBulb1 = new SmartBulb(simulador,"SmartBulb1",5,OFF,0,15);
        assertEquals(15,SmartBulb1.getTamanho(),  "Valor do tamanho não é o esperado");
        SmartBulb SmartBulb2 = new SmartBulb(SmartBulb1);
        assertEquals(15,SmartBulb2.getTone(),  "Valor do tamanho não é o esperado");
    }
    


    /*
    Não sei se é suposto fazer testes para o criarSmartBulb.
    Se for, o que é que é o scanner?
    */
}