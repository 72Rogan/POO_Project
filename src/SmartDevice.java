package src;

public class SmartDevice {
    private int codigo;
    private double custoInstalacao;
    private Modo modo;

    public enum Modo{
        ON, OFF;
    }

    public SmartDevice(int codigo, double custoInstalacao) {
        this.codigo = codigo;
        this.custoInstalacao = custoInstalacao;
        this.modo = Modo.OFF;
    }

    public SmartDevice(int codigo, double custoInstalacao, Modo modo) {
        this.codigo = codigo;
        this.custoInstalacao = custoInstalacao;
        this.modo = modo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getCustoInstalacao() {
        return custoInstalacao;
    }

    public void setCustoInstalacao(double custoInstalacao) {
        this.custoInstalacao = custoInstalacao;
    }

    public Modo getModo() {
        return modo;
    }

    public void setModo(Modo modo) {
        this.modo = modo;
    }
}
