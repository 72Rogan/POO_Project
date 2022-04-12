package src;

import src.SmartDevice;

import java.time.LocalDateTime;

public class SmartSpeaker extends SmartDevice {

	private double consumoEnergetico_pSec;
	private double consumoEnergetico_Total;
	private int tempoLigado;
	private LocalDateTime horaDeLigado;
	private int volume;
	private String marca;
	private String radio;

	public SmartSpeaker(){
		super(-1, 0);
		this.consumoEnergetico_pSec=1;
		this.consumoEnergetico_Total=0;
		this.tempoLigado = 0;
		//this.horaDeLigado = 0;
		this.volume =0;
		this.marca = "N/A";
		this.radio = "N/A";
	}

	public SmartSpeaker(Modo x, double instalacao, int codigo, double consumoEnergetico_pSec, double consumoEnergetico_Total,
						int tempoLigado, LocalDateTime horadeLigado, int vol, String marca, String radio){
		super(codigo,instalacao, x);

		this.consumoEnergetico_pSec=consumoEnergetico_pSec;
		this.consumoEnergetico_Total=consumoEnergetico_Total;
		this.tempoLigado=tempoLigado;
		this.horaDeLigado=horaDeLigado;
		this.volume=vol;
		this.marca=marca;
		this.radio=radio;
	}

	public SmartSpeaker(SmartSpeaker c){
		super(c.getCodigo(), c.getCustoInstalacao(), c.getModo());
		this.consumoEnergetico_pSec=c.getConsumoEnergetico_pSec();
		this.consumoEnergetico_Total=c.getConsumoEnergetico_Total();
		this.tempoLigado=c.getTempoLigado();
		//this.horadeLigado=c.getHorasdeLigado();
		this.volume=c.getVolume();
		this.marca=c.getMarca();
		this.radio=c.getRadio();
	}

	public double getConsumoEnergetico_pSec(){
		return this.consumoEnergetico_pSec;
	}
	public double getConsumoEnergetico_Total(){
		return this.consumoEnergetico_pSec;
	}
	public int getTempoLigado(){
		return this.tempoLigado;
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

	public void setConsumoEnergetico_pSec(double consumoEnergetico_pSec){
		this.consumoEnergetico_pSec=consumoEnergetico_pSec;
	}
	//public void setConsumoEnergetico_Total(double consumoEnergetico_toal){
	//	this.consumoEnergetico_Total=consumoEnergetico_total;
	//}
	public void setTempoLigado(int tempoLigado){
		this.tempoLigado=tempoLigado;
	}
	//public void setHorasdeLigado(LocalDateTime horasdeLigado){
	//	this.horasdeLigado=horasdeLigado;
	//}
	public void setVolume(int vol){
		this.volume=vol;
	}
	public void setMarca(String marca){
		this.marca=marca;
	}
	public void setRadio(String radio){
		this.radio=radio;
	}

    public boolean equals(Object o) {
        if (this==o) return true; 
        if ((o == null) || (this.getClass() != o.getClass())) return false; 
        
        SmartSpeaker c = (SmartSpeaker) o;
        return c.getModo() == this.getModo() &&
               c.getCustoInstalacao() == this.getCustoInstalacao() &&
               c.getCodigo() == this.getCodigo() &&
               c.getConsumoEnergetico_pSec() == this.consumoEnergetico_pSec && 
               c.getConsumoEnergetico_Total() == this.consumoEnergetico_Total && 
               c.getTempoLigado()==this.tempoLigado &&
               //c.getHorasdeLigado()==this.horasdeLigado &&
               c.getVolume() == this.volume && 
               c.getMarca().equals(this.marca) &&
               c.getRadio().equals(this.radio); 
    }
    
    public SmartSpeaker clone() {
         return new SmartSpeaker(this);
    }
    
    public String toString() {
         StringBuilder sb = new StringBuilder(); 
         sb.append("Coluna: ").append(this.getModo())
          .append("\nID: ").append(this.getCodigo())
          .append("\nConsumo energetico por segundo: ").append(this.consumoEnergetico_pSec)
          .append("\nConsumo Total: ").append(this.consumoEnergetico_Total)
          .append("\nTempo Ligado: ").append(this.tempoLigado)
          //.append("\nHoras que foi ligado: ").append(this.horasdeLigado)
          .append("\nVolume: ").append(this.volume)
          .append("\nMarca: ").append(this.marca)
          .append("\nRadio: ").append(this.radio); 
         return sb.toString();
    }


    public void ligarColuna(){
    	this.horasdeLigado=System.LocalTime.now();
    	this.setModo(Modo.ON);
    }

    public void desligarColuna(){
    	this.tempoLigado=this.horasdeLigado-System.LocalTime.now();
		this.setModo(Modo.OFF);
    }

    public int mudarVolume(int vol){
    	this.volume=vol;
    }

    public String mudarEstacao(String estacao){
    	this.radio=estacao;
    }




}
