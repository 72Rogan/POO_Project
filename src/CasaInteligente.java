package src;

/*********************************************************************************/
/** DISCLAIMER: Este codigo foi criado e alterado durante as aulas praticas      */
/** de POO. Representa uma solucao em construcao, com base na materia leccionada */
/** ate ao momento da sua elaboracao, e resulta da discussao e experimentacao    */
/** durante as aulas. Como tal, nao devera ser visto como uma solucao canonica,  */
/** ou mesmo acabada. e disponibilizado para auxiliar o processo de estudo.      */
/** Os alunos sao encorajados a testar adequadamente o codigo fornecido e a      */
/** procurar solucoes alternativas, a medida que forem adquirindo mais           */
/** conhecimentos de POO.                                                        */
/*********************************************************************************/

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;


/**
 * A CasaInteligente faz a gestao dos SmartDevices que existem e dos
 * espacos (as salas) que existem na casa.

public class CasaInteligente {
   
    private String morada;
    private Map<String, SmartDevice> devices; // identificador -> SmartDevice
    private Map<String, List<String>> locations; // Espaco -> Lista codigo dos devices

    /**
     * Constructor for objects of class CasaInteligente
     */

// mudar o metodo de procura pelos maps usei forEach que era o que sabia usar melhor mas existem metodos mais eficientes
// exceto para a funcao turnAllOn acho que o forEach e bom nessa
public class CasaInteligente extends Change<CasaInteligente> implements Serializable{
    private Simulador simulador;
    private Comercializador comercializador;
    private String nome;
    private int nif;
    private Map<String, SmartDevice> devices;
    private Map<String, List<String>> locations;
    private List<Fatura> faturas;

    public CasaInteligente() {
        // initialise instance variables
        super();
        this.simulador = null;
        this.nome = "N/A";
        this.nif = -1;
        this.devices = new HashMap();
        this.locations = new HashMap();
        this.comercializador = null;
        this.faturas = new ArrayList<>();
    }

    public CasaInteligente(Simulador simulador,String nome, int nif, Comercializador comercializador) {
        // initialise instance variables
        super();
        this.simulador = simulador;
        this.nome = nome;
        this.nif = nif;
        this.devices = new HashMap();
        this.locations = new HashMap();
        this.comercializador = comercializador;
        this.faturas = new ArrayList<>();

        this.simulador.addCasa(this);
    }

    public CasaInteligente(CasaInteligente casaInteligente) {
        super.toChange = casaInteligente.toChange;
        this.simulador = casaInteligente.simulador;
        this.nome = casaInteligente.nome;
        this.nif = casaInteligente.nif;
        this.devices = casaInteligente.devices;
        this.locations = casaInteligente.locations;
        this.comercializador = casaInteligente.comercializador;
        this.faturas = casaInteligente.faturas;
    }

    public void setDeviceOn(String devCode) {
        this.devices.get(devCode).turnOn();
    }

    public boolean existsDevice(String id) {
        return this.devices.keySet().contains(id);
    }

    public void addDevice(SmartDevice s) {
        this.devices.put(s.getID(), s);
        s.setCasa(this);
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

    public void saltarParaData(LocalDate data) {

        if (this.devices.isEmpty()) {
            //A casa nao tem dispositivos, para esta funçao
            System.out.println("A casa " + this.toString() + " nao tem dispositivos");
            return;
        }

        double consumo = 0;
        double custo = 0;

        LocalDate inicio = this.devices.values().stream().findAny().get().getLastChange();

        LocalDate fim = data;

        for (SmartDevice smartDevice: this.devices.values()) {
            consumo += smartDevice.consumoAte(fim);
            custo += smartDevice.custoAte(this.comercializador, fim);
            smartDevice.setLastChange(simulador.getData());
        }
        faturar(inicio,fim,consumo,custo);
        //depois de geradas as faturas, executam-se as mudancas pendentes necessarias
        this.change();
    }

    public void faturar(LocalDate inicio, LocalDate fim, double consumo, double custo) {
        Fatura fatura = new Fatura(this.nome,inicio,fim,consumo,custo);
        this.faturas.add(fatura);
        this.comercializador.addFatura(fatura);
    }

    public static CasaInteligente escolherCasa(List<CasaInteligente> listaCasas, Scanner scanner) {
        System.out.println("Escolhe uma casa");
        for (int i=0; i<listaCasas.size(); i++) {
            System.out.println(i + " - " + listaCasas.get(i).toString());
        }
        int escolha = scanner.nextInt(); //assume-se que escolheu uma opcao valida
        return listaCasas.get(escolha);
    }

    public static CasaInteligente parse(Simulador simulador, String linha) {
        String[] linhaPartida = linha.split(",", 3); //no maximo 3 parametros
        String nome = linhaPartida[0];
        int nif = Integer.valueOf(linhaPartida[1]);
        String nomeComercializador = linhaPartida[2];
        Comercializador c = simulador.getComercializador(nomeComercializador);

        CasaInteligente casa = new CasaInteligente(simulador, nome, nif, c);
        return casa;
    }

    public Fatura getFatura(Periodo periodo) {
        for (Fatura fatura: this.faturas) {
            if (fatura.getPeriodo().equals(periodo)) {
                return fatura;
            }
        }
        System.out.println("Nao encontrou fatura neste periodo");
        return null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        //this.nome = nome;
        //Coloca o nome nas mudancas pendentes
        if (super.toChange == null) createToChange();
        this.toChange.nome = nome;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        //this.nif = nif;
        //Coloca o nif nas mudancas pendentes
        if (super.toChange == null) createToChange();
        this.toChange.nif = nif;
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

    public Comercializador getComercializador() {
        return comercializador;
    }

    public void setComercializador(Comercializador comercializador) {
        //this.comercializador = comercializador;

        //coloca o novo comercializador nas mudancas pendentes
        if (super.toChange == null) createToChange();
        super.toChange.comercializador = comercializador;
    }

    public List<Fatura> getFaturas() {
        return faturas;
    }

    public void setFaturas(List<Fatura> faturas) {
        this.faturas = faturas;
    }

    public CasaInteligente clone() {
        return new CasaInteligente(this);
    }

    @Override
    public void createToChange() {
        CasaInteligente casaInteligente = new CasaInteligente();
        super.setToChange(casaInteligente);
    }

    @Override
    public void change() {
        CasaInteligente casaInteligente = getToChange();
        if (casaInteligente != null) { //existem mudancas pendentes
            if (casaInteligente.nome != "N/A") this.nome = casaInteligente.nome;
            if (casaInteligente.nif != -1) this.nif = casaInteligente.nif;
            if (casaInteligente.comercializador != null) this.comercializador = casaInteligente.comercializador;
            super.toChange = null; //resetar as mudancas pendentes
        }
        for (SmartDevice sD: this.devices.values()) {
            sD.change();
        }
    }

    public String toString() {
        return "Casa de " + this.nome;
    }

    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || this.getClass() != o.getClass()) return false;
        CasaInteligente casaInteligente = (CasaInteligente) o;
        return this.nome.equals(casaInteligente.nome) && this.nif == casaInteligente.nif;
    }
}
