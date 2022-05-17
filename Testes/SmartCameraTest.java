import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class SmartCameraTest{

    @BeforeEach
    public void setUp(){}

    @AfterEach
    public void tearDown(){}


    Simulador simulador = new Simulador();

    @Test
	public void testeConstrutor(){
		SmartCamera SmartCamera1 = new SmartCamera();
		assertTrue(SmartCamera1!=null, "Erro ao criar a SmartCamera");
		SmartCamera SmartCamera1 = new SmartCamera(simulador,"SmartCamera1",5);
		assertTrue(SmartCamera1!=null, "Erro ao criar a SmartCamera");
        SmartCamera SmartCamera1 = new SmartCamera(simulador,"SmartCamera1",5,OFF,5,15,15);
        assertTrue(SmartCamera1!=null, "Erro ao criar a SmartCamera");
		SmartCamera SmartCamera2 = new SmartCamera(SmartCamera1);
		assertTrue(SmartCamera2!=null, "Erro ao criar a SmartCamera");
	}

    @Test
	public void testConsumoDiario() {
        SmartCamera SmartCamera1 = new SmartCamera();
        assertEquals(0,SmartCamera1.consumoDiario(), "Valor do consumo Diario não é o esperado");
        SmartCamera SmartCamera2 = new SmartCamera(simulador,"SmartCamera1",5);
        assertEquals(0,SmartCamera2.consumoDiario(), "Valor do consumo Diario não é o esperado");
        SmartCamera SmartCamera2 = new SmartCamera(simulador,"SmartCamera1",5,OFF,5,15,15);
        assertEquals(1.125, SmartCamera2.consumoDiario(), "Valor do consumo Diario não é o esperado");
        SmartCamera SmartCamera3 = new SmartCamera(SmartCamera2);
        assertEquals(1.125, SmartCamera3.consumoDiario(), "Valor do consumo Diario não é o esperado");
    }

    @Test
    public void testGetWidth() {
        SmartCamera SmartCamera1 = new SmartCamera();
        assertEquals(0,SmartCamera1.getWidth(), "Valor do width não é o esperado");
        SmartCamera SmartCamera1 = new SmartCamera(simulador,"SmartCamera1",5);
        assertEquals(0,SmartCamera1.getWidth(), "Valor do width não é o esperado");
        SmartCamera SmartCamera1 = new SmartCamera(simulador,"SmartCamera1",5,OFF,5,15,15);
        assertEquals(5,SmartCamera1.getWidth(), "Valor do width não é o esperado");
        SmartCamera SmartCamera2 = new SmartCamera(SmartCamera1);
        assertEquals(5,SmartCamera2.getWidth(), "Valor do width não é o esperado");
    }

    @Test
    public void testGetHeight() {
        SmartCamera SmartCamera1 = new SmartCamera();
        assertEquals(0,SmartCamera1.getHeight(), "Valor do height não é o esperado");
        SmartCamera SmartCamera1 = new SmartCamera(simulador,"SmartCamera1",5);
        assertEquals(0,SmartCamera1.getHeight(), "Valor do height não é o esperado");
        SmartCamera SmartCamera1 = new SmartCamera(simulador,"SmartCamera1",5,OFF,5,15,15);
        assertEquals(15,SmartCamera1.getHeight(), "Valor do height não é o esperado");
        SmartCamera SmartCamera2 = new SmartCamera(SmartCamera1);
        assertEquals(15,SmartCamera2.getHeight(), "Valor do height não é o esperado");
    }

    @Test
    public void testGetTamanhoFicheiro() {
        SmartCamera SmartCamera1 = new SmartCamera();
        assertEquals(0,SmartCamera1.getTamanhoFicheiro(), "Valor do tamanho do ficheiro não é o esperado");
        SmartCamera SmartCamera1 = new SmartCamera(simulador,"SmartCamera1",5);
        assertEquals(0,SmartCamera1.getTamanhoFicheiro(), "Valor do tamanho do ficheiro não é o esperado");
        SmartCamera SmartCamera1 = new SmartCamera(simulador,"SmartCamera1",5,OFF,5,15,15);
        assertEquals(15,SmartCamera1.getTamanhoFicheiro(), "Valor do tamanho do ficheiro não é o esperado");
        SmartCamera SmartCamera2 = new SmartCamera(SmartCamera1);
        assertEquals(15,SmartCamera2.getTamanhoFicheiro(), "Valor do tamanho do ficheiro não é o esperado");
    }

    @Test
    public void testSetWidth(){
        SmartCamera SmartCamera1 = new SmartCamera();
        SmartCamera1.setWidth(15);
        assertEquals(15,SmartCamera1.getWidth(), "Valor do width não é o esperado");
    }

    @Test
    public void testSetHeight(){
        SmartCamera SmartCamera1 = new SmartCamera();
        SmartCamera1.setHeight(15);
        assertEquals(15,SmartCamera1.getHeight(), "Valor do height não é o esperado");
    }

    @Test
    public void testSetTamanhoFicheiro(){
        SmartCamera SmartCamera1 = new SmartCamera();
        SmartCamera1.setTamanhoFicheiro(15);
        assertEquals(15,SmartCamera1.getTamanhoFicheiro(), "Valor do tamanho do ficheiro não é o esperado");
    }

    /* Acho que nao vale a pena estar a testar o toString()
    public void testToString(){
        SmartCamera SmartCamera1 = new SmartCamera(simulador,"SmartCamera1",5,OFF,5,15,15);
        assertEquals("Camara, id:")
    }
    */



    /*
    Não sei se é suposto fazer testes para o criarSmartCamera.
    Se for, o que é que é o scanner?
    */
}