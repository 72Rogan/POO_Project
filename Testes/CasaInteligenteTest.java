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
        casaInteligente = new CasaInteligente( "Casa1", 001, comercializador);
        simulador.addCasa(casaInteligente);
        assertNotNull(casaInteligente, "Erro ao criar a casa inteligente");
        CasaInteligente casaInteligente1 = new CasaInteligente(casaInteligente);
        assertNotNull(casaInteligente1, "Erro ao criar a casa inteligente");
    }


    @Test
    void testeSetDeviceOn() {
        Simulador simulador = new Simulador();
        Comercializador comercializador = new Comercializador();
        SmartCamera smartCamera = new SmartCamera();
        SmartBulb smartBulb = new SmartBulb();
        SmartSpeaker smartSpeaker = new SmartSpeaker();
        CasaInteligente casaInteligente = new CasaInteligente("casa1",1,comercializador);
        simulador.addCasa(casaInteligente);
        simulador.addDispositivo(smartCamera);
        simulador.addDispositivo(smartSpeaker);
        simulador.addDispositivo(smartBulb);
        casaInteligente.addDevice(smartCamera);
        casaInteligente.addDevice(smartSpeaker);
        casaInteligente.addDevice(smartBulb);
        simulador.addDispositivo(smartCamera);
        simulador.addDispositivo(smartSpeaker);
        simulador.addDispositivo(smartBulb);
        casaInteligente.setDeviceOn(smartCamera.getID());
        casaInteligente.setDeviceOn(smartSpeaker.getID());
        casaInteligente.setDeviceOn(smartBulb.getID());
        simulador.addCasa(casaInteligente);
        simulador.addDispositivo(smartCamera);
        simulador.addDispositivo(smartSpeaker);
        simulador.addDispositivo(smartBulb);
        assertTrue(SmartDevice.Modo.ON==smartCamera.getModo(), "Erro ao ligar o dispositivo tipo Camera");
        assertTrue(SmartDevice.Modo.ON==smartSpeaker.getModo(), "Erro ao ligar o dispositivo tipo Speaker");
        assertTrue(SmartDevice.Modo.ON==smartBulb.getModo(), "Erro ao ligar o dispositivo tipo Bulb");
    }
    @Test
    void testeExistsDevice(){
        Simulador simulador = new Simulador();
        Comercializador comercializador = new Comercializador();
        SmartCamera smartCamera = new SmartCamera();
        CasaInteligente casaInteligente = new CasaInteligente("casa1",1,comercializador);
        simulador.addCasa(casaInteligente);
        simulador.addDispositivo(smartCamera);
        casaInteligente.addDevice(smartCamera);
        simulador.addDispositivo(smartCamera);
        simulador.addCasa(casaInteligente);
        simulador.addDispositivo(smartCamera);
        assertTrue(SmartDevice.Modo.ON==smartCamera.getModo(), "Erro ao ligar o dispositivo");
    }
}