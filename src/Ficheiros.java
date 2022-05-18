package src;

import java.io.*;
import java.time.LocalDate;


/*
Esta classe serve para criar presets, ou seja, ficheiros de objetos que ja tem
uma configuracao de casas inteligentes, dispositivos e comercializadores de energia.
 */
public class Ficheiros {
    public static void main(String[] args) {
        Simulador simulador = new Simulador(LocalDate.now());
        Comercializador comercializador1 = new Comercializador(simulador, "EDP",10, 3);
        Comercializador comercializador2 = new Comercializador(simulador, "Energia Top",5, 2);
        CasaInteligente casaInteligente1 = new CasaInteligente(simulador,"Pedro", 55555, comercializador1);
        CasaInteligente casaInteligente2 = new CasaInteligente(simulador,"Josefino Paraquedistas", 11111, comercializador1);
        SmartCamera camera1 = new SmartCamera(simulador,"5", 250, SmartDevice.Modo.ON, 1920, 1080, 50);
        SmartCamera camera2 = new SmartCamera(simulador,"6", 760, SmartDevice.Modo.ON, 1920, 1080, 100);
        SmartBulb smartBulb1 = new SmartBulb(simulador,"7", SmartDevice.Modo.ON, SmartBulb.NEUTRAL, 10);
        SmartSpeaker smartSpeaker1 = new SmartSpeaker(simulador,"8", 300, SmartDevice.Modo.ON,10, "Adidas", "MTV");

        casaInteligente1.addDevice(camera1);
        casaInteligente1.addDevice(smartBulb1);
        casaInteligente1.addDevice(smartSpeaker1);
        casaInteligente2.addDevice(camera2);


        simulador.addCasa(casaInteligente1);
        simulador.addCasa(casaInteligente2);

        simulador.addComercializador(comercializador1);
        simulador.addComercializador(comercializador2);

        //simulador.saltarDias(20);

        try {
            FileOutputStream f = new FileOutputStream(new File("presets/simulador1"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(simulador);

            o.close();
            f.close();

            FileInputStream fi = new FileInputStream(new File("presets/simulador1"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            Simulador simulador1 = (Simulador) oi.readObject();

            simulador1.printFaturas();

            oi.close();
            fi.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro nao encontrado");
        } catch (IOException e) {
            System.out.println("Erro a inicializar a stream");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}