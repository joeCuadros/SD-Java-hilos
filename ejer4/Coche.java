import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Coche extends Thread {

    private String id;
    private boolean aparcado;
    private Semaphore semaforo;
    private static int MAX_ITERACIONES = 10;

    private static ArrayList<Coche> coches = new ArrayList<>();

    public Coche(String id, Semaphore semaforo) {
        this.id = id;
        this.semaforo = semaforo;
        this.aparcado = false;

        coches.add(this);
    }

    @Override
    public void run() {
        boolean plazaConseguida;
        boolean decideAbandonarAparcamiento;

        int iteracionActual = 0;

        // El proceso se debe repetir n veces
        while (iteracionActual < MAX_ITERACIONES) {
            try {
                // Tenemos que comprobar que este coche no está aparcado ya. Un coche no se puede aparcar si ya está aparcado
                // También tenemos que comprobar que hay plazas disponibles.
                if (!this.aparcado) {
                    System.out.println(String.format("Coche %s buscando plaza de las %s plazas libres", this.id, this.semaforo.availablePermits()));
                    // Hay un 50% de posibilidades de conseguir plaza
                    plazaConseguida = ThreadLocalRandom.current().nextDouble(1) >= 0.5;
                    if (plazaConseguida) {
                        // Debemos comprobar que hay plazas disponibles, ya que otro coche se nos puede haber adelantado. Cosas de hilos.
                        if (this.semaforo.availablePermits() != 0) {
                            this.semaforo.acquire();
                            this.aparcado = true;
                            System.out.println(String.format("Coche %s conseguida una plaza de las %d plazas disponibles", this.id, this.semaforo.availablePermits()));
                        }
                    } else {
                        System.out.println(String.format("Plaza no conseguida en esta iteración para el coche %s", this.id));
                    }
                } else {
                    // El coche a veces decide salir del aparcamiento. Hay un 25% de posibilidades de que esto ocurra.
                    decideAbandonarAparcamiento = ThreadLocalRandom.current().nextDouble(1) >= 0.25;

                    if (decideAbandonarAparcamiento) {
                        this.semaforo.release();
                        this.aparcado = false;
                        System.out.println(String.format("Coche %s saliendo del aparcamiento...", this.id));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                iteracionActual++;
            }
        }
    }

    public static ArrayList<Coche> getCoches() {
        return coches;
    }

    public boolean isAparcado() {
        return aparcado;
    }

    @Override
    public String toString() {
        return "Coche{" +
                "id='" + id + '\'' +
                ", aparcado=" + aparcado +
                '}';
    }
}
