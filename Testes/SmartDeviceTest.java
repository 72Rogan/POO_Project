//package Testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import src.Simulador;
import src.SmartDevice;

public class SmartDeviceTest {

    @BeforeEach
    public void setUp(){}

    @AfterEach
    public void tearDown(){}

    @Test
    public void testeConstrutor(){
        Simulador simulador = new Simulador();
        SmartDevice smartDevice1 = new SmartDevice();
        assertNotNull(smartDevice1, "Erro ao criar a SmartBulb");
        smartDevice1 = new SmartDevice(simulador, "smartDevide1", 5);


    }



















}