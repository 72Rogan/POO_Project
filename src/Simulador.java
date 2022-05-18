package src;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static src.SmartBulb.criarSmartBulb;
import static src.SmartCamera.criarSmartCamera;
import static src.SmartDevice.escolherDispositivo;
import static src.SmartSpeaker.criarSmartSpeaker;


public class Simulador implements Serializable{
    private LocalDate data;
    private boolean faseInicial;
    private List<SmartDevice> dispositivos;
    private List<CasaInteligente> casasInteligentes;
    private List<Comercializador> comercializadores;
    private List<Periodo> periodos;

    public Simulador() {
        this.data = LocalDate.now();
        this.faseInicial = true;
        this.dispositivos = new ArrayList<>();
        this.casasInteligentes = new ArrayList<>();
        this.comercializadores = new ArrayList<>();
        this.periodos = new ArrayList<>();
    }

    public Simulador(boolean faseI) {
        this.data = LocalDate.now();
        this.faseInicial = faseI;
        this.dispositivos = new ArrayList<>();
        this.casasInteligentes = new ArrayList<>();
        this.comercializadores = new ArrayList<>();
        this.periodos = new ArrayList<>();
    }

    public Simulador(LocalDate date) {
        this.faseInicial = true;
        this.dispositivos = new ArrayList<>();
        this.casasInteligentes = new ArrayList<>();
        this.comercializadores = new ArrayList<>();
        this.periodos = new ArrayList<>();
        this.data = date;
    }

