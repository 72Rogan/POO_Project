import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;


public class SmartSpeakerTest{

    @BeforeEach
    public void setUp(){}

    @AfterEach
    public void tearDown(){}


	Simulador simulador = new Simulador();

    @Test
	public void testeConstrutor(){
		SmartSpeaker smartSpeaker1 = new SmartSpeaker();
		assertTrue(smartSpeaker1!=null , "Erro ao criar a SmartSpeaker");
		SmartSpeaker smartSpeaker1 = new SmartSpeaker(simulador,"smartSpeaker1",5,OFF,15,"Panasonic","RUM");
		assertTrue(smartSpeaker1!=null , "Erro ao criar a SmartSpeaker");
		SmartSpeaker smartSpeaker2 = new SmartSpeaker(smartSpeaker1);
		assertTrue(smartSpeaker1!=null , "Erro ao criar a SmartSpeaker");
	}

    @Test
	public void testGetVolume() {
        SmartSpeaker smartSpeaker1 = new SmartSpeaker();
        assertEquals(0,smartSpeaker1.getVolume(), "Volume da coluna não é o esperado");
        SmartSpeaker smartSpeaker2 = new SmartSpeaker(simulador,"smartSpeaker1",5,OFF,SmartSpeaker.MAX,"Panasonic","RUM");
        assertEquals(100, smartSpeaker2.getVolume(), "Volume da coluna não é o esperado");
        SmartSpeaker smartSpeaker3 = new SmartSpeaker(simulador,"smartSpeaker1",5,OFF,-15,"Panasonic","RUM");
        assertEquals(0, smartSpeaker1.getVolume(), "Volume da coluna não é o esperado");
        SmartSpeaker smartSpeaker4 = new SmartSpeaker(simulador,"smartSpeaker1",5,OFF,110,"Panasonic","RUM");
        assertEquals(100, smartSpeaker4.getVolume(), "Volume da coluna não é o esperado");
        SmartSpeaker smartSpeaker5 = new SmartSpeaker();
        assertEquals(0, smartSpeaker5.getVolume(), "Volume da coluna não é o esperado");
    }

    @Test
    public void testGetMarca() {
        SmartSpeaker smartSpeaker1 = new SmartSpeaker();
        assertEquals("N/A", smartSpeaker1.getMarca() , "Não é a marca da coluna esperada");
        SmartSpeaker smartSpeaker2 = new SmartSpeaker(simulador,"smartSpeaker2",5,OFF,15,"Panasonic","RUM");
        assertEquals("Panasonic", smartSpeaker2.getMarca(), "Não é a marca da coluna esperada");
        SmartSpeaker smartSpeaker3 = new SmartSpeaker(smartSpeaker2);
        assertEquals("Panasonic", smartSpeaker3.getMarca(), "Não é a marca da coluna esperada");
    }

    @Test
    public void testGetRadio() {
        SmartSpeaker smartSpeaker1 = new SmartSpeaker();
        assertEquals("N/A", smartSpeaker1.getRadio() , "Não é a radio esperada");
        SmartSpeaker smartSpeaker2 = new SmartSpeaker(simulador,"smartSpeaker2",5,OFF,15,"Panasonic","RUM");
        assertEquals("RUM", smartSpeaker2.getRadio(), "Não é a radio esperada");
        SmartSpeaker smartSpeaker3 = new SmartSpeaker(smartSpeaker2);
        assertEquals("RUM", smartSpeaker3.getRadio(), "Não é a radio esperada");
    }

    @Test
    public void testSetVolume() {
        SmartSpeaker smartSpeaker1 = new SmartSpeaker(simulador,"smartSpeaker1",5,OFF,13,"Panasonic","RUM");
        smartSpeaker1.volumeUp();
        smartSpeaker1.volumeUp();
        assertEquals(15, smartSpeaker1.getVolume(), "Não é o volume esperado");
        for (int i=0; i<25; i++) smartSpeaker1.volumeUp();
        assertEquals(40, smartSpeaker1.getVolume(), "Não é o volume esperado");

        for (int i=0; i<10; i++) smartSpeaker1.volumeDown();
        assertEquals(30, smartSpeaker1.getVolume(), "Não é o volume esperado");
        smartSpeaker1.setVolume(50);
        assertEquals(50,smartSpeaker1.getVolume(), "Não é o volume esperado");
        smartSpeaker1.setVolume(150);
        assertEquals(0,smartSpeaker1.getVolume(), "Não é o volume esperado");
        smartSpeaker1.setVolume(-50);
        assertEquals(0,smartSpeaker1.getVolume(), "Não é o volume esperado");
    }



    /*
    Não sei se é suposto fazer testes para o criarSmartSpeaker.
    Se for, o que é que é o scanner?
    */










}