package src;

public class SmartBulb extends SmartDevice {
    public static final int WARM = 2;
    public static final int NEUTRAL = 1;
    public static final int COLD = 0;

    private int tone;

    /**
     * Constructor for objects of class SmartBulb
     */
    public SmartBulb() {
        super();
        this.tone = NEUTRAL;
    }

    public SmartBulb(String id, int tone) {
        super(id);
        this.tone = tone;
    }

    public SmartBulb(String id) {
        super(id);
        this.tone = NEUTRAL;
    }

    public void setTone(int t) {
        if (t>WARM) this.tone = WARM;
        else if (t<COLD) this.tone = COLD;
        else this.tone = t;
    }

    public int getTone() {
        return this.tone;
    }

}

    public void toString(){
        return super().toString + "Tone: " this.getTone();
    }


    @Override
    public double consumoDiario() {
        //Consumo em funçao de um valor fixo + factor em funçao do tipo de luz que esta a ser emitida
        return -1;
    }
}
