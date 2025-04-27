import java.util.ArrayList;
public class Main {
    public static void main(String[] args) { 
        Pista pista = new Pista(500,-1,30,100); 
        // lista de carros
        ArrayList<Carro> carros = new ArrayList<>();
        carros.add(new Carro("Thunderbolt", 3, 4, 20, pista)); 
        carros.add(new Carro("Speedster", 2, 5, 18, pista)); 
        carros.add(new Carro("Vortex", 4, 3, 22, pista)); 
        carros.add(new Carro("NitroX", 3, 5, 17, pista));
        carros.add(new Carro("Falcon", 3, 4, 19, pista)); 

        pista.programarCarrera(300, carros,1);
        
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
        pista.ImprimirCarrera();
        pista.imprimirPosiciones();
    }
}
