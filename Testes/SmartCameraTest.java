import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static src.SmartDevice.Modo.OFF;
import src.Simulador;
import src.SmartCamera;

public class SmartCameraTest{

    @BeforeEach
    public void setUp(){}

    @AfterEach
    public void tearDown(){}


    Simulador simulador = new Simulador();

    @Test
	public void testeConstrutor(){
		SmartCamera smartCamera1 = new SmartCamera();
		assertNotNull(smartCamera1, "Erro ao criar a SmartCamera");
		smartCamera1 = new SmartCamera(simulador);
        assertNotNull(smartCamera1, "Erro ao criar a SmartCamera");
        smartCamera1 = new SmartCamera(simulador,OFF,5,15,15);
        assertNotNull(smartCamera1, "Erro ao criar a SmartCamera");
		SmartCamera SmartCamera2 = new SmartCamera(smartCamera1);
		assertNotNull(SmartCamera2, "Erro ao criar a SmartCamera");
	}

    @Test
	public void testConsumoDiario() {
        SmartCamera smartCamera1 = new SmartCamera();
        assertEquals(0,smartCamera1.getConsumoDiario(), "Valor do consumo Diario não é o esperado");
        SmartCamera smartCamera2 = new SmartCamera(simulador);
        assertEquals(0,smartCamera2.getConsumoDiario(), "Valor do consumo Diario não é o esperado");
        smartCamera2 = new SmartCamera(simulador,OFF,5,15,15);
        assertEquals("1.125", smartCamera2.getConsumoDiario(), "Valor do consumo Diario não é o esperado");
        SmartCamera smartCamera3 = new SmartCamera(smartCamera2);
        assertEquals(1.125, smartCamera3.getConsumoDiario(), "Valor do consumo Diario não é o esperado");
    }

    @Test
    public void testGetWidth() {
        SmartCamera smartCamera1 = new SmartCamera();
        assertEquals(0,smartCamera1.getWidth(), "Valor do width não é o esperado");
        smartCamera1 = new SmartCamera(simulador);
        assertEquals(0,smartCamera1.getWidth(), "Valor do width não é o esperado");
        smartCamera1 = new SmartCamera(simulador,OFF,5,15,15);
        assertEquals(5,smartCamera1.getWidth(), "Valor do width não é o esperado");
        SmartCamera smartCamera2 = new SmartCamera(smartCamera1);
        assertEquals(5,smartCamera2.getWidth(), "Valor do width não é o esperado");
    }

    @Test
    public void testGetHeight() {
        SmartCamera smartCamera1 = new SmartCamera();
        assertEquals(0,smartCamera1.getHeight(), "Valor do height não é o esperado");
        smartCamera1 = new SmartCamera(simulador);
        assertEquals(0,smartCamera1.getHeight(), "Valor do height não é o esperado");
        smartCamera1 = new SmartCamera(simulador,OFF,5,15,15);
        assertEquals(15,smartCamera1.getHeight(), "Valor do height não é o esperado");
        SmartCamera smartCamera2 = new SmartCamera(smartCamera1);
        assertEquals(15,smartCamera2.getHeight(), "Valor do height não é o esperado");
    }

    @Test
    public void testGetTamanhoFicheiro() {
        SmartCamera smartCamera1 = new SmartCamera();
        assertEquals(0,smartCamera1.getTamanhoFicheiro(), "Valor do tamanho do ficheiro não é o esperado");
        smartCamera1 = new SmartCamera(simulador);
        assertEquals(0,smartCamera1.getTamanhoFicheiro(), "Valor do tamanho do ficheiro não é o esperado");
        smartCamera1 = new SmartCamera(simulador,OFF,5,15,15);
        assertEquals(15,smartCamera1.getTamanhoFicheiro(), "Valor do tamanho do ficheiro não é o esperado");
        SmartCamera smartCamera2 = new SmartCamera(smartCamera1);
        assertEquals(15,smartCamera2.getTamanhoFicheiro(), "Valor do tamanho do ficheiro não é o esperado");
    }

    @Test
    public void testSetWidth(){
        SmartCamera smartCamera1 = new SmartCamera();
        smartCamera1.setWidth(15);
        assertEquals(15,smartCamera1.getWidth(), "Valor do width não é o esperado");
    }

    @Test
    public void testSetHeight(){
        SmartCamera smartCamera1= new SmartCamera();
        smartCamera1.setHeight(15);
        assertEquals(15,smartCamera1.getHeight(), "Valor do height não é o esperado");
    }

    @Test
    public void testSetTamanhoFicheiro(){
        SmartCamera smartCamera1 = new SmartCamera();
        smartCamera1.setTamanhoFicheiro(15);
        assertEquals(15,smartCamera1.getTamanhoFicheiro(), "Valor do tamanho do ficheiro não é o esperado");
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