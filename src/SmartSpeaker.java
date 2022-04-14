package src;

import src.SmartDevice;

import java.time.LocalDateTime;

public class SmartSpeaker extends SmartDevice {

	private int volume;
	public static final int MAX = 100;
	private String marca;
	private String radio;

	public SmartSpeaker(){
		super("", 0);
		//this.horaDeLigado = 0;
		this.volume = 0; //volume max = 100
		this.marca = "N/A";
		this.radio = "N/A";
	}

	public SmartSpeaker(Modo x, double instalacao,String id, int vol, String marca, String radio){
		super(id,instalacao,x);
		this.volume=vol;
		this.marca=marca;
		this.radio=radio;
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
         sb.append("Coluna: ").append(this.getModo())
          .append("\nID: ").append(this.getID())
          .append("\nVolume: ").append(this.volume)
          .append("\nMarca: ").append(this.marca)
          .append("\nRadio: ").append(this.radio); 
         return sb.toString();
    }

	@Override
	public double consumoDiario() {
		//consumo em funçao da marca + factor em funçao do volume
		return -1;
	}
}
