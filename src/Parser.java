package src;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class Parser {
    private String filePath;

    public Parser(String path) {
        this.filePath = path;
    }

    public Simulador parse(){
        List<String> linhasFicheiro;
        try {
            linhasFicheiro = lerFicheiro(this.filePath);
        } catch (IOException e) {
            System.out.println("\n");
            System.out.println("+--------------------------------------------------+");
            System.out.println("| -> Ficheiro nao encontrado                       |");
            System.out.println("+--------------------------------------------------+\n");

            return null;
        }

        Simulador simulador = new Simulador(false); //cria um simulador vazio

        String[] linhaPartida;
        CasaInteligente ultimaCasa = null;
        SmartDevice ultimoDispositivo = null;
        String ultimaDivisao = null;

        Random rand = new Random(); //randomizador para gerar dados aleatorios

        try {
            for (String linha: linhasFicheiro) {
                linhaPartida = linha.split(":", 2);
                switch(linhaPartida[0]) {
                    case "Fornecedor":
                        Comercializador c = Comercializador.parse(linhaPartida[1], rand);
                        simulador.addComercializador(c);
                        break;
                    case "Casa":
                        ultimaCasa = CasaInteligente.parse(simulador, linhaPartida[1]);
                        simulador.addCasa(ultimaCasa);
                        ultimaDivisao = null;
                        break;
                    case "Divisao":
                        if (ultimaCasa == null) throw new LinhaIncorretaException();
                        ultimaDivisao = linhaPartida[1];
                        ultimaCasa.addRoom(ultimaDivisao);
                        break;
                    case "SmartBulb":
                        if (ultimaDivisao == null) throw new LinhaIncorretaException();
                        ultimoDispositivo = SmartBulb.parse(linhaPartida[1], rand);
                        simulador.addDispositivo(ultimoDispositivo);
                        ultimaCasa.addDevice(ultimoDispositivo);
                        ultimaCasa.addToRoom(ultimaDivisao, ultimoDispositivo.getID());
                        break;
                    case "SmartCamera":
                        if (ultimaDivisao == null) throw new LinhaIncorretaException();
                        ultimoDispositivo = SmartCamera.parse(linhaPartida[1], rand);
                        simulador.addDispositivo(ultimoDispositivo);
                        ultimaCasa.addDevice(ultimoDispositivo);
                        ultimaCasa.addToRoom(ultimaDivisao, ultimoDispositivo.getID());
                        break;
                    case "SmartSpeaker":
                        if (ultimaDivisao == null) throw new LinhaIncorretaException();
                        ultimoDispositivo = SmartSpeaker.parse(linhaPartida[1], rand);
                        simulador.addDispositivo(ultimoDispositivo);
                        ultimaCasa.addDevice(ultimoDispositivo);
                        ultimaCasa.addToRoom(ultimaDivisao, ultimoDispositivo.getID());
                        break;
                }
            }
        } catch(LinhaIncorretaException e){}
        return simulador;
    }

    List<String> lerFicheiro(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    /*
    Recebe um simulador com a fase inicial ja realizada, e o caminho para o ficheiro com os passos de simulacao
    Le o ficheiro, le linha a linha, e executa cada uma das transformacoes ao simulador, criando ficheiros
    intermedios com os estados intermedios do simulador.
     */
    public void simular(Simulador simulador) {
        List<String> linhasFicheiro;
        try {
            linhasFicheiro = lerFicheiro(this.filePath);
        } catch (IOException e) {
            System.out.println("\n");
            System.out.println("+--------------------------------------------------+");
            System.out.println("| -> Ficheiro nao encontrado                       |");
            System.out.println("+--------------------------------------------------+\n");
            return;
        }
        Comercializador comercializador = null;
        CasaInteligente casa = null;

        int i=1;

        for (String linha: linhasFicheiro) {
            String[] str = linha.split(",", 3);
            LocalDate data = parseLocalDate(str[0]);
            simulador.saltarDias(data); //assume-se que esta data e posterior a data atual

            String id = str[1].trim();
            if ((casa = simulador.getCasa(id)) != null) {
                //evento de uma casa
                eventoCasa(casa, str[2], simulador);
            } else if ((comercializador = simulador.getComercializador(id)) != null) {
                eventoComercializador(comercializador, str[2]);
            }

            simulador.createStatusFile("output/evento" + i);
            i++;
        }
        //simulador.saltarDias(1); //salta um dia para aplicar as ultimas mudancas

    }

    public void eventoComercializador(Comercializador c, String input) {
        String[] str = input.split(",",2);
        if (str[0].trim().equals("alteraValorDesconto")) {
            double impostos = Double.valueOf(str[1].trim());
            c.setFatorImpostos(impostos);
        } else if (str[0].trim().equals("alteraValorCusto")) {
            double custo = Double.valueOf(str[1].trim());
            c.setCustoDiarioKwh(custo);
        }
    }

    public void eventoCasa(CasaInteligente casa, String input, Simulador simulador) {
        String[] str = input.split(",");
        String param1 = str[0].trim();
        Comercializador c = null;
        SmartDevice sd = null;

        if (casa.hasRoom(param1)) {
            //evento sobre divisao de casa
            if (str[1].trim().equals("setAllOff")) {
                //Desligar todos os dispositivos da divisao
                casa.setAllOn(param1, false);
            } else if (str[1].trim().equals("setAllOn")) {
                casa.setAllOn(param1, true);
            }
        } else if ((c = simulador.getComercializador(param1)) != null) {
            casa.setComercializador(c);
        } else if ((sd = simulador.getDispositivo(param1)) != null) {
            if (str[1].trim().equals("setOn")) {
                //Ligar o dispositivo
                sd.turnOn();
            } else if (str[1].trim().equals("setOff")) {
                sd.turnOff();
            }
        }
    }

    public LocalDate parseLocalDate(String str) {
        String s[] = str.split("\\.", 3);
        int ano = Integer.valueOf(s[0]);
        int mes = Integer.valueOf(s[1]);
        int dia = Integer.valueOf(s[2]);
        LocalDate date = LocalDate.of(ano,mes,dia);
        return date;
    }
}
