import java.time.LocalDateTime;

public class Coluna{

	public enum Modo{
		ON, OFF;
	}

	private Modo modo;
	private double custoInstalacao;
	private String id;
	private double consumoEnergetico_pSec;
	private double consumoEnergetico_Total;
	private int tempoLigado;
	private LocalDateTime horaDeLigado;
	private int volume;
	private String marca;
	private String radio;

	public Coluna(){
		this.modo = Modo.OFF;
		this.custoInstalacao = 1;
		this.id = "N/A";
		this.consumoEnergetico_pSec=1;
		this.consumoEnergetico_Total=0;
		this.tempoLigado = 0;
		this.horaDeLigado = 0;
		this.volume =0;
		this.marca = "N/A";
		this.raio = "N/A";
	}

	public Coluna(Modo x, double instalacao, String identificador, double consumoEnergetico_pSec, double consumoEnergetico_Total, 
		int tempoLigado, LocalDateTime horadeLigado, int vol, String marca, String radio){
		
		switch(x){
			case ON:
				this.modo = Modo.ON;
				break;
			case OFF:
				this.modo = Modo.OFF;
				break;
		}

		this.custoInstalacao=instalacao;
		this.id=identificador;
		this.consumoEnergetico_pSec=consumoEnergetico_pSec;
		this.consumoEnergetico_Total=consumoEnergetico_Total;
		this.tempoLigado=tempoLigado;
		this.horaDeLigado=horaDeLigado;
		this.volume=vol;
		this.marca=marca;
		this.radio=radio;
	}

	public Coluna(Coluna c){
		this.modo=c.getModo();
		this.custoInstalacao=c.getCustoInstalacao();
		this.id=c.getId();
		this.consumoEnergetico_pSec=c.getConsumoEnergetico_pSec();
		this.consumoEnergetico_Total=c.getConsumoEnergetico_Total();
		this.tempoLigado=c.getTempoLigado();
		this.horadeLigado=c.getHorasdeLigado();
		this.volume=c.getVolume();
		this.marca=c.getMarca();
		this.radio=c.getRadio();
	}

	public Modo getModo(){
		return this.modo;
	}
	public double getCustoInstalacao(){
		return this.custoInstalacao;
	}
	public String getId(){
		return this.id;
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
	public LocalDateTime getHorasdeLigado(){
		return this.horadeLigado;
	}
	public int getVolume(){
		return this.volume;
	}
	public String getMarca(){
		return this.marca;
	}
	public String getRadio(){
		return this.radio;
	}

	public void setModo(Modo m){
		this.modo=m;
	}
	public void setCustoInstalacao(double custoInstalacao){
		this.custoInstalacao=custoInstalacao;
	}
	public void setId(String identificador){
		this.id=identificador;
	}
	public void setConsumoEnergetico_pSec(double consumoEnergetico_pSec){
		this.consumoEnergetico_pSec=consumoEnergetico_pSec;
	}
	public void setConsumoEnergetico_Total(double consumoEnergetico_toal){
		this.consumoEnergetico_Total=consumoEnergetico_total;
	}
	public void setTempoLigado(int tempoLigado){
		this.tempoLigado=tempoLigado;
	}
	public void setHorasdeLigado(LocalDateTime horasdeLigado){
		this.horasdeLigado=horasdeLigado;
	}
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
        
        Coluna C = (Coluna) o; 
        return c.getModo() == this.modo && 
               c.getCustoInstalacao() == this.custoInstalacao &&
               c.getId().equals(this.id)  &&               
               c.getConsumoEnergetico_pSec() == this.consumoEnergetico_pSec && 
               c.getConsumoEnergetico_Total() == this.consumoEnergetico_Total && 
               c.getTempoLigado()==this.tempoLigado &&
               c.getHorasdeLigado()==this.horasdeLigado &&
               c.getVolume() == this.volume && 
               c.getMarca().equals(this.marca) &&
               c.getRadio().equals(this.radio); 
    }
    
    public Coluna clone() {
         return new Coluna(this);    
    }
    
    public String toString() {
         StringBuilder sb = new StringBuilder(); 
         sb.append("Coluna: ").append(this.modo)
          .append("\nID: ").append(this.id)
          .append("\nConsumo energetico por segundo: ").append(this.consumoEnergetico_pSec)
          .append("\nConsumo Total: ").append(this.consumoEnergetico_Total)
          .append("\nTempo Ligado: ").append(this.tempoLigado)
          .append("\nHoras que foi ligado: ").append(this.horasdeLigado)
          .append("\nVolume: ").append(this.volume)
          .append("\nMarca: ").append(this.marca)
          .append("\nRadio: ").append(this.radio); 
         return sb.toString();
    }


    public void ligarColuna(){
    	this.horasdeLigado=System.LocalTime.now();
    	this.modo=Modo.ON;
    }

    public void desligarColuna(){
    	this.tempoLigado=this.horasdeLigado-System.LocalTime.now();
    	this.modo=Modo.OFF;
    }

    public int mudarVolume(int vol){
    	this.volume=vol;
    }

    public String mudarEstacao(String estacao){
    	this.radio=estacao;
    }




}