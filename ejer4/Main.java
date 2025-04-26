import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numeroPlazas, numeroCoches;

        while (true) {
            try {
                System.out.println("Introduce el numero de plazas");
                numeroPlazas = Integer.parseInt(sc.nextLine());
                System.out.println("Introduce el numero de coches");
                numeroCoches = Integer.parseInt(sc.nextLine());
                new ParkingGestor(numeroPlazas, numeroCoches);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Introduce un numero");
            }   
        }
    }
}