    public static Simulador construirSimulador(String caminhoFicheiro) {
        //Recebe o caminho para um ficheiro com um Simulador ja feito, e constroi-o
        Simulador simulador = null;
        try {
            File ficheiro = new File(caminhoFicheiro);
            if (!ficheiro.exists()) {
                System.out.println("Ficheiro nao existe");
                return null;
            }
            FileInputStream fi = new FileInputStream(ficheiro);
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

    /*
    Nesta fase, ser permitido criar dispositivos, casas e fornecedores de energia.
     */
    public void faseInicial(Scanner scanner) {
        this.faseInicial = true;
        while (this.gerirEntidades(scanner));
        this.faseInicial = false;
    }

    public void startInterface(Scanner scanner) {
        while (true) {
            System.out.println("Data atual: " + this.data.toString());
            System.out.println("Escolhe uma opcao");
            System.out.println("1. Avancar no tempo");
            System.out.println("2. Gerir entidades");
            System.out.println("3. Estatisticas");
            System.out.println("4. Acabar Programa");
            int escolha = scanner.nextInt();
            if (escolha == 1) {
                avancarTempo(scanner);
            } else if (escolha == 2) {
                gerirEntidades(scanner);
            } else if (escolha == 3) {
                Estatisticas estatisticas = new Estatisticas(this.casasInteligentes, this.comercializadores, this.periodos);
                estatisticas.escolherEstatistica(scanner);
            } else if (escolha == 4) {
                //sair do programa
                break;
            }
        }
    }

    public void avancarTempo(Scanner scanner) {
        System.out.println("1. Avancar X dias");
        System.out.println("2. Avancar para a data X");
        int escolha = scanner.nextInt();
        if (escolha == 1) {
            System.out.println("Quantos dias queres avancar?");
            int diasAvancados = scanner.nextInt();
            this.saltarDias(diasAvancados);
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

    public boolean gerirEntidades(Scanner scanner) {
        System.out.println("1. Gerir Dispositivos");
        System.out.println("2. Gerir Casas Inteligentes");
        System.out.println("3. Gerir Comercializadores");
        int escolha = scanner.nextInt();
        if (escolha == 1) {
            gerirDispositivos(scanner);
        } else if (escolha == 2) {
            gerirCasas(scanner);
        } else if (escolha == 3) {
            gerirComercializadores(scanner);
        } else {
            return false;
        }
        return true;
    }

    public void gerirComercializadores(Scanner scanner) {
        if (this.faseInicial) {
            System.out.println("1. Listar comercializadores");
            System.out.println("2. Criar comercializador");
            int escolha = scanner.nextInt();
            if (escolha == 1) {
                listarComercializadores();
            } else if (escolha == 2) {
                criarComercializador(scanner);
            }
        } else {
            System.out.println("1. Listar comercializadores");
            System.out.println("2. Mudar valores de um comercializador");
            int escolha = scanner.nextInt();
            if (escolha == 1) {
                listarComercializadores();
            } else if (escolha == 2) {
                mudarValoresDeComercializador(scanner);
            }
        }
    }

    public void listarComercializadores() {
        for (Comercializador com: this.comercializadores) {
            System.out.println(com.toString());
        }
    }

    public void listarCasas() {
        for (CasaInteligente casa: this.casasInteligentes) {
            System.out.println(casa.toString());
        }
    }

    public void listarDispositivos() {
        for (SmartDevice smartDevice: this.dispositivos) {
            System.out.println(smartDevice.toString());
        }
    }

    public void criarComercializador(Scanner scanner) {
        System.out.println("Escreve o novo comercializador no formato Nome-CustoDiarioKwh-FatorImpostos");
        String input = scanner.next();
        String[] nomeNif = input.split("-", 3);
        String nome = nomeNif[0];
        double custoDiarioKwh = Double.valueOf(nomeNif[1]);
        double fatorImpostos = Double.valueOf(nomeNif[2]);
        System.out.println("Escreva a formula para calcular o custo diario, sendo o custoDiarioKwh a variavel x, e o fatorImpostos a variavel y");
        String formula = scanner.next();
        Comercializador comercializador = new Comercializador(this,nome,custoDiarioKwh,fatorImpostos);
        this.addComercializador(comercializador);
    }

    public void criarCasa(Scanner scanner) {
        if (this.comercializadores.isEmpty()) {
            System.out.println("Ainda nao existem comercializadores");
            System.out.println("Crie comercializadores para poder criar casa");
            return;
        }
        System.out.println("Escreve a nova casa no formato Nome-Nif");
        String input = scanner.next();
        String[] nomeNif = input.split("-", 2);
        String nome = nomeNif[0];
        int nif = Integer.valueOf(nomeNif[1]);
        Comercializador comercializador = Comercializador.escolherComercializador(this.comercializadores, scanner);
        CasaInteligente casa = new CasaInteligente(this, nome, nif, comercializador);
        this.addCasa(casa);
    }

    public void criarDispositivo(Scanner scanner) {
        System.out.println("1. Criar SmartBulb");
        System.out.println("2. Criar SmartSpeaker");
        System.out.println("3. Criar SmartCamera");
        int escolha = scanner.nextInt();
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

    public void mudarValoresDeComercializador(Scanner scanner) {
        Comercializador comercializador = Comercializador.escolherComercializador(this.comercializadores,scanner);
        System.out.println("Digite os novos valores do comercializador no formato Nome-CustoDiarioKwh-FatorImpostos");
        System.out.println("Se quiser manter algum parametro, escreva -1 no parametro respetivo");
        String input = scanner.next();
        String[] nomeNif = input.split("-", 3);
        String nome = nomeNif[0];
        double custoDiarioKwh = Double.valueOf(nomeNif[1]);
        double fatorImpostos = Double.valueOf(nomeNif[2]);
        if (!nome.equals("-1")) comercializador.setNome(nome);
        if (custoDiarioKwh != -1) comercializador.setCustoDiarioKwh(custoDiarioKwh);
        if (fatorImpostos != -1) comercializador.setFatorImpostos(fatorImpostos);
    }

    public void mudarComercializadorDeCasa(Scanner scanner) {
        CasaInteligente casa = CasaInteligente.escolherCasa(this.casasInteligentes, scanner);
        Comercializador comercializador = Comercializador.escolherComercializador(this.comercializadores, scanner);
        casa.setComercializador(comercializador);
    }


    public void gerirCasas(Scanner scanner) {
        if (this.faseInicial) {
            System.out.println("1. Listar casas");
            System.out.println("2. Mudar comercializador de uma casa");
            System.out.println("3. Criar casas");
            System.out.println("4. Adicionar dispositivo a uma casa");
            int escolha = scanner.nextInt();
            if (escolha == 1) {
                listarCasas();
            } else if (escolha == 2) {
                mudarComercializadorDeCasa(scanner);
            } else if (escolha == 3) {
                criarCasa(scanner);
            } else if (escolha == 4) {
                adicionarDispositivoACasa(scanner);
            }
        } else {
            System.out.println("1. Listar casas");
            System.out.println("2. Mudar comercializador de uma casa");
            System.out.println("3. Ligar/Desligar dispositivos de uma casa");
            int escolha = scanner.nextInt();
            if (escolha == 1) {
                listarCasas();
            } else if (escolha == 2) {
                mudarComercializadorDeCasa(scanner);
            } else if (escolha == 3) {
                ligarDesligarDispositivoDeCasa(scanner);
            }
        }

    }

    public void ligarDesligarDispositivoDeCasa(Scanner scanner) {
        CasaInteligente casa = CasaInteligente.escolherCasa(this.casasInteligentes, scanner);
        List<SmartDevice> dispositivosDaCasa = this.dispositivos.stream()
                .filter(dispositivo -> casa.existsDevice(dispositivo.getID())).collect(Collectors.toList());
        System.out.println("O modo do dispositivo que escolher sera trocado");
        SmartDevice dispositivo = escolherDispositivo(dispositivosDaCasa, scanner);
        dispositivo.setOn(!dispositivo.isOn());
    }

    public void adicionarDispositivoACasa(Scanner scanner) {
        CasaInteligente casa = CasaInteligente.escolherCasa(this.casasInteligentes, scanner);
        List<SmartDevice> dispositivosForaDaCasa = this.dispositivos.stream()
                .filter(dispositivo -> !casa.equals(dispositivo.getCasa()))
                .collect(Collectors.toList());
        System.out.println("O dispositivo escolhido sera adicionado na casa");
        SmartDevice dispositivo = escolherDispositivo(dispositivosForaDaCasa, scanner);
        casa.addDevice(dispositivo);
    }

    public void gerirDispositivos(Scanner scanner) {
        if (this.faseInicial) {
            System.out.println("1. Listar Dispositivos");
            System.out.println("2. Criar Dispositivo");
            int escolha = scanner.nextInt();
            if (escolha == 1) {
                listarDispositivos();
            } else if (escolha == 2) {
                criarDispositivo(scanner);
            }
        } else {
            System.out.println("1. Listar Dispositivos");
            int escolha = scanner.nextInt();
            if (escolha == 1) {
                listarDispositivos();
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

    public List<SmartDevice> getDispositivos() {
        return dispositivos;
    }

    public List<CasaInteligente> getCasasInteligentes() {
        return casasInteligentes;
    }

    public List<Comercializador> getComercializadores() {
        return comercializadores;
    }

    public void setDispositivos(Map<String, SmartDevice> disp) {

    }

    public void setCasasInteligentes(Map<Integer, CasaInteligente> casas) {

    }

    public void setComercializadores(Map<String, Comercializador> comercializadores) {

    }
}
