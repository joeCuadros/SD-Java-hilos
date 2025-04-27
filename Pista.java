import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Pista {
    // variables Carro
    public final long VELOCIDAD;
    public int DESACELERACION;
    public int MAX_TIEMPO;
    public int MAX_VELOCIDAD;
    public int META;
    public static final int MAX_PUESTO = 3;
    // variables de la pista
    public volatile boolean carreraTerminada = false;
    private ArrayList<Carro> carros;
    private final List<Carro> posiciones;
    private final List<Carro> jugadores;
    private int cantidadCarros = 0;
    // eventos de la carrera
    private String nombreEvento;
    private ArrayList<Eventos> eventos = new ArrayList<>();
    public final List<Consumer<Carro>> efectosTrampas = new ArrayList<>();
    public final List<Consumer<Carro>> efectosVentajas = new ArrayList<>();

    public Pista(long velocidad, int desaceleracion, int maxTiempo, int maxVelocidad,int cantidadEventos, String nombre, final ArrayList<Carro> carros) {
        for (int i=0; i < cantidadEventos; i++){
            eventos.add(new Eventos(
                (byte) numeroAleatorio(1, 4),
                (byte) numeroAleatorio(1, 4),
                (byte) numeroAleatorio(1, 4),
                this, carros
            ));
        }
        this.VELOCIDAD = velocidad;
        this.DESACELERACION = desaceleracion;
        this.MAX_TIEMPO = maxTiempo;
        this.MAX_VELOCIDAD = maxVelocidad;
        this.nombreEvento = nombre;
        this.carros = carros;
        this.posiciones = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        this.agregarEfectos();
    }

    public Pista(long velocidad,int tipo, final ArrayList<Carro> carros){
        this.carros = carros;
        this.VELOCIDAD = velocidad;
        switch (tipo) {
            case 1 -> { // Sprint
                nombreEvento = "Sprint";
                int cantidadEventos = 2;
                for (int i = 0; i < cantidadEventos; i++) {
                    eventos.add(new Eventos(
                        (byte) numeroAleatorio(1, 2),
                        (byte) numeroAleatorio(2, 3),
                        (byte) numeroAleatorio(4, 6),
                        this, carros
                    ));
                }
                this.META = 50;
                this.MAX_TIEMPO = 20;
                this.DESACELERACION = -1;
                this.MAX_VELOCIDAD = 25;
            }
            case 2 -> { // Medio
                nombreEvento = "Medio";
                int cantidadEventos = 3;
                for (int i = 0; i < cantidadEventos; i++) {
                    eventos.add(new Eventos(
                        (byte) numeroAleatorio(1, 2),
                        (byte) numeroAleatorio(1, 3),
                        (byte) numeroAleatorio(3, 5),
                        this, carros
                    ));
                }
                this.META = 100;
                this.MAX_TIEMPO = 35;
                this.DESACELERACION = -1;
                this.MAX_VELOCIDAD = 30;
            }
            case 3 -> { // Maratón
                nombreEvento = "Maraton";
                int cantidadEventos = 4;
                for (int i = 0; i < cantidadEventos; i++) {
                    eventos.add(new Eventos(
                        (byte) numeroAleatorio(2, 4),
                        (byte) numeroAleatorio(1, 3),
                        (byte) numeroAleatorio(2, 4),
                        this, carros
                    ));
                }
                this.META = 200;
                this.MAX_TIEMPO = 70;
                this.DESACELERACION = -1;
                this.MAX_VELOCIDAD = 50;
            }
            case 4 -> { // Caos
                nombreEvento = "Caos";
                int cantidadEventos = 4;
                for (int i = 0; i < cantidadEventos; i++) {
                    eventos.add(new Eventos(
                        (byte) numeroAleatorio(2, 3),
                        (byte) numeroAleatorio(2, 3),
                        (byte) numeroAleatorio(1, 2),
                        this, carros
                    ));
                }
                this.META = 120;
                this.MAX_TIEMPO = 30;
                this.DESACELERACION = -2;
                this.MAX_VELOCIDAD = 28;
            }
            case 5 -> { // Offroad
                nombreEvento = "Offroad";
                int cantidadEventos = 3;
                for (int i = 0; i < cantidadEventos; i++) {
                    eventos.add(new Eventos(
                        (byte) numeroAleatorio(2, 4),
                        (byte) numeroAleatorio(1, 2),
                        (byte) numeroAleatorio(4, 6),
                        this, carros
                    ));
                }
                this.META = 100;
                this.MAX_TIEMPO = 40;
                this.DESACELERACION = -1;
                this.MAX_VELOCIDAD = 20;
            }
            case 6 -> { // Circuito
                nombreEvento = "Circuito";
                int cantidadEventos = 3;
                for (int i = 0; i < cantidadEventos; i++) {
                    eventos.add(new Eventos(
                        (byte) numeroAleatorio(1, 3),
                        (byte) numeroAleatorio(2, 4),
                        (byte) numeroAleatorio(3, 5),
                        this, carros
                    ));
                }
                this.META = 150;
                this.MAX_TIEMPO = 50;
                this.DESACELERACION = -1;
                this.MAX_VELOCIDAD = 35;
            }
            case 7 -> { // Rally
                nombreEvento = "Rally";
                int cantidadEventos = 4;
                for (int i = 0; i < cantidadEventos; i++) {
                    eventos.add(new Eventos(
                        (byte) numeroAleatorio(2, 5),
                        (byte) numeroAleatorio(1, 3),
                        (byte) numeroAleatorio(1, 3),
                        this, carros
                    ));
                }
                this.META = 130;
                this.MAX_TIEMPO = 45;
                this.DESACELERACION = -2;
                this.MAX_VELOCIDAD = 22;
            }
            case 8 -> { // Ultramaratón
                nombreEvento = "Ultramaraton";
                int cantidadEventos = 6;
                for (int i = 0; i < cantidadEventos; i++) {
                    eventos.add(new Eventos(
                        (byte) numeroAleatorio(3, 6),
                        (byte) numeroAleatorio(1, 4),
                        (byte) numeroAleatorio(1, 2),
                        this, carros
                    ));
                }
                this.META = 300;
                this.MAX_TIEMPO = 90;
                this.DESACELERACION = -1;
                this.MAX_VELOCIDAD = 50;
            }
            case 9 -> { // Eliminatoria
                nombreEvento = "Eliminatoria";
                int cantidadEventos = 3;
                for (int i = 0; i < cantidadEventos; i++) {
                    eventos.add(new Eventos(
                        (byte) numeroAleatorio(1, 2),
                        (byte) numeroAleatorio(2, 3),
                        (byte) numeroAleatorio(5, 7),
                        this, carros
                    ));
                }
                this.META = 80;
                this.MAX_TIEMPO = 25;
                this.DESACELERACION = -1;
                this.MAX_VELOCIDAD = 22;
            }
            case 10 -> { // Velocidad Pura
                nombreEvento = "VelocidadPura";
                int cantidadEventos = 2;
                for (int i = 0; i < cantidadEventos; i++) {
                    eventos.add(new Eventos(
                        (byte) numeroAleatorio(1, 1),
                        (byte) numeroAleatorio(4, 6),
                        (byte) numeroAleatorio(2, 3),
                        this, carros
                    ));
                }
                this.META = 70;
                this.MAX_TIEMPO = 25;
                this.DESACELERACION = -1;
                this.MAX_VELOCIDAD = 25;
            }
            default -> throw new IllegalArgumentException("Tipo de carrera desconocido: " + tipo);
        }        
        this.posiciones = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        this.agregarEfectos();
    }

    public String getNombre(){
        return this.nombreEvento;
    }

    public void colocarJugador(Carro carro){
        jugadores.add(carro);
    }

    public void crearCarro(final ArrayList<Carro> carros) {
        final int numeroAleatorio = 1 + (int)(Math.random() * 10);
        switch (numeroAleatorio) {
            case 1 -> {carros.add(new Carro("Flash"+cantidadCarros+"", 2, 0, 18, this));}
            case 2 -> {carros.add(new Carro("Bolt("+cantidadCarros+")", 3, 0, 16, this));}
            case 3 -> {carros.add(new Carro("Steady("+cantidadCarros+")", 1, 1, 22, this));}
            case 4 -> {carros.add(new Carro("Cruiser("+cantidadCarros+")", 2, 1, 20, this));}
            case 5 -> {carros.add(new Carro("Rocket("+cantidadCarros+")", 3, 2, 15, this));}
            case 6 -> {carros.add(new Carro("Phantom("+cantidadCarros+")", 4, 0, 14, this));}
            case 7 -> {carros.add(new Carro("Titan("+cantidadCarros+")", 1, 2, 25, this));}
            case 8 -> {carros.add(new Carro("Vortex("+cantidadCarros+")", 3, 1, 17, this));}
            case 9 -> {carros.add(new Carro("Ranger("+cantidadCarros+")", 2, 0, 19, this));}
            case 10 -> {carros.add(new Carro("Blazer("+cantidadCarros+")", 4, 1, 13, this));}
        }
        this.cantidadCarros++;
    }

    public void crearCarros(final ArrayList<Carro> carros, int cantidadCarros) {
        for (int i = 0; i < cantidadCarros; i++) {
            this.crearCarro(carros);
        }
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
        System.out.println("LA CARRERA HA COMENZADO " + nombreEvento);
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
        System.out.println("POSICIONES DE "+ nombreEvento +": ");
        for (Carro carro : this.posiciones) {
            carro.terminadoPuesto();
        }
    }

    public void imprimirJugadores() {
        System.out.println("JUGADORES: ");
        for (Carro carro : this.jugadores) {
            carro.terminadoPuesto();
        }
    }

    private int numeroAleatorio(int min, int max) {
        return min + (int)(Math.random() * (max - min + 1));
    }

    private void agregarEfectos() {
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
            carro.modificarVelocidad(numeroAleatorio);
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
            carro.modificarVelocidad(numeroAleatorio);;
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
            carro.modificarVelocidad(numeroAleatorio);
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
            carro.modificarGasolina(numeroAleatorio);
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
}

