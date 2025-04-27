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
            final String nombreEfecto = "Rebote";
            final int min = -15;
            final int max = -7;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(--){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha perdido " + (-numeroAleatorio) + " m de distancia.");
            // ejecutar efecto
            carro.modificarDistancia(numeroAleatorio);
        });
        efectosTrampas.add(carro -> {
            // variables internos
            final String nombreEfecto = "Retroceso";
            final int min = -25;
            final int max = -10;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(--){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha perdido " + (-numeroAleatorio) + " m de distancia.");
            // ejecutar efecto
            carro.modificarDistancia(numeroAleatorio);
        });
        efectosTrampas.add(carro -> {
            // variables internos
            final String nombreEfecto = "Deslizamiento";
            final int min = -10;
            final int max = -3;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(--){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha perdido " + (-numeroAleatorio) + " m de distancia.");
            // ejecutar efecto
            carro.modificarDistancia(numeroAleatorio);
        });
        efectosTrampas.add(carro -> {
            // variables internos
            final String nombreEfecto = "Bache";
            final int min = -3;
            final int max = -1;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(--){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha perdido " + (-numeroAleatorio) + " m/s de velocidad.");
            // ejecutar efecto
            carro.modificarDistancia(numeroAleatorio);
        });
        efectosTrampas.add(carro -> {
            // variables internos
            final String nombreEfecto = "Obstaculo";
            final int min = -5;
            final int max = 0;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(--){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha perdido " + (-numeroAleatorio) + " m/s de velocidad.");
            // ejecutar efecto
            carro.modificarDistancia(numeroAleatorio);
        });
        efectosTrampas.add(carro -> {
            // variables internos
            final String nombreEfecto = "Viento(-)";
            final int min = -4;
            final int max = -1;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(--){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha perdido " + (-numeroAleatorio) + " m/s de velocidad.");
            // ejecutar efecto
            carro.modificarDistancia(numeroAleatorio);
        });
        efectosTrampas.add(carro -> {
            // variables internos
            final String nombreEfecto = "MotorFallido";
            final int min = -2;
            final int max = -1;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(--){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha perdido " + (-numeroAleatorio) + " m/s^2 de aceleracion.");
            // ejecutar efecto
            carro.modificarAceleracion(numeroAleatorio, null);
        });
        efectosTrampas.add(carro -> {
            // variables internos
            final String nombreEfecto = "Fuga";
            final int min = -4;
            final int max = -1;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(--){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha perdido " + (-numeroAleatorio) + " L de gasolina.");
            // ejecutar efecto
            carro.modificarGasolina(numeroAleatorio);
        });
        efectosTrampas.add(carro -> {
            // variables internos
            final String nombreEfecto = "GolpeMortal";
            final int min = -3;
            final int max = 0;
            final int min2 = -2;
            final int max2 = 0;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            final int numeroAleatorio2 = min2 + (int)(Math.random() * (max2 - min2 + 1));
            System.out.println("(--){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha perdido " + (-numeroAleatorio) + " L de gasolina y " + 
                (-numeroAleatorio2) + " m/s^2 de aceleracion.");
            // ejecutar efecto
            carro.modificarGasolina(numeroAleatorio);
            carro.modificarAceleracion(numeroAleatorio2, null);
        });
        efectosTrampas.add(carro -> {
            // variables internos
            final String nombreEfecto = "SuperViento(-)";
            final int min = -10;
            final int max = 0;
            final int min2 = -4;
            final int max2 = 0;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            final int numeroAleatorio2 = min2 + (int)(Math.random() * (max2 - min2 + 1));
            System.out.println("(--){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha perdido " + (-numeroAleatorio) + " m de distancia y " + 
                (-numeroAleatorio2) + " m/s de velocidad.");
            // ejecutar efecto
            carro.modificarDistancia(numeroAleatorio);
            carro.modificarVelocidad(numeroAleatorio2);
        });
        efectosTrampas.add(carro -> {
            // variables internos
            final String nombreEfecto = "GolpeTotal";
            final int min = -5;
            final int max = 0;
            final int min2 = -2;
            final int max2 = 0;
            final int min3 = -4;
            final int max3 = -1;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            final int numeroAleatorio2 = min2 + (int)(Math.random() * (max2 - min2 + 1));
            final int numeroAleatorio3 = min3 + (int)(Math.random() * (max3 - min3 + 1));
            System.out.println("(----){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha perdido " + (-numeroAleatorio) + " L de gasolina, " + 
                (-numeroAleatorio2) + " m/s^2 de aceleracion y " + 
                (-numeroAleatorio3) + " m/s de velocidad.");
            // ejecutar efecto
            carro.modificarGasolina(numeroAleatorio);
            carro.modificarAceleracion(numeroAleatorio2, null);
            carro.modificarVelocidad(numeroAleatorio3);
            
        });
        // AGREGAR VENTAJAS
        efectosVentajas.add(carro -> {
            // variables internos
            final String nombreEfecto = "Atajo";
            final int min = 8;
            final int max = 15;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(++){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha ganado " + numeroAleatorio + " m de distancia.");
            // ejecutar efecto
            carro.modificarDistancia(numeroAleatorio);
        });
        efectosVentajas.add(carro -> {
            // variables internos
            final String nombreEfecto = "Impulso";
            final int min = 5;
            final int max = 12;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(++){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha ganado " + numeroAleatorio + " m de distancia.");
            // ejecutar efecto
            carro.modificarDistancia(numeroAleatorio);
        });
        efectosVentajas.add(carro -> {
            // variables internos
            final String nombreEfecto = "SuperAtajo";
            final int min = 12;
            final int max = 25;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(++){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha ganado " + numeroAleatorio + " m de distancia.");
            // ejecutar efecto
            carro.modificarDistancia(numeroAleatorio);
        });
        efectosVentajas.add(carro -> {
            // variables internos
            final String nombreEfecto = "Turbo";
            final int min = 1;
            final int max = 3;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(++){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha ganado " + numeroAleatorio + " m/s de velocidad.");
            // ejecutar efecto
            carro.modificarVelocidad(numeroAleatorio);
        });
        efectosVentajas.add(carro -> {
            // variables internos
            final String nombreEfecto = "Viento(+)";
            final int min = 1;
            final int max = 4;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(++){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha ganado " + numeroAleatorio + " m/s de velocidad.");
            // ejecutar efecto
            carro.modificarVelocidad(numeroAleatorio);
        });
        efectosVentajas.add(carro -> {
            // variables internos
            final String nombreEfecto = "PistaLisa";
            final int min = 2;
            final int max = 5;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(++){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha ganado " + numeroAleatorio + " m/s de velocidad.");
            // ejecutar efecto
            carro.modificarVelocidad(numeroAleatorio);
        });
        efectosVentajas.add(carro -> {
            // variables internos
            final String nombreEfecto = "MotorMejorado";
            final int min = 1;
            final int max = 2;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(++){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha ganado " + numeroAleatorio + " m/s^2 de aceleracion.");
            // ejecutar efecto
            carro.modificarAceleracion(numeroAleatorio, null);
        });
        efectosVentajas.add(carro -> {
            // variables internos
            final String nombreEfecto = "Recarga";
            final int min = 4;
            final int max = 8;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            System.out.println("(++){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha ganado " + numeroAleatorio + " L de gasolina.");
            // ejecutar efecto
            carro.modificarGasolina(cantidadEventos);
        });
        efectosVentajas.add(carro -> {
            // variables internos
            final String nombreEfecto = "SuperMejora";
            final int min = 3;
            final int max = 5;
            final int min2 = 2;
            final int max2= 6;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            final int numeroAleatorio2 = min2 + (int)(Math.random() * (max2 - min2 + 1));
            System.out.println("(++){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha ganado " + numeroAleatorio + " L de gasolina y " + numeroAleatorio2
                + " m/s de velocidad");
            // ejecutar efecto
            carro.modificarGasolina(numeroAleatorio);
            carro.modificarVelocidad(numeroAleatorio2);
        });
        efectosVentajas.add(carro -> {
            // variables internos
            final String nombreEfecto = "SuperViento(+)";
            final int min = 5;
            final int max = 12;
            final int min2 = 2;
            final int max2= 4;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            final int numeroAleatorio2 = min2 + (int)(Math.random() * (max2 - min2 + 1));
            System.out.println("(++){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha ganado " + numeroAleatorio + " m de distancia y " + numeroAleatorio2
                + " m/s de velocidad.");
            // ejecutar efecto
            carro.modificarDistancia(numeroAleatorio);
            carro.modificarVelocidad(numeroAleatorio2);
        });
        efectosVentajas.add(carro -> {
            // variables internos
            final String nombreEfecto = "MejoraDefinitiva";
            final int min = 5;
            final int max = 10;
            final int min2 = 2;
            final int max2= 8;
            final int min3 = 1;
            final int max3= 2;
            // imprimir efecto
            final int numeroAleatorio = min + (int)(Math.random() * (max - min + 1));
            final int numeroAleatorio2 = min2 + (int)(Math.random() * (max2 - min2 + 1));
            final int numeroAleatorio3 = min3 + (int)(Math.random() * (max3 - min3 + 1));
            System.out.println("(++++){"+ nombreEfecto +"} (" + carro.getNombre() + 
                ") ha ganado " + numeroAleatorio + " L de gasolina, " + numeroAleatorio2
                + " m/s de velocidad y " + numeroAleatorio3 + " m/s^2 de aceleracion.");
            // ejecutar efecto
            carro.modificarGasolina(numeroAleatorio);
            carro.modificarVelocidad(numeroAleatorio2);
            carro.modificarAceleracion(numeroAleatorio3, null);
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

