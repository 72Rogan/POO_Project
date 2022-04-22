package src;

public abstract class Change<T> {
    protected T toChange; //ao ser protected, mantem-se encapsulamento

    public Change() {
        this.toChange = null; //ainda nao existem mudancas pendentes
    }

    public abstract void createToChange();

    public abstract void change();

    public void setToChange(T toChange) {
        this.toChange = toChange;
    }

    public T getToChange() {
        return this.toChange;
    }
}
