import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Long velocidad = 500L; //milisegundos
    public static Scanner sc = new Scanner(System.in);
    public static final int PRESUPUESTO = 15 + (int)(Math.random() * 11);
    public static final String RESET_TEXT = "\033[0m";
    public static final String[] COLORES = {
        "\033[31m",  // Rojo
        "\033[32m",  // Verde
        "\033[34m",  // Azul
        "\033[36m",  // Cian
        "\033[35m",  // Magenta
        "\033[33m",  // Amarillo
    };
    private static int inicioCadenas = 0;

    public static void main(String[] args) { 
        ArrayList<Carro> carros = new ArrayList<>();
        Pista pista = crearPista(carros); // crear pista
        int cantidadCarros, cantidadJugadores;

        // lista de carros automaticos
        while (true) {
            try {
                cantidadCarros = solicitarInt("cantidad de carros automáticos");
                if (cantidadCarros >= 0){
                    break;
                }
                System.out.println("Debe ser igual o mayor a 0.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, ingresa un número entero.");
            }
        }
        pista.crearCarros(carros, cantidadCarros);

        // lista de jugadores
        while (true) {
            try {
                cantidadJugadores = solicitarInt("cantidad de jugadores");
                if (cantidadJugadores >= 0){
                    break;
                }
                System.out.println("Debe ser igual o mayor a 0.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, ingresa un número entero.");
            }
        }

        // crear jugadores
        for (int i = 0; i < cantidadJugadores; i++) {
            Carro carro = crearCarro(pista, i);
            carros.add(carro);
            pista.colocarJugador(carro);
        }

        if (carros.size() == 0){
            System.out.println("La lista de carros está vacía");
            return;
        }

        System.out.print("Presiona Enter para empezar...");
        sc.nextLine();

        ArrayList<Thread> hilos = new ArrayList<>();
        pista.Ejecutar(hilos);

        try {
            for (Thread hilo : hilos) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("LA CARRERA HA TERMINADO " + pista.getNombre());
        pista.ImprimirCarrera();
        pista.imprimirPosiciones();
        pista.imprimirJugadores();
    }

        private static void imprimirMenuPista() {
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println("Selecciona un tipo de pista predeterminada (1-10) o cualquier otro número para crear una personalizada");
        System.out.println("  1) Sprint        (c=2, neg=1-2, pos=2-3, nada=4-6, meta=50 m, t=20 s, d=-1 m/s², maxVel=25 m/s)");
        System.out.println("  2) Medio         (c=3, neg=1-2, pos=1-3, nada=3-5, meta=100 m, t=35 s, d=-1 m/s², maxVel=30 m/s)");
        System.out.println("  3) Maraton       (c=4, neg=2-4, pos=1-3, nada=2-4, meta=200 m, t=70 s, d=-1 m/s², maxVel=50 m/s)");
        System.out.println("  4) Caos          (c=4, neg=2-3, pos=2-3, nada=1-2, meta=120 m, t=30 s, d=-2 m/s², maxVel=28 m/s)");
        System.out.println("  5) Offroad       (c=3, neg=2-4, pos=1-2, nada=4-6, meta=100 m, t=40 s, d=-1 m/s², maxVel=20 m/s)");
        System.out.println("  6) Circuito      (c=3, neg=1-3, pos=2-4, nada=3-5, meta=150 m, t=50 s, d=-1 m/s², maxVel=35 m/s)");
        System.out.println("  7) Rally         (c=4, neg=2-5, pos=1-3, nada=1-3, meta=130 m, t=45 s, d=-2 m/s², maxVel=22 m/s)");
        System.out.println("  8) Ultramaraton  (c=6, neg=3-6, pos=1-4, nada=1-2, meta=300 m, t=90 s, d=-1 m/s², maxVel=50 m/s)");
        System.out.println("  9) Eliminatoria  (c=3, neg=1-2, pos=2-3, nada=5-7, meta=80 m,  t=25 s, d=-1 m/s², maxVel=22 m/s)");
        System.out.println(" 10) VelocidadPura (c=2, neg=1-1, pos=4-6, nada=2-3, meta=70 m,  t=25 s, d=-1 m/s², maxVel=25 m/s)");
        System.out.print("Opción: ");
    }

    private static void imprimirMenuCarros(int numeroJugador) {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("Jugador " + (numeroJugador + 1) + ": Selecciona un coche predeterminado (1-10) o cualquier otro para personalizar");
            System.out.println("  1) Flash   (a=2, v=0, g=18)");
            System.out.println("  2) Bolt    (a=3, v=0, g=16)");
            System.out.println("  3) Steady  (a=1, v=1, g=22)");
            System.out.println("  4) Cruiser (a=2, v=1, g=20)");
            System.out.println("  5) Rocket  (a=3, v=2, g=15)");
            System.out.println("  6) Phantom (a=4, v=0, g=14)");
            System.out.println("  7) Titan   (a=1, v=2, g=25)");
            System.out.println("  8) Vortex  (a=3, v=1, g=17)");
            System.out.println("  9) Ranger  (a=2, v=0, g=19)");
            System.out.println(" 10) Blazer  (a=4, v=1, g=13)");
            System.out.print("Opción: ");
        }


    public static Pista crearPista(ArrayList<Carro> carros) {
        while (true) {
            imprimirMenuPista();
            System.out.println("Selecciona un tipo de pista (1-10) o cualquier otro número para personalizada:");
            String line = sc.nextLine();
            try {
                int tipo = Integer.parseInt(line);
                if (tipo >= 1 && tipo <= 10) {
                    return new Pista(velocidad, tipo, carros);
                }
                // Creación personalizada:
                int desaceleracion = solicitarInt("desaceleración (m/s²) [Usa negativo]");
                int maxTiempo = solicitarInt("tiempo máximo (s) [Que sea positivo]");
                int maxVelocidad = solicitarInt("velocidad máxima (m/s) [Número positivo alto]");
                int eventos = solicitarInt("cantidad de eventos [Que sea positiva]");
                int meta = solicitarInt("cuantos metros sera la meta [Que sea positiva]");
                System.out.print("Ingresa nombre de la pista: ");
                String nombrePista = sc.nextLine();
                return new Pista(velocidad, desaceleracion, maxTiempo, maxVelocidad, eventos, meta, nombrePista, carros);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, ingresa un número entero.");
            }
        }
    }

    public static Carro crearCarro(Pista pista, int numeroJugador) {
        while (true) {
            imprimirMenuCarros(numeroJugador);
            String line = sc.nextLine();
            try {
                int tipo = Integer.parseInt(line);
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();
                String nombreColor = COLORES[inicioCadenas] + nombre + RESET_TEXT;
                inicioCadenas = (inicioCadenas + 1) % COLORES.length;

                switch (tipo) {
                    case 1: return new Carro(nombreColor, 2, 0, 18, pista);
                    case 2: return new Carro(nombreColor, 3, 0, 16, pista);
                    case 3: return new Carro(nombreColor, 1, 1, 22, pista);
                    case 4: return new Carro(nombreColor, 2, 1, 20, pista);
                    case 5: return new Carro(nombreColor, 3, 2, 15, pista);
                    case 6: return new Carro(nombreColor, 4, 0, 14, pista);
                    case 7: return new Carro(nombreColor, 1, 2, 25, pista);
                    case 8: return new Carro(nombreColor, 3, 1, 17, pista);
                    case 9: return new Carro(nombreColor, 2, 0, 19, pista);
                    case 10:return new Carro(nombreColor, 4, 1, 13, pista);
                    default: {
                        System.out.println("Creando coche personalizado (presupuesto " + PRESUPUESTO + " puntos):");                   
                        int puntosRestantes = PRESUPUESTO;

                        int maxA = Math.min(4, puntosRestantes);
                        int aceleracion = solicitarIntRango("aceleración (m/s²) [1-" + maxA + "]", 1, maxA);
                        puntosRestantes -= aceleracion;
                        System.out.println("> Te quedan " + puntosRestantes + " puntos.");

                        int gasolina = solicitarIntRango("gasolina (L) [1-" + puntosRestantes + "]", 1, puntosRestantes);
                        puntosRestantes -= gasolina;
                        System.out.println("> Presupuesto usado. Puntos sobrantes (m/s): " + puntosRestantes);
                        System.out.println("> Lo demás se usó con velocidad inicial");

                        int velocidad = puntosRestantes;

                        return new Carro(nombreColor, aceleracion, velocidad, gasolina, pista);
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, ingresa un número entero.");
            }
        }
    }

    private static int solicitarInt(String prompt) {
        while (true) {
            System.out.print("> " + prompt + ": ");
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe ser un entero válido.");
            }
        }
    }

    private static int solicitarIntRango(String prompt, int min, int max) {
        while (true) {
            System.out.print("> " + prompt + ": ");
            try {
                int v = Integer.parseInt(sc.nextLine());
                if (v >= min && v <= max) {
                    return v;
                }
            } catch (NumberFormatException ignored) {}
            System.out.printf("¡Error! Introduce un entero entre %d y %d.%n", min, max);
        }
    }
}
