package src;

import src.SmartDevice;

import java.time.LocalDateTime;
import java.util.Scanner;

public class SmartSpeaker extends SmartDevice {
	private Simulador simulador;
	private int volume;
	public static final int MAX = 100;
	private String marca;
	private String radio;

	public SmartSpeaker(){
		super();
		this.volume = 0; //volume max = 100
		this.marca = "N/A";
		this.radio = "N/A";
	}

	public SmartSpeaker(Simulador simulador, Modo x, int vol, String marca, String radio){
		super(simulador, 350,x);
		if (vol > MAX) {
			this.volume = MAX;
		} else if (vol < 0) {
			this.volume = 0;
		} else {
			this.volume=vol;
		}
		this.marca=marca;
		this.radio=radio;
		calcularConsumoDiario();
	}

	public SmartSpeaker(Simulador simulador, Modo x, int vol, String marca, String radio, double consumoDiario){
		super(simulador, 350,x);
		if (vol > MAX) {
			this.volume = MAX;
		} else if (vol < 0) {
			this.volume = 0;
		} else {
			this.volume=vol;
		}
		this.marca=marca;
		this.radio=radio;
		setConsumoDiario(consumoDiario);
	}

	public SmartSpeaker(SmartSpeaker c){
		super(c);
		this.volume=c.getVolume();
		this.marca=c.getMarca();
		this.radio=c.getRadio();
	}

	//public LocalDateTime getHorasdeLigado(){
	//	return this.horadeLigado;
	//}
	public int getVolume(){
		return this.volume;
	}
	public String getMarca(){
		return this.marca;
	}
	public String getRadio(){
		return this.radio;
	}

	public void setVolume(int vol){
		if (vol > MAX) {
			this.volume = MAX;
		} else if (vol < 0) {
			this.volume = 0;
		} else {
			this.volume=vol;
		}
	}

// podem ser mais uteis depois

	public void volumeUp() {
		if (this.volume<MAX) this.volume++;
	}

	public void volumeDown() {
		if (this.volume>0) this.volume--;
	}



	public void setMarca(String marca){
		this.marca=marca;
	}

	public void setRadio(String radio){
		this.radio=radio;
	}

	public static SmartSpeaker criarSmartSpeaker(Simulador simulador, Scanner scanner) {
		System.out.println("Escreve no formato Modo,Volume,Marca,Radio / Exemplo: ON,73,JBL,MEGAHITS");
		String input = scanner.nextLine();
		String[] idCustoModoVolumeMarcaRadio = input.split(",", 4);
		Modo modo = idCustoModoVolumeMarcaRadio[0].equals("OFF") ? Modo.OFF : idCustoModoVolumeMarcaRadio[0].equals("ON") ? Modo.ON : null;
		int volume = Integer.valueOf(idCustoModoVolumeMarcaRadio[1]);
		String marca = idCustoModoVolumeMarcaRadio[2];
		String radio = idCustoModoVolumeMarcaRadio[3];
		SmartSpeaker ret = new SmartSpeaker(simulador, modo, volume,marca,radio);
		return ret;
	}

    public boolean equals(Object o) {
        if (this==o) return true; 
        if ((o == null) || (this.getClass() != o.getClass())) return false; 
        
        SmartSpeaker c = (SmartSpeaker) o;
        return c.getModo() == this.getModo() &&
               c.getCustoInstalacao() == this.getCustoInstalacao() &&
               c.getID() == this.getID() &&
               c.getVolume() == this.volume && 
               c.getMarca().equals(this.marca) &&
               c.getRadio().equals(this.radio); 
    }
    
    public SmartSpeaker clone() {
         return new SmartSpeaker(this);
    }
    
    public String toString() {
         StringBuilder sb = new StringBuilder(); 
         sb.append("Coluna, id: ")
				 .append(this.getID())
				 .append(", ")
				 .append(this.getModo())
		 		 .append(", ")
				 .append(this.getVolume());
         return sb.toString();
    }

	@Override
	public void calcularConsumoDiario() {
		//consumo em funcao da marca + factor em funcao do volume
		double consumoBase = 0.5;
		double consumoDiario = consumoBase + (this.volume / 20.0); // o consumoDiario tem um valor entre 0.5 e 5.5
		setConsumoDiario(consumoDiario);
	}

	public static SmartSpeaker parse(Simulador simulador, String linha) {
		String[] linhaPartida = linha.split(",", 4); //no maximo 4 parametros
		int volume = Integer.valueOf(linhaPartida[0]);
		String radio = linhaPartida[1];
		String marca = linhaPartida[2];
		double consumo = Double.valueOf(linhaPartida[3]);

		SmartSpeaker sS = new SmartSpeaker(simulador, Modo.ON, volume, marca, radio, consumo);
		return sS;
	}
}
