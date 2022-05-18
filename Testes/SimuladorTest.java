import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Scanner;
import java.time.Month;
import java.util.Scanner;

import src.Simulador;

class SimuladorTest {

    @Test
    void testeConstrutor() {
        LocalDate time = LocalDate.now();
        Simulador sim = new Simulador();
        assertNotNull(sim,"Erro ao criar o Simulador");
        sim = new Simulador(true);
        assertNotNull(sim, "Erro ao criar o Simulador");
        sim = new Simulador(time);
        assertNotNull(sim, "Erro ao criar o Simulador");
    }

    @Test
    void testeGetData(){
        LocalDate time = LocalDate.now();
        Simulador sim = new Simulador(time);
        assertEquals(LocalDate.now(), sim.getData(), "Data não corresponde");
        /*
        LocalDate data = LocalDate.of(2022,Month.DECEMBER,31);
        sim.saltarDias(data);
        assertEquals(data, sim.getData(), "Data não igual");
        */
    }

    @Test
    void testeGerirEntidades(){
        System.out.println("Escolhe uma opcao");
        System.out.println("1. Avancar no tempo");
        System.out.println("2. Gerir entidades");
        System.out.println("3. Estatisticas");
        System.out.println("4. Acabar Programa");
        Simulador sim = new Simulador();
        Scanner sc = new Scanner(System.in);
        int escolha = sc.nextInt();
        if(escolha == 2)

        //sim.gerirEntidades(sc);
        assertEquals(true,sim.gerirEntidades(sc),"não conseguiu gerar entidades");



    }

    void faseInicial() {
    }

    @Test
    void startInterface() {
    }

    @Test
    void avancarTempo() {
    }

    @Test
    void gerirEntidades() {
    }

    @Test
    void gerirComercializadores() {
    }

    @Test
    void listarComercializadores() {
    }

    @Test
    void listarCasas() {
    }

    @Test
    void listarDispositivos() {
    }

    @Test
    void criarComercializador() {
    }

    @Test
    void criarCasa() {
    }

    @Test
    void criarDispositivo() {
    }

    @Test
    void mudarValoresDeComercializador() {
    }

    @Test
    void mudarComercializadorDeCasa() {
    }

    @Test
    void gerirCasas() {
    }

    @Test
    void ligarDesligarDispositivoDeCasa() {
    }

    @Test
    void adicionarDispositivoACasa() {
    }

    @Test
    void gerirDispositivos() {
    }

    @Test
    void addDispositivo() {
    }

    @Test
    void addCasa() {
    }

    @Test
    void addComercializador() {
    }

    @Test
    void addPeriodo() {
    }

    @Test
    void printFaturas() {
    }

    @Test
    void getData() {
    }

    @Test
    void getDispositivos() {
    }

    @Test
    void getCasasInteligentes() {
    }

    @Test
    void getComercializadores() {
    }

    @Test
    void setDispositivos() {
    }

    @Test
    void setCasasInteligentes() {
    }

    @Test
    void setComercializadores() {
    }
}