import java.util.ArrayList;
import java.util.List;

public class Eventos extends Thread {
    public final Pista PISTA;
    private final ArrayList<Carro> carros;
    private int tiempo;
    private byte sinEvento;
    private byte trampas;
    private byte plus;

    public Eventos(byte sinEvento, byte trampas, byte plus, Pista pista, ArrayList<Carro> carros) {
        this.PISTA = pista;
        this.carros = carros;
        this.sinEvento = sinEvento;
        this.trampas = trampas;
        this.plus = plus;
        this.tiempo = 0;
    }

    private synchronized Carro obtenerCarro() {
        List<Carro> filtrados = carros.stream()
            .filter(c -> "normal".equals(c.estadoFinalizacion))
            .toList();
        return filtrados.isEmpty() ? null : filtrados.get((int) (Math.random() * filtrados.size()));
    }
    
    private synchronized void eventoTrampa() {
        Carro carroTrampa = obtenerCarro();
        if (carroTrampa != null) {
            //System.out.println(">{XX}(" + carroTrampa.getNombre() + ") ha caido en una trampa");
        }
    }

    private synchronized void eventoPlus() {
        Carro carroTrampa = obtenerCarro();
        if (carroTrampa != null) {
            //System.out.println(">{?}(" + carroTrampa.getNombre() + ") ha caido en una trampa");
        }
    }

    @Override
    public void run() {
        try {
            while (this.tiempo < PISTA.MAX_TIEMPO && this.PISTA.carreraTerminada == false) {
                Thread.sleep(PISTA.VELOCIDAD);
                this.tiempo++;
                // aleatorio de eventos
                int random = (int) (Math.random() * (this.sinEvento + this.trampas + this.plus));
                if (random < this.trampas){
                    this.eventoTrampa();
                } else if (random < (this.trampas + this.plus)) {
                    this.eventoPlus();
                }
                System.out.println(">" + this.tiempo + "s: " + this.carros.size() + " carros en la pista");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
