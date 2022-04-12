 

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


    public CasaInteligente() {
        // initialise instance variables
        this.morada = "";
        this.devices = new HashMap();
        this.locations = new HashMap();
    }

    public CasaInteligente(String morada) {
        // initialise instance variables
        this.morada = morada;
        this.devices = new HashMap();
        this.locations = new HashMap();
    }

    
    public void setDeviceOn(String devCode) {
        devices.forEach((k,v) -> if(k == devCode) v.setOn(true));
    }
    
    public boolean existsDevice(String id) {
        devices.forEach((k,v) -> if(v.getID == Id) return true;
        else false;
    }
    
    public void addDevice(SmartDevice s) {
        if(existsDevice(s.getID())) System.out.println("Ja existe um dispositio com o mesmo ID");
        else{
            devices.put(s.getID,s);
        }
    }
    
    public SmartDevice getDevice(String s) {
        devices.forEach((k,v) -> if(k == s) return v);
    }


    public void setOn(String s, boolean b) {
    }

    
    public void setAllOn(boolean b) {}   // for each é vom neste mas no resto deve ser melhor mudar

    public void addRoom(String s) {}
    
    public boolean hasRoom(String s) {return false;}
    
    public void addToRoom (String s1, String s2) {}
    
    public boolean roomHasDevice (String s1, String s2) {return false;}
    
}
