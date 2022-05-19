import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import src.*;

class CasaInteligenteTest {

    @Test
    void testeConstrutor() {
        Simulador simulador = new Simulador();
        Comercializador comercializador = new Comercializador();
        CasaInteligente casaInteligente = new CasaInteligente();
        assertNotNull(casaInteligente, "Erro ao criar a casa inteligente");
        casaInteligente = new CasaInteligente(simulador, "Casa1", 001, comercializador);
        assertNotNull(casaInteligente, "Erro ao criar a casa inteligente");
        CasaInteligente casaInteligente1 = new CasaInteligente(casaInteligente);
        assertNotNull(casaInteligente1, "Erro ao criar a casa inteligente");
    }


    @Test
    void testeSetDeviceOn() {
        Simulador simulador = new Simulador();
        Comercializador comercializador = new Comercializador();
        SmartCamera smartCamera = new SmartCamera();
        CasaInteligente casaInteligente = new CasaInteligente(simulador,"casa1",1,comercializador);
        simulador.addCasa(casaInteligente);
        simulador.addDispositivo(smartCamera);
        casaInteligente.addDevice(smartCamera);
        simulador.addDispositivo(smartCamera);
        casaInteligente.setDeviceOn(smartCamera.getID());
        simulador.addCasa(casaInteligente);
        simulador.addDispositivo(smartCamera);
        assertTrue(SmartDevice.Modo.ON==smartCamera.getModo(), "Erro ao ligar o dispositivo");
    }

}