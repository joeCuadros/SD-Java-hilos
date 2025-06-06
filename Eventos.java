import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class Eventos extends Thread {
    public final Pista PISTA;
    private final ArrayList<Carro> carros;
    private int tiempo;
    private byte sinEvento;
    private byte desventajas;
    private byte ventajas;


    public Eventos(byte desventajas, byte ventajas, byte sinEvento, Pista pista, ArrayList<Carro> carros) {
        this.PISTA = pista;
        this.carros = carros;
        this.sinEvento = sinEvento;
        this.desventajas = desventajas;
        this.ventajas = ventajas;
        this.tiempo = 0;
    }

    private Carro obtenerCarro() {
        synchronized (carros) {
            ArrayList<Carro> copia = new ArrayList<>(carros);
            Collections.shuffle(copia);
            for (Carro c : copia) {
                if ("normal".equals(c.estadoFinalizacion)) {
                    return c;
                }
            }
            return null;
        }
    }

    private synchronized void eventoTrampa() {
        Carro carro = obtenerCarro();
        if (carro != null) {
            List<Consumer<Carro>> efectos = PISTA.efectosTrampas;
            Consumer<Carro> efecto = efectos.get(
                (int)(Math.random() * efectos.size())
            );
            efecto.accept(carro);
        }else {
            PISTA.carreraTerminada = true;
        }
    }

    private synchronized void eventoventajas() {
        Carro carro = obtenerCarro();
        if (carro != null) {
            List<Consumer<Carro>> efectos = PISTA.efectosVentajas;
            Consumer<Carro> efecto = efectos.get(
                (int)(Math.random() * efectos.size())
            );
            efecto.accept(carro);
        }else {
            PISTA.carreraTerminada = true;
        }
    }

    @Override
    public void run() {
        try {
            while (this.tiempo < PISTA.MAX_TIEMPO && this.PISTA.carreraTerminada == false) {
                Thread.sleep(PISTA.VELOCIDAD);
                this.tiempo++;
                // aleatorio de eventos
                int random = (int) (Math.random() * (this.sinEvento + this.desventajas + this.ventajas));
                if (random < this.desventajas){
                    this.eventoTrampa();
                } else if (random < (this.desventajas + this.ventajas)) {
                    this.eventoventajas();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
