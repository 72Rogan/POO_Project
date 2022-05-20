package src;

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
public class CasaInteligente implements Serializable, PendingChanges{
    private Simulador simulador;
    private Comercializador comercializador;
    private Comercializador comercializadorToChange;
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
        this.comercializadorToChange = null;
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
        this.comercializadorToChange = null;
        this.faturas = new ArrayList<>();

        this.simulador.addCasa(this);
    }

    public CasaInteligente(CasaInteligente casaInteligente) {
        this.simulador = casaInteligente.simulador;
        this.nome = casaInteligente.nome;
        this.nif = casaInteligente.nif;
        this.devices = casaInteligente.devices;
        this.locations = casaInteligente.locations;
        this.comercializador = casaInteligente.comercializador;
        this.comercializadorToChange = casaInteligente.comercializadorToChange;
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

    /*
    Recebe uma lista de divisoes em String e mostra ao utilizador para escolher uma
     */
    public String escolherDivisao(Scanner scanner) {
        Set<String> divisoes = this.locations.keySet();
        System.out.println("Divisoes disponiveis");
        for (String div: divisoes) {
            System.out.println(div);
        }
        System.out.println("Escreva o nome da divisao que quer");
        String escolha = scanner.nextLine(); //assume-se que escolheu uma opcao valida
        if (divisoes.contains(escolha)) {
            return escolha;
        } else {
            System.out.println("Essa Divisao nao existe");
            return "";
        }
    }

    /*
    Recebe uma string que representa a divisao, e um boleano que indica se e pra ligar ou desligar os
    dispositivos dessa divisao
     */
    public void setAllOn(String divisao, boolean b) {
        if (!locations.containsKey(divisao)) return; //nao contem esta divisao
        List<String> deviceList = locations.get(divisao);
        for (String str: deviceList) {
            SmartDevice dev = devices.get(str);
            if (dev != null) {
                dev.setOn(b);
            }
        }
    }

    public void listarDispositivos() {
        for (SmartDevice sd: this.devices.values()) {
            System.out.println(sd);
        }
    }

    public String conteudo() {
        StringBuilder sB = new StringBuilder();
        for (String divisao: this.locations.keySet()) {
            List<String> dispIds = this.locations.get(divisao);
            sB.append("Divisao: ");
            sB.append(divisao);
            sB.append("\n");
            if (!dispIds.isEmpty()) {
                for (String id: dispIds) {
                    SmartDevice sd = this.devices.get(id);
                    if (sd != null) {
                        //dispositivo existe
                        sB.append(sd.toString());
                        sB.append("\n");
                    }
                }
            }
        }
        return sB.toString();
    }

    public boolean existsRooms() {
        return !this.locations.isEmpty();
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

    public void saltarParaData(LocalDate dataFinal) {

        if (this.devices.isEmpty()) {
            //A casa nao tem dispositivos, para esta funçao
            System.out.println("A casa " + this.toString() + " nao tem dispositivos");
            return;
        }

        double consumo = 0;
        double custo = 0;

        LocalDate inicio = simulador.getData();

        for (SmartDevice smartDevice: this.devices.values()) {
            consumo += smartDevice.consumoAte(dataFinal);
            custo += smartDevice.custoAte(this.comercializador, dataFinal);
            smartDevice.setLastChange(simulador.getData());
        }
        faturar(inicio,dataFinal,consumo,custo);
    }

    public void faturar(LocalDate inicio, LocalDate fim, double consumo, double custo) {
        Fatura fatura = new Fatura(this.nome,inicio,fim,consumo,custo);
        this.faturas.add(fatura);
        this.comercializador.addFatura(fatura);
    }

    public static CasaInteligente escolherCasa(Map<Integer, CasaInteligente> casas, Scanner scanner) {
        if (casas.isEmpty()) {
            System.out.println("Nao existem casas");
            return null;
        }
        for (Map.Entry<Integer, CasaInteligente> casa: casas.entrySet()) {
            System.out.println(casa.getValue().toString() + ", nif: " + casa.getKey());
        }
        System.out.println("Escreve o nif da casa que queres");
        int nif = Integer.parseInt(scanner.nextLine()); //assume-se que escolheu uma opcao valida
        if (casas.containsKey(nif)) {
            return casas.get(nif);
        } else {
            System.out.println("Nao ha nenhuma casa com este nif");
            return null;
        }
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

    public Comercializador getComercializador() {
        //comercializador antes das mudanças pendentes
        return comercializador;
    }

    public void setComercializador(Comercializador comercializador) {
        //coloca o novo comercializador nas mudancas pendentes
        this.comercializadorToChange = comercializador;
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
    public void change() {
        if (this.comercializadorToChange != null) {
            this.comercializador = this.comercializadorToChange;
            this.comercializadorToChange = null;
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
