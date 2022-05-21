import org.junit.jupiter.api.Test;
import src.Comercializador;
import src.Simulador;
import src.SmartSpeaker;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static src.SmartDevice.Modo.OFF;

class ComercializadorTest {

    @Test
    public void testeConstrutor(){
        Simulador simulador = new Simulador();
        Comercializador comercializador1 = new Comercializador();
        assertNotNull(comercializador1 , "Erro ao criar a SmartSpeaker");
        comercializador1 = new Comercializador("jose",15,13);
        simulador.addComercializador(comercializador1);
        assertNotNull(comercializador1!=null , "Erro ao criar a SmartSpeaker");
        Comercializador comercializador2 = new Comercializador("jose",3,4,new Random());
        simulador.addComercializador(comercializador2);
        assertNotNull(  comercializador2!=null , "Erro ao criar a SmartSpeaker");
    }
}