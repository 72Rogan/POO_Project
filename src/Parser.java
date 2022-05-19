package src;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            System.out.println("Ficheiro nao encontrado");
            return null;
        }

        Simulador simulador = new Simulador(false); //cria um simulador vazio
        Map<String, Comercializador> comercializadores = new HashMap<>();
        Map<Integer, CasaInteligente> casas = new HashMap<>();
        Map<String, SmartDevice> dispositivos = new HashMap<>();

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
                        Comercializador c = Comercializador.parse(simulador, linhaPartida[1], rand);
                        comercializadores.put(c.getNome(), c);
                        break;
                    case "Casa":
                        ultimaCasa = CasaInteligente.parse(simulador, linhaPartida[1]);
                        casas.put(ultimaCasa.getNif(), ultimaCasa);
                        ultimaDivisao = null;
                        break;
                    case "Divisao":
                        if (ultimaCasa == null) throw new LinhaIncorretaException();
                        ultimaDivisao = linhaPartida[1];
                        ultimaCasa.addRoom(ultimaDivisao);
                        break;
                    case "SmartBulb":
                        if (ultimaDivisao == null) throw new LinhaIncorretaException();
                        ultimoDispositivo = SmartBulb.parse(simulador, linhaPartida[1]);
                        ultimaCasa.addDevice(ultimoDispositivo);
                        ultimaCasa.addToRoom(ultimaDivisao, ultimoDispositivo.getID());
                        dispositivos.put(ultimoDispositivo.getID(), ultimoDispositivo);
                        break;
                    case "SmartCamera":
                        if (ultimaDivisao == null) throw new LinhaIncorretaException();
                        ultimoDispositivo = SmartCamera.parse(simulador, linhaPartida[1]);
                        ultimaCasa.addDevice(ultimoDispositivo);
                        ultimaCasa.addToRoom(ultimaDivisao, ultimoDispositivo.getID());
                        dispositivos.put(ultimoDispositivo.getID(), ultimoDispositivo);
                        break;
                    case "SmartSpeaker":
                        if (ultimaDivisao == null) throw new LinhaIncorretaException();
                        ultimoDispositivo = SmartSpeaker.parse(simulador, linhaPartida[1]);
                        ultimaCasa.addDevice(ultimoDispositivo);
                        ultimaCasa.addToRoom(ultimaDivisao, ultimoDispositivo.getID());
                        dispositivos.put(ultimoDispositivo.getID(), ultimoDispositivo);
                        break;
                }
            }
        } catch(LinhaIncorretaException e){}

        simulador.setComercializadores(comercializadores);
        simulador.setCasasInteligentes(casas);
        simulador.setDispositivos(dispositivos);

        return simulador;
    }

    List<String> lerFicheiro(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    public void parseFornecedores(Scanner scanner) {

    }
}
