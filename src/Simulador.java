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
    //data da simulacao
    private LocalDate data;
    //boleano indicando se o simulador esta na fase de criar entidades
    private boolean faseInicial;
    //conjunto de dispositivos
    private Map<String, SmartDevice> dispositivos;
    //conjunto de casas inteligentes
    private Map<Integer, CasaInteligente> casasInteligentes;
    //conjunto de comercializadores
    private Map<String, Comercializador> comercializadores;
    //conjunto de periodos
    private List<Periodo> periodos;
    //id atual do atribuidor de ids
    private int currentId;

    public Simulador() {
        this.data = LocalDate.now();
        this.faseInicial = true;
        this.dispositivos = new HashMap<>();
        this.casasInteligentes = new HashMap<>();
        this.comercializadores = new HashMap<>();
        this.periodos = new ArrayList<>();
        this.currentId = 0;
    }

    public Simulador(boolean faseI) {
        this.data = LocalDate.now();
        this.faseInicial = faseI;
        this.dispositivos = new HashMap<>();
        this.casasInteligentes = new HashMap<>();
        this.comercializadores = new HashMap<>();
        this.periodos = new ArrayList<>();
        this.currentId = 0;
    }

    public Simulador(LocalDate date) {
        this.faseInicial = true;
        this.dispositivos = new HashMap<>();
        this.casasInteligentes = new HashMap<>();
        this.comercializadores = new HashMap<>();
        this.periodos = new ArrayList<>();
        this.data = date;
        this.currentId = 0;
    }

    public Simulador(Simulador simulador) {
        this.faseInicial = simulador.faseInicial;
        /*
        Os clones ao serem chamado em cada forEach chamam os construtores de cada entidade,
        que por sua vez adicionam a entidade ao simulador
        Os dispositivos ja sao adicionados ao simulador dentro dos clones da casa inteligente
         */
        this.dispositivos = new HashMap<>();
        this.casasInteligentes = simulador.casasInteligentes.entrySet().stream()
                        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().clone()));
        this.comercializadores = simulador.comercializadores.entrySet().stream()
                        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().clone()));

        for (CasaInteligente c: this.casasInteligentes.values()) {
            //adiciona os dispositivos da casa c ao map dispositivos, sem dar clone
            c.addAllDevices(dispositivos);
            Comercializador com = c.getComercializador();
            if (this.comercializadores.containsKey(com.getNome())) {
                c.setComercializador(this.comercializadores.get(com.getNome()));
            }
        }

        this.periodos = simulador.periodos.stream().map(Periodo::clone).collect(Collectors.toList());
        this.data = simulador.data;
        this.currentId = simulador.currentId;

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
            simulador.faseInicial = false;

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

        return simulador; //se devolver null, deu problema em cima
    }

    public void guardarEstadoAtual(String ficheiroObjeto) {
        try {
            File ficheiro = new File(ficheiroObjeto);

            FileOutputStream fo = new FileOutputStream(ficheiro);
            ObjectOutputStream oo = new ObjectOutputStream(fo);

            Simulador simuladorToWrite = this.clone();
            simuladorToWrite.reset(); //elimina as faturas e periodos mas deixa a informacao das entidades
            oo.writeObject(simuladorToWrite);

            fo.close();
            oo.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro nao encontrado");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erro a inicializar a stream");
            e.printStackTrace();
        }
    }

        public void saltarDias(LocalDate date) {
        int dias = data.until(date).getDays();
        saltarDias(dias);
    }

    public void saltarDias(int daysToSkip) {
        if (daysToSkip > 0) {

        }
        LocalDate antes = data;
        LocalDate depois = data.plusDays(daysToSkip);
        for (CasaInteligente casaInteligente: this.casasInteligentes.values()) {
            casaInteligente.saltarParaData(antes, depois);
            casaInteligente.change(); //aplicar mudancas pendentes
        }
        data = depois;
        for (SmartDevice sd: this.dispositivos.values()) sd.change(); //aplicar mudancas pendentes
        for (Comercializador c: this.comercializadores.values()) c.change(); //aplicar mudancas pendentes

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
            int escolha = Integer.parseInt(scanner.nextLine());
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
        int escolha = Integer.parseInt(scanner.nextLine());
        if (escolha == 1) {
            System.out.println("Quantos dias queres avancar?");
            int diasAvancados = Integer.parseInt(scanner.nextLine());
            this.saltarDias(diasAvancados);
        } else if (escolha == 2) {
            System.out.println("Data atual: " + data);
            System.out.println("Escreve a nova data no formato AA-MM-DD (ano-mes-dia)");
            String dataStr = scanner.nextLine();
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
        System.out.println("1. Listar Dispositivos");
        System.out.println("2. Gerir Casas Inteligentes");
        System.out.println("3. Gerir Comercializadores");
        System.out.println("4. Sair");
        int escolha = Integer.parseInt(scanner.nextLine());
        if (escolha == 1) {
            listarDispositivos();
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
            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha == 1) {
                listarComercializadores();
            } else if (escolha == 2) {
                criarComercializador(scanner);
            }
        } else {
            System.out.println("1. Listar comercializadores");
            System.out.println("2. Mudar valores de um comercializador");
            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha == 1) {
                listarComercializadores();
            } else if (escolha == 2) {
                mudarValoresDeComercializador(scanner);
            }
        }
    }

    public void listarComercializadores() {
        for (Comercializador com: this.comercializadores.values()) {
            System.out.println(com.toString());
        }
    }

    public void listarCasas() {
        for (CasaInteligente casa: this.casasInteligentes.values()) {
            System.out.println(casa.toString());
        }
    }

    public void listarDispositivos() {
        for (SmartDevice smartDevice: this.dispositivos.values()) {
            System.out.println(smartDevice.toString());
        }
    }

    public void criarComercializador(Scanner scanner) {
        System.out.println("Escreve o novo comercializador no formato Nome,CustoDiarioKwh,FatorImpostos");
        String input = scanner.nextLine();
        String[] nomeNif = input.split(",", 3);
        String nome = nomeNif[0];
        double custoDiarioKwh = Double.valueOf(nomeNif[1]);
        double fatorImpostos = Double.valueOf(nomeNif[2]);
        Comercializador comercializador = new Comercializador(nome,custoDiarioKwh,fatorImpostos);
        this.addComercializador(comercializador);
    }

    public void criarCasa(Scanner scanner) {
        if (this.comercializadores.isEmpty()) {
            System.out.println("Ainda nao existem comercializadores");
            System.out.println("Crie comercializadores para poder criar casa");
            return;
        }
        System.out.println("Escreve a nova casa no formato Nome,Nif");
        String input = scanner.nextLine();
        String[] nomeNif = input.split(",", 2);
        String nome = nomeNif[0];
        int nif = Integer.valueOf(nomeNif[1]);
        Comercializador comercializador;
        while ((comercializador = Comercializador.escolherComercializador(this.comercializadores, scanner)) == null) {
            System.out.println("Escolha um comercializador valido");
        }
        CasaInteligente casa = new CasaInteligente(nome, nif, comercializador);
        this.addCasa(casa);
    }

    public SmartDevice criarDispositivo(Scanner scanner) {
        System.out.println("1. Criar SmartBulb");
        System.out.println("2. Criar SmartSpeaker");
        System.out.println("3. Criar SmartCamera");
        int escolha = Integer.parseInt(scanner.nextLine());
        SmartDevice smartDevice = null;
        if (escolha == 1) smartDevice = criarSmartBulb(scanner);
        if (escolha == 2) smartDevice = criarSmartSpeaker(scanner);
        if (escolha == 3) smartDevice = criarSmartCamera(scanner);
        if (smartDevice == null) {
            System.out.println("Opcao invalida, saindo");
        }
        return smartDevice;
    }

    public void mudarValoresDeComercializador(Scanner scanner) {
        Comercializador comercializador = Comercializador.escolherComercializador(this.comercializadores,scanner);
        if(comercializador != null) comercializador.mudarValores(scanner);
    }

    public void mudarComercializadorDeCasa(Scanner scanner) {
        CasaInteligente casa = CasaInteligente.escolherCasa(this.casasInteligentes, scanner);
        if (casa != null) mudarComercializadorDeCasa(casa, scanner);
    }

    public void mudarComercializadorDeCasa(CasaInteligente casa, Scanner scanner) {
        Comercializador comercializador = Comercializador.escolherComercializador(this.comercializadores, scanner);
        if (comercializador != null) casa.setComercializador(comercializador);
    }

    public boolean gerirCasa(CasaInteligente casa, Scanner scanner) {
        System.out.println(casa);
        if (this.faseInicial) {
            System.out.println("Escolha uma opcao");
            System.out.println("1. Mudar comercializador da casa");
            System.out.println("2. Adicionar dispositivo a casa");
            System.out.println("3. Adicionar divisao a casa");
            System.out.println("4. Sair");
            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha == 1) {
                mudarComercializadorDeCasa(casa, scanner);
            } else if (escolha == 2) {
                adicionarDispositivoACasa(casa, scanner);
            } else if (escolha == 3) {
                adicionarDivisaoACasa(casa, scanner);
            } else if (escolha == 6){
                return false;
            }
            return true;
        } else {
            System.out.println("Escolha uma opcao");
            System.out.println("1. Listar dispositivos da casa");
            System.out.println("2. Mudar comercializador da casa");
            System.out.println("3. Ligar/Desligar um dispositivo especifico da casa");
            System.out.println("4. Ligar todos os dispositivos de uma divisao");
            System.out.println("5. Desligar todos os dispositivos de uma divisao");
            System.out.println("6. Sair");
            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha == 1) {
                casa.listarDispositivos();
            } else if (escolha == 2) {
                mudarComercializadorDeCasa(casa, scanner);
            } else if (escolha == 3) {
                ligarDesligarDispositivoDeCasa(casa,scanner);
            } else if (escolha == 4) {
                String divisao = casa.escolherDivisao(scanner);
                casa.setAllOn(divisao, true);
            } else if (escolha == 5) {
                String divisao = casa.escolherDivisao(scanner);
                casa.setAllOn(divisao, false);
            } else if (escolha == 6){
                return false;
            }
            return true;
        }

    }


    public void gerirCasas(Scanner scanner) {
        if (this.faseInicial) {
            System.out.println("1. Listar casas");
            System.out.println("2. Criar casas");
            System.out.println("3. Gerir uma casa");
            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha == 1) {
                listarCasas();
            } else if (escolha == 2) {
                criarCasa(scanner);
            } else if (escolha == 3) {
                CasaInteligente casa = CasaInteligente.escolherCasa(this.casasInteligentes, scanner);
                if (casa != null) while(gerirCasa(casa, scanner));
            }
        } else {
            System.out.println("1. Listar casas");
            System.out.println("2. Gerir uma casa");
            int escolha = Integer.parseInt(scanner.nextLine());
            if (escolha == 1) {
                listarCasas();
            } else if (escolha == 2) {
                CasaInteligente casa = CasaInteligente.escolherCasa(this.casasInteligentes, scanner);
                if (casa != null) while(gerirCasa(casa, scanner));
            }
        }

    }

    public void ligarDesligarDispositivoDeCasa(CasaInteligente casa, Scanner scanner) {
        Map<String, SmartDevice> dispositivosDaCasa = this.dispositivos.entrySet().stream()
                .filter(e -> casa.existsDevice(e.getValue().getID()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        System.out.println("O modo do dispositivo que escolher sera trocado");
        SmartDevice dispositivo = escolherDispositivo(dispositivosDaCasa, scanner);
        if (dispositivo != null) dispositivo.setOn(!dispositivo.isOn());
    }

    public void adicionarDispositivoACasa(CasaInteligente casa, Scanner scanner) {
        if (!casa.existsRooms()) {
            System.out.println("Casa nao tem divisoes");
            return;
        }
        String divisao = casa.escolherDivisao(scanner);
        SmartDevice disp = criarDispositivo(scanner);
        this.addDispositivo(disp);
        casa.addDevice(disp); //adiciona dispositivo a casa
        casa.addToRoom(divisao, disp.getID()); //adiciona dispositivo a divisao
    }

    public void adicionarDivisaoACasa(CasaInteligente casa, Scanner scanner) {
        System.out.println("Escreva o nome da divisao");
        String div = scanner.nextLine();
        System.out.println("Divisao adicionada");
        if (casa != null) casa.addRoom(div);
    }

    /*
    Recebe uma string que representa o path para o ficheiro que vai conter o estado atual
    do simulador
     */
    public void createStatusFile(String filePath) {
        try {
            FileWriter myWriter = new FileWriter(filePath);

            myWriter.write("Fornecedores: \n");
            for (Comercializador c: this.comercializadores.values()) {
                myWriter.write(c.toString());
                myWriter.write("\n");
            }

            myWriter.write("\n");
            myWriter.write("Casas Inteligentes: \n");
            for (CasaInteligente c: this.casasInteligentes.values()) {
                myWriter.write(c.toString());
                myWriter.write("\n");
                myWriter.write(c.conteudo());
                myWriter.write("\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Nao conseguiu abrir/escrever ficheiro");
        }
    }

    public CasaInteligente getCasa(String nome) {
        try {
            int nif = Integer.valueOf(nome);
            return this.casasInteligentes.get(nif);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getNextId() {
        this.currentId++;
        return String.valueOf(this.currentId);
    }

    public Comercializador getComercializador(String nome) {
        return this.comercializadores.get(nome);
    }

    public SmartDevice getDispositivo(String id) {
        return this.dispositivos.get(id);
    }

    public void addDispositivo(SmartDevice smartDevice) {
        smartDevice.setID(this.getNextId()); //atribui id ao smartdevice
        this.dispositivos.put(smartDevice.getID(), smartDevice);
    }

    public void addCasa(CasaInteligente casaInteligente) {
        this.casasInteligentes.put(casaInteligente.getNif(), casaInteligente);
    }

    public void addComercializador(Comercializador c) {
        this.comercializadores.put(c.getNome(), c);
    }

    public void addPeriodo(Periodo periodo) {
        if (!this.periodos.contains(periodo)) {
            this.periodos.add(periodo);
        }
    }

    public void printFaturas() {
        for (CasaInteligente casaInteligente: this.casasInteligentes.values()) {
            System.out.println(casaInteligente.getFaturas());
        }
    }

    /*
    Esta funcao elimina as faturas existentes no simulador inteiro, tambem elimina as instancias de periodo
     */
    public void reset() {
        this.data = LocalDate.now();
        for (CasaInteligente casa: this.casasInteligentes.values()) {
            casa.setFaturas(new ArrayList<>()); //elimina as faturas da casa
        }
        for (Comercializador c: this.comercializadores.values()) {
            c.setFaturas(new ArrayList<>()); //elimina as faturas do fornecedor
        }
        this.periodos = new ArrayList<>();


    }

    public LocalDate getData() {
        return this.data;
    }

    public Map<String, SmartDevice> getDispositivos() {
        return dispositivos;
    }

    public Map<Integer, CasaInteligente> getCasasInteligentes() {
        return casasInteligentes;
    }

    public Map<String, Comercializador> getComercializadores() {
        return comercializadores;
    }

    public void setDispositivos(Map<String, SmartDevice> disp) {
        this.dispositivos = disp.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    public void setCasasInteligentes(Map<Integer, CasaInteligente> casas) {
        this.casasInteligentes = casas.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    public void setComercializadores(Map<String, Comercializador> comercializadores) {
        this.comercializadores = comercializadores.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    public Simulador clone() {
        return new Simulador(this);
    }
}
