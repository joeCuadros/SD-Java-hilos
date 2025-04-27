public class Carro extends Thread {
    public final static int DEPURABLE_PREDETERMINADO = 1;
    public final Pista PISTA;
    private final String nombre;
    private int aceleracion;
    private int velocidad;
    private int distancia;
    private int tiempo;
    private int gasolina;
    private byte depurable;
    public volatile String estadoFinalizacion = "normal"; 
    /*
     * 1 = mostrar todo
     * 2 = mostrar distancia y gasolina
     * 3 = solo gasolina
     * 4 = solo distancia
     * ? = no mostrar nada
     */

    public Carro(final String nombre, int aceleracion, int velocidad, int gasolina, Pista pista) {
        // variables del carro
        this.PISTA = pista;
        // atributos de la pista
        this.nombre = nombre;
        this.aceleracion = aceleracion;
        this.velocidad = velocidad;
        this.gasolina = gasolina;
        this.distancia = 0;
        this.tiempo = 0;
        this.depurable = DEPURABLE_PREDETERMINADO; //depurable
    }
    public void setDepurable(byte depurable) {
        this.depurable = depurable;
    }
    
    public int getDistancia() {
        return this.distancia;
    }

    public String getNombre() {
        return this.nombre;
    }

    private String getEstado(){
        // si la gasolina es 0
        String Gasolina = String.valueOf(this.gasolina);
        if (this.gasolina == 0){
            modificarAceleracion(null,PISTA.DESACELERACION);
            Gasolina = "X";
        } else if (this.gasolina > 0 && this.aceleracion <= 0){
            modificarAceleracion(null,1);
        }
        // imprimir el estado del carro
        if (depurable == 1) {
            return "("+this.nombre +")["+Gasolina+"], a = " + this.aceleracion + 
                " m/s^2, v = " + this.velocidad + " m/s, distancia: " + this.distancia+" m";
        }else if (depurable == 2) {
            return "("+this.nombre +")["+Gasolina+"], distancia: " + this.distancia+" m";
        }else if (depurable == 3) {
            return "("+this.nombre +")["+Gasolina+"] se mueve";
        }else if (depurable == 4) {
            return "("+this.nombre +"), distancia: "+this.distancia+" m";
        }
        return "("+this.nombre +") se mueve";
    }

    public void terminado(){
        System.out.println("> El Carro '" + this.nombre + 
            "' ha terminado en " + this.tiempo + " segundos y ha recorrido " + 
            this.distancia + " m {"+this.estadoFinalizacion+"}" );
    }
    
    public void terminadoPuesto(){
        System.out.println("("+this.estadoFinalizacion+"), el Carro '"+ this.nombre 
            + "', "+ this.tiempo + " seg, ha recorrido "+ this.distancia + " m, a = " 
            + this.aceleracion + " m/s^2, v = " + this.velocidad 
            + " m/s, gasolina = "+ this.gasolina + " L" );

    }
    
    // procesos potegido contra condiciones de carrera
    public synchronized void modificarVelocidad(int cantidad) {
        this.velocidad = Math.max(this.velocidad + cantidad, 0);
        // modificacion de la velocidad
        if (this.velocidad == 0 && this.gasolina == 0){
            System.out.println("> ("+this.nombre +") se detuvo por falta de gasolina");
            this.estadoFinalizacion = "Falta de gasolina";
        }else if (this.velocidad > PISTA.MAX_VELOCIDAD){
            System.out.println("> ("+this.nombre +") se destruyo por exceso de velocidad");
            this.estadoFinalizacion = "Exceso de velocidad";
        }
    }

    public synchronized void modificarDistancia(int cantidad) {
        this.distancia = Math.max(this.distancia + cantidad, 0);
        if (this.distancia >= this.PISTA.META){
            this.PISTA.ganeCarrera(this);
        }
    }

    public synchronized void modificarGasolina(int cantidad) {
        this.gasolina = Math.max(this.gasolina + cantidad, 0);
    }

    public synchronized void modificarAceleracion(Integer cantidad,Integer colocado) {
        if (colocado != null){
            this.aceleracion = colocado;
        }else if (cantidad != null){
            this.aceleracion = this.aceleracion + cantidad;
        }
    }

    // ejecutar el carro
    @Override
    public void run() {
        try {
            while (this.tiempo < PISTA.MAX_TIEMPO && this.PISTA.carreraTerminada == false && this.estadoFinalizacion.equals("normal")) {
                Thread.sleep(PISTA.VELOCIDAD);
                this.tiempo++;
                // ejecutar el proceso de la pista
                modificarVelocidad(Math.min(this.aceleracion,this.gasolina));
                modificarDistancia(this.velocidad);
                modificarGasolina(- Math.max(this.aceleracion,0));               
                System.out.println(this.getEstado());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
