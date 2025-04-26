import java.util.ArrayList;

public class Pista {
    // variables Carro
    public final long VELOCIDAD;
    public final int DESACELERACION;
    public final int MAX_TIEMPO;
    public final int MAX_VELOCIDAD;
    // variables de la pista
    public int META;
    public boolean carreraTerminada = false;
    private ArrayList<Carro> carros;
    private ArrayList<Eventos> eventos = new ArrayList<>();

    public Pista(long velocidad, int desaceleracion, int maxTiempo, int maxVelocidad) {
        this.VELOCIDAD = velocidad;
        this.DESACELERACION = desaceleracion;
        this.MAX_TIEMPO = maxTiempo;
        this.MAX_VELOCIDAD = maxVelocidad;
    }

    public void programarCarrera(int meta, final ArrayList<Carro> carros, int cantidadEventos) {
        this.META = meta;
        this.carros = carros;
        for (int i=0; i < cantidadEventos; i++){
            eventos.add(new Eventos((byte) 1, (byte) 1, (byte) 1, this, carros));
        }
    }

    public synchronized void ganeCarrera(final Carro carro) {
        if (!carreraTerminada) {
            carro.estadoFinalizacion = "ganador";
            carreraTerminada = true;
        }else {
            carro.estadoFinalizacion = "ganador";
        }
    }
    // ejecutar la carrera
    public void Ejecutar(final ArrayList<Thread> hilos) {
        System.out.println("LA CARRERA HA COMENZADO");
        for (Eventos evento : eventos) {
            evento.start();
            hilos.add(evento);
        }
        for (Carro carro : carros) {
            carro.start();
            hilos.add(carro);
        }
    }
       
}

