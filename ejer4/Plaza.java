public class Plaza {
    private int id;
    private boolean ocupada;
    private Coche coche;

    public Plaza(int id, boolean ocupada) {
        this.id = id;
        this.ocupada = ocupada;
    }

    public int getId() {
        return id;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupadaPor(Coche coche) {
        this.ocupada = true;
        this.coche = coche;
    }

    public void liberarPlaza() {
        this.ocupada = false;
        this.coche = null;
    }

    public Coche getCoche() {
        return coche;
    }

    @Override
    public String toString() {
        return "Plaza{" +
                "id=" + id +
                ", ocupada=" + ocupada +
                '}';
    }
}