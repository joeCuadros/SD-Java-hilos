import java.util.ArrayList;
public class Main {
    public static void main(String[] args) { 
        Pista pista = new Pista(1000,-1,20,100); 
        // lista de carros
        ArrayList<Carro> carros = new ArrayList<>();
        carros.add(new Carro("2", 1, 3,25, pista));
        carros.add(new Carro("2-1", 1, 3,10, pista));
        carros.add(new Carro("2-2", 1, 3,20, pista));
        carros.add(new Carro("2-3", 2, 1,15, pista));

        pista.programarCarrera(100, carros,1);
        
        ArrayList<Thread> hilos = new ArrayList<>(); // lista de hilos     
        pista.Ejecutar(hilos);
        // esperar a que terminen los hilos
        try {
            for (Thread hilo : hilos) {
                hilo.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("LA CARRERA HA TERMINADO");
        for (Carro carro : carros) {
            carro.finalizo(); 
        }

    }
}
