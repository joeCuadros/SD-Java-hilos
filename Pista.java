import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Pista {
    // variables Carro
    public final long VELOCIDAD;
    public final int DESACELERACION;
    public final int MAX_TIEMPO;
    public final int MAX_VELOCIDAD;
    public static final int MAX_PUESTO = 3;
    // variables de la pista
    public int META;
    public volatile boolean carreraTerminada = false;
    private ArrayList<Carro> carros;
    private final List<Carro> posiciones;
    // eventos de la carrera
    private ArrayList<Eventos> eventos = new ArrayList<>();
    public final List<Consumer<Carro>> efectosTrampas = new ArrayList<>();
    public final List<Consumer<Carro>> efectosVentajas = new ArrayList<>();

    public Pista(long velocidad, int desaceleracion, int maxTiempo, int maxVelocidad) {
        this.VELOCIDAD = velocidad;
        this.DESACELERACION = desaceleracion;
        this.MAX_TIEMPO = maxTiempo;
        this.MAX_VELOCIDAD = maxVelocidad;
        this.posiciones = new ArrayList<>();
    }

    public void programarCarrera(int meta, final ArrayList<Carro> carros, int cantidadEventos) {
        this.META = meta;
        this.carros = carros;
        for (int i=0; i < cantidadEventos; i++){
            eventos.add(new Eventos((byte) 1, (byte) 1, (byte) 1, this, carros));
        }
        // AGREGAR TRAMPAS
        efectosTrampas.add(carro -> {
            // variables internos
            final String nombreEfecto = "Bache";
            final int min = -5;
            final int max = -1;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(--){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha perdido " + (-numeroAleatorio) + " m/s de velocidad.");
            // ejecutar efecto
            carro.modificarVelocidad(numeroAleatorio);
        });
        efectosTrampas.add(carro -> {
            // variables internos
            final String nombreEfecto = "Perdida de gasolina";
            final int min = -3;
            final int max = -1;
            // ejecutar efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(--){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha perdido " + (-numeroAleatorio) + " L");
            // ejecutar efecto
            carro.modificarGasolina(numeroAleatorio);
        });
        // AGREGAR VENTAJAS
        efectosVentajas.add(carro -> {
            // variables internos
            final String nombreEfecto = "Turbo";
            final int min = 2;
            final int max = 5;
            // ejecutar efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(++){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha ganado " + numeroAleatorio + " m/s de velocidad.");
            // ejecutar efecto
            carro.modificarVelocidad(numeroAleatorio);
        });
        efectosVentajas.add(carro -> {
            // variables internos
            final String nombreEfecto = "Recarga de gasolina";
            final int min = 1;
            final int max = 3;
            // ejecutar efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(++){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha ganado " + numeroAleatorio + " L de gasolina.");
            // ejecutar efecto
            carro.modificarGasolina(numeroAleatorio);
        });
    }

    public synchronized void ganeCarrera(final Carro carro) {
        if (!carreraTerminada && carro.estadoFinalizacion.equals("normal")) {
                carro.estadoFinalizacion = (posiciones.size()+1) + " puesto";
                if (posiciones.size() == (MAX_PUESTO - 1 )) {
                    carreraTerminada = true;
                }
            posiciones.add(carro);
        }
    }
    // ejecutar la carrera
    public void Ejecutar(final ArrayList<Thread> hilos) {
        System.out.println("LA CARRERA HA COMENZADO");
        for (Carro carro : carros) {
            carro.start();
            hilos.add(carro);
        }
        for (Eventos evento : eventos) {
            evento.start();
            hilos.add(evento);
        }
        
    }

    public void ImprimirCarrera() {
        for (Carro carro : this.carros) {
            carro.terminado(); 
        }
    }

    public void imprimirPosiciones() {
        System.out.println("POSICIONES: ");
        for (Carro carro : this.posiciones) {
            carro.terminadoPuesto();
        }
    }
}

