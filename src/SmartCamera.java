package src;

public class SmartCamera extends SmartDevice{
    // private int res;
    private int width;
    private int height;
    private double tamanhoFicheiro;

    public SmartCamera(){
        super();
        //this.res = 0;
        this.width = 0;
        this.height = 0;
        this.tamanhoFicheiro = 0;
    }

    public SmartCamera(String id, double custoInstalacao) {
        super(id, custoInstalacao);
        this.width = 0;
        this.height = 0;
        this.tamanhoFicheiro = 0;
    }

    public SmartCamera(String id, double custoInstalacao, Modo modo,
                       int width, int height, double tamanhoFicheiro) {
        super(id,custoInstalacao,modo);
        this.width = width;
        this.height = height;
        this.tamanhoFicheiro = tamanhoFicheiro;
    }

    public SmartCamera(SmartCamera c) {
        super(c);
        this.width = c.width;
        this.height = c.height;
        this.tamanhoFicheiro = c.tamanhoFicheiro;
    }

    @Override
    public double consumoDiario() {
        //Consumo em funçao do tamanho do ficheiro que geram * a resoluçao da imagem
        int fator = this.width * this.height / 1000; // /1000 para tornar o numero mais pequeno
        return fator * tamanhoFicheiro;
    }

    @Override
    public SmartDevice clone() {
        return new SmartCamera(this);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getTamanhoFicheiro() {
        return tamanhoFicheiro;
    }

    public void setTamanhoFicheiro(double tamanhoFicheiro) {
        this.tamanhoFicheiro = tamanhoFicheiro;
    }

}
