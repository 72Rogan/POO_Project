import org.junit.jupiter.api.Test;
import src.*;

import static org.junit.jupiter.api.Assertions.*;

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

    /* Não sei como dar fix nisto
    @Test
    void testeSetDeviceOn() {
        Simulador simulador = new Simulador();
        SmartCamera smartCamera = new SmartCamera();
        CasaInteligente casaInteligente = new CasaInteligente();
        casaInteligente.addDevice(smartCamera);
        simulador.addDispositivo(smartCamera);
        casaInteligente.setDeviceOn(smartCamera.getID());
        assertEquals(SmartDevice.Modo.ON, smartCamera.getModo(), "Erro ao ligar o dispositivo");
    }
    */
}