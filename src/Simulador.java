package src;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static src.SmartBulb.criarSmartBulb;
import static src.SmartCamera.criarSmartCamera;
import static src.SmartSpeaker.criarSmartSpeaker;


public class Simulador implements Serializable{
    private LocalDate data;
    private List<SmartDevice> dispositivos;
    private List<CasaInteligente> casasInteligentes;
    private List<Comercializador> comercializadores;
    private List<Periodo> periodos;
    private Estatisticas estatisticas;

    public Simulador() {
        this.data = LocalDate.now();
        this.dispositivos = new ArrayList<>();
        this.casasInteligentes = new ArrayList<>();
        this.comercializadores = new ArrayList<>();
        this.periodos = new ArrayList<>();
        this.estatisticas = new Estatisticas(casasInteligentes, comercializadores, periodos);
    }

    public Simulador(LocalDate date) {
        this.dispositivos = new ArrayList<>();
        this.casasInteligentes = new ArrayList<>();
        this.comercializadores = new ArrayList<>();
        this.periodos = new ArrayList<>();
        this.estatisticas = new Estatisticas(casasInteligentes, comercializadores, periodos);
        this.data = date;
    }

    public static Simulador construirSimulador(String caminhoFicheiro) {
        //Recebe o caminho para um ficheiro com um Simulador ja feito, e constroi-o
        Simulador simulador = null;
        try {
            FileInputStream fi = new FileInputStream(new File(caminhoFicheiro));
            ObjectInputStream oi = new ObjectInputStream(fi);

            simulador = (Simulador) oi.readObject();

            fi.close();
            oi.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro nao encontrado");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erro a inicializar a stream");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (simulador == null) System.out.println("Simulador e null");
        return simulador; //se devolver null, deu problema em cima
    }

    public void saltarDias(LocalDate date) {
        int dias = data.until(date).getDays();
        saltarDias(dias);
    }

    public void saltarDias(int daysToSkip) {
        LocalDate antes = data;
        data = data.plusDays(daysToSkip);
        for (CasaInteligente casaInteligente: this.casasInteligentes) {
            casaInteligente.saltarParaData(data);
        }
        Periodo periodo = new Periodo(antes, data);
        this.addPeriodo(periodo);
    }

    public void startInterface() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Data atual: " + this.data.toString());
            System.out.println("Escolhe uma opçao");
            System.out.println("1. Avançar no tempo");
            System.out.println("2. Gerir entidades");
            System.out.println("3. Estatisticas");
            int escolha = scanner.nextInt();
            if (escolha == 1) {
                avancarTempo(scanner);
            } else if (escolha == 2) {
                gerirEntidades(scanner);
            } else if (escolha == 3) {
                this.estatisticas.escolherEstatistica(scanner);
            }
        }
    }

    public void avancarTempo(Scanner scanner) {
        System.out.println("1. Avançar X dias");
        System.out.println("2. Avançar para a data X");
        int escolha = scanner.nextInt();
        if (escolha == 1) {
            System.out.println("Quantos dias queres avançar?");
            int diasAvançados = scanner.nextInt();
            this.saltarDias(diasAvançados);
        } else if (escolha == 2) {
            System.out.println("Data atual: " + data);
            System.out.println("Escreve a nova data no formato AA-MM-DD (ano-mes-dia)");
            String dataStr = scanner.next();
            String[] diaMesAno = dataStr.split("-", 3);
            int ano = Integer.valueOf(diaMesAno[0]);
            int mes = Integer.valueOf(diaMesAno[1]);
            int dia = Integer.valueOf(diaMesAno[2]);
            LocalDate dataNova = LocalDate.of(ano,mes,dia);
            if (dataNova.isBefore(data)) {
                System.out.println("Inseriu uma data invalida");
            } else {
                saltarDias(dataNova);
            }
        }
    }

    public void gerirEntidades(Scanner scanner) {
        System.out.println("1. Gerir Dispositivos");
        System.out.println("2. Gerir Casas Inteligentes");
        System.out.println("3. Gerir Comercializadores");
        int escolha = scanner.nextInt();
        if (escolha == 1) {
            gerirDispositivos(scanner);
        } else if (escolha == 2) {
            //gerirCasas(scanner);
        } else if (escolha == 3) {
            //gerirComercializadores(scanner);
        }
    }

    public void gerirDispositivos(Scanner scanner) {
        System.out.println("1. Listar Dispositivos");
        System.out.println("2. Criar Dispositivo");
        int escolha = scanner.nextInt();
        if (escolha == 1) {
            for (SmartDevice smartDevice: this.dispositivos) {
                System.out.println(smartDevice.toString());
            }
        } else if (escolha == 2) {
            System.out.println("1. Criar SmartBulb");
            System.out.println("2. Criar SmartSpeaker");
            System.out.println("3. Criar SmartCamera");
            escolha = scanner.nextInt();
            SmartDevice smartDevice = null;
            if (escolha == 1) smartDevice = criarSmartBulb(this, scanner);
            if (escolha == 2) smartDevice = criarSmartSpeaker(this, scanner);
            if (escolha == 3) smartDevice = criarSmartCamera(this, scanner);
            if (smartDevice != null) {
                addDispositivo(smartDevice);
            } else {
                System.out.println("Opcao invalida, saindo");
            }
        }
    }

    public void addDispositivo(SmartDevice smartDevice) {
        if (!this.dispositivos.contains(smartDevice)) {
            this.dispositivos.add(smartDevice);
        }
    }

    /*
    Pros e Contras de meter um apontador pro simulador em todas as classes
    Pros:
    a data passaria a ser private, secalhar melhor encapsulamento por isso
    conseguia adicionar dispositivos diretamente das casas, sem antes ter adicionado ao simulador

    Contras:
    Secalhar violava alguma regra
     */

    public void addCasa(CasaInteligente casaInteligente) {
        if (!this.casasInteligentes.contains(casaInteligente)) {
            //apenas adiciona a casa se ainda nao estiver na lista
            this.casasInteligentes.add(casaInteligente);
        }
    }

    public void addComercializador(Comercializador comercializador) {
        if (!this.comercializadores.contains(comercializador)) {
            this.comercializadores.add(comercializador);
        }
    }

    public void addPeriodo(Periodo periodo) {
        if (!this.periodos.contains(periodo)) {
            this.periodos.add(periodo);
        }
    }

    public void printFaturas() {
        for (CasaInteligente casaInteligente: this.casasInteligentes) {
            System.out.println(casaInteligente.getFaturas());
        }
    }

    public LocalDate getData() {
        return this.data;
    }
}
