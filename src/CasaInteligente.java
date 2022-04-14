package src;

/*********************************************************************************/
/** DISCLAIMER: Este código foi criado e alterado durante as aulas práticas      */
/** de POO. Representa uma solução em construção, com base na matéria leccionada */ 
/** até ao momento da sua elaboração, e resulta da discussão e experimentação    */
/** durante as aulas. Como tal, não deverá ser visto como uma solução canónica,  */
/** ou mesmo acabada. É disponibilizado para auxiliar o processo de estudo.      */
/** Os alunos são encorajados a testar adequadamente o código fornecido e a      */
/** procurar soluções alternativas, à medida que forem adquirindo mais           */
/** conhecimentos de POO.                                                        */
/*********************************************************************************/

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.HashMap;


/**
 * A CasaInteligente faz a gestão dos SmartDevices que existem e dos 
 * espaços (as salas) que existem na casa.

public class CasaInteligente {
   
    private String morada;
    private Map<String, SmartDevice> devices; // identificador -> SmartDevice
    private Map<String, List<String>> locations; // Espaço -> Lista codigo dos devices

    /**
     * Constructor for objects of class CasaInteligente
     */

// mudar o metodo de procura pelos maps usei forEach que era o que sabia usar melhor mas existem metodos mais eficientes
// exceto para a funçao turnAllOn acho que o forEach é bom nessa
public class CasaInteligente {
    private Comercializador comercializador;
    private String nome;
    private int nif;
    private Map<String, SmartDevice> devices;
    private Map<String, List<String>> locations;

    public CasaInteligente() {
        // initialise instance variables
        this.nome = "N/A";
        this.nif = -1;
        this.devices = new HashMap();
        this.locations = new HashMap();
        this.comercializador = null;
    }

    public CasaInteligente(String nome, int nif, Comercializador comercializador) {
        // initialise instance variables
        this.nome = nome;
        this.nif = nif;
        this.devices = new HashMap();
        this.locations = new HashMap();
        this.comercializador = comercializador;
    }


    public void setDeviceOn(String devCode) {
        this.devices.get(devCode).turnOn();
    }

    public boolean existsDevice(String id) {
        return this.devices.keySet().contains(id);
    }

    public void addDevice(SmartDevice s) {
        this.devices.put(s.getID(), s.clone());
    }

    public SmartDevice getDevice(String s) {
        return this.devices.get(s).clone();
    }


    public void setOn(String s, boolean b) {
        if (this.devices.get(s) != null) {
            this.devices.get(s).setOn(b); //assume que nao recebe um clone
            //se receber um clone e suposto meter de novo?
        }
    }


    public void setAllOn(boolean b) {
        for (SmartDevice smartDevice: this.devices.values()) {
            smartDevice.setOn(b);
        }
    }

    public void setAllOn(String divisao, boolean b) {
        List<String> deviceList = locations.get(divisao);
        for (String str: deviceList) {
            SmartDevice dev = devices.get(str);
            if (dev != null) {
                dev.setOn(b);
            }
        }
    }

    public void addRoom(String s) {
        this.locations.put(s, new ArrayList<>());
    }

    public boolean hasRoom(String s) {
        return this.locations.keySet().contains(s);
    }

    public void addToRoom (String s1, String s2) {
        if (this.locations.get(s1) == null) {
            System.out.println("A tentar adicionar a room que nao existe");
        } else {
            List<String> codigoDevices = this.locations.get(s1);
            codigoDevices.add(s2);
        }
    }

    public boolean roomHasDevice (String s1, String s2) {
        List<String> devices = this.locations.get(s1);
        if (devices != null && !devices.isEmpty()) {
            return devices.contains(s2);
        }
        return false;
    }

    public double precoPorDia() {
        double ret = 0;
        for (SmartDevice smartDevice: devices.values()) {
            ret += comercializador.precoDiaPorDispositivo(smartDevice);
        }
        return ret;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public Map<String, SmartDevice> getDevices() {
        return devices;
    }

    public void setDevices(Map<String, SmartDevice> devices) {
        this.devices = devices;
    }

    public Map<String, List<String>> getLocations() {
        return locations;
    }

    public void setLocations(Map<String, List<String>> locations) {
        this.locations = locations;
    }
}
