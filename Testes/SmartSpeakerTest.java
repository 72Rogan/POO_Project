//package Testes;

import static org.junit.jupiter.api.Assertions.*;
import static src.SmartDevice.Modo.OFF;
import static src.SmartSpeaker.criarSmartSpeaker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import src.SmartCamera;
import src.SmartDevice;
import src.SmartSpeaker;
import src.Simulador;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;


public class SmartSpeakerTest{

    @BeforeEach
    public void setUp(){}

    @AfterEach
    public void tearDown(){}




    @Test
	public void testeConstrutor(){
        Simulador simulador = new Simulador();
		SmartSpeaker smartSpeaker1 = new SmartSpeaker();
		assertNotNull(smartSpeaker1 , "Erro ao criar a SmartSpeaker");
		smartSpeaker1 = new SmartSpeaker(OFF,15,"Panasonic","RUM");
        simulador.addDispositivo(smartSpeaker1);
		assertNotNull(smartSpeaker1!=null , "Erro ao criar a SmartSpeaker");
		SmartSpeaker smartSpeaker2 = new SmartSpeaker(smartSpeaker1);
		assertNotNull(  smartSpeaker1!=null , "Erro ao criar a SmartSpeaker");
	}

    @Test
	public void testGetVolume() {
        Simulador simulador = new Simulador();
        SmartSpeaker smartSpeaker1 = new SmartSpeaker();
        assertEquals(0,smartSpeaker1.getVolume(), "Volume da coluna não é o esperado");
        SmartSpeaker smartSpeaker2 = new SmartSpeaker(OFF,SmartSpeaker.MAX,"Panasonic","RUM");
        simulador.addDispositivo(smartSpeaker2);
        assertEquals(100, smartSpeaker2.getVolume(), "Volume da coluna não é o esperado");
        SmartSpeaker smartSpeaker3 = new SmartSpeaker(OFF,-15,"Panasonic","RUM");
        simulador.addDispositivo(smartSpeaker3);
        assertEquals(0, smartSpeaker3.getVolume(), "Volume da coluna não é o esperado");
        SmartSpeaker smartSpeaker4 = new SmartSpeaker(OFF,110,"Panasonic","RUM");
        assertEquals(100, smartSpeaker4.getVolume(), "Volume da coluna não é o esperado");
        SmartSpeaker smartSpeaker5 = new SmartSpeaker();
        assertEquals(0, smartSpeaker5.getVolume(), "Volume da coluna não é o esperado");
    }

    @Test
    public void testGetMarca() {
        Simulador simulador = new Simulador();
        SmartSpeaker smartSpeaker1 = new SmartSpeaker();
        assertEquals("N/A", smartSpeaker1.getMarca() , "Não é a marca da coluna esperada");
        SmartSpeaker smartSpeaker2 = new SmartSpeaker(OFF,15,"Panasonic","RUM");
        simulador.addDispositivo(smartSpeaker2);
        assertEquals("Panasonic", smartSpeaker2.getMarca(), "Não é a marca da coluna esperada");
        SmartSpeaker smartSpeaker3 = new SmartSpeaker(smartSpeaker2);
        assertEquals("Panasonic", smartSpeaker3.getMarca(), "Não é a marca da coluna esperada");
    }

    @Test
    public void testGetRadio() {
        Simulador simulador = new Simulador();
        SmartSpeaker smartSpeaker1 = new SmartSpeaker();
        assertEquals("N/A", smartSpeaker1.getRadio() , "Não é a radio esperada");
        SmartSpeaker smartSpeaker2 = new SmartSpeaker(OFF,15,"Panasonic","RUM");
        simulador.addDispositivo(smartSpeaker2);
        assertEquals("RUM", smartSpeaker2.getRadio(), "Não é a radio esperada");
        SmartSpeaker smartSpeaker3 = new SmartSpeaker(smartSpeaker2);
        assertEquals("RUM", smartSpeaker3.getRadio(), "Não é a radio esperada");
    }

    @Test
    public void testSetVolume() {
        Simulador simulador = new Simulador();
        SmartSpeaker smartSpeaker1 = new SmartSpeaker(OFF,13,"Panasonic","RUM");
        simulador.addDispositivo(smartSpeaker1);
        smartSpeaker1.volumeUp();
        smartSpeaker1.volumeUp();
        assertEquals(15, smartSpeaker1.getVolume(), "Não é o volume esperado");
        for (int i=0; i<25; i++) smartSpeaker1.volumeUp();
        assertEquals(40, smartSpeaker1.getVolume(), "Não é o volume esperado");
        for (int i=0; i<70; i++) smartSpeaker1.volumeUp();
        assertEquals(100, smartSpeaker1.getVolume(), "Não é o volume esperado");
        for (int i=0; i<70; i++) smartSpeaker1.volumeDown();
        assertEquals(30, smartSpeaker1.getVolume(), "Não é o volume esperado");
        for (int i=0; i<50; i++) smartSpeaker1.volumeDown();
        assertEquals(0, smartSpeaker1.getVolume(), "Não é o volume esperado");
        smartSpeaker1.setVolume(50);
        assertEquals(50,smartSpeaker1.getVolume(), "Não é o volume esperado");
        smartSpeaker1.setVolume(150);
        assertEquals(100,smartSpeaker1.getVolume(), "Não é o volume esperado");
        smartSpeaker1.setVolume(-50);
        assertEquals(0,smartSpeaker1.getVolume(), "Não é o volume esperado");
    }


    @Test
    public static void criarSmartSpeakerTest(){
        InputStream sysInBackup = System.in;
        Simulador simulador = new Simulador();
        Scanner scanner = new Scanner(System.in);
        SmartSpeaker ss = criarSmartSpeaker(scanner);
        String input = "ON,73,JBL,MEGAHITS";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        System.setIn(sysInBackup);
        assertEquals(SmartDevice.Modo.ON, simulador.getDispositivos().get(1).getModo(),"erro");
        //assertEquals(73, simulador.getDispositivos().get(1).getVolume(),"erro");
        //assertEquals("JBL",simulador.getDispositivos().get(1).getMarca(),"erro");
        //assertEquals("MEGAHITS",simulador.getDispositivos().get(1).getRadio(),"erro");
    }

    }


    /*
    Não sei se é suposto fazer testes para o criarSmartSpeaker.
    Se for, o que é que é o scanner?
    */