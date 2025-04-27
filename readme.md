## Ejecucion
Descargar este archivo **no necesario tener la carpeta ejer4**
- Compilar `javac *.java`
- Ejecutar `java Main`


## Estructura
### Carro.java: 
- Es un hilo independiente 
- Ejecuta cada *X* milisegundos determinado por la **Pista**
- Se inicia una interaccion termina cuanto el tiempo llega a los establecido por **pista**, llego a la meta o se acabo las primeras posiciones
- En cada interacion ejecuta
1. ***velocidad = max(velocidad + min(aceleracion ,gasolina),0)***: Se encarga de aumentar la velocidad, que no puede haber velocidad negativa 
2. ***distancia = max(distancia + velocidad,0)***: Se encarga de aumentar la distancia y que no pueda haber distancia negativa ademas si la distancia es mayor o igual a la **meta** establecida en **Pista** invocara **ganeCarrera** si hay puestos disponibles entrara en caso contrario no
3. ***gasolina = max(gasolina - aceleracion,0)***: Se encarga de consumir la gasolina proporcionalmente a la aceleracion y este tampoco puede ser negativo
4. Si la **velocidad** es 0: El carro se detuvo por falta de gasolina
5. Si la  **velocidad** es superior a la velocidad maxima dada por **Pista**: El carro explotara y se quedara ahi
- Al terminar se cierra el hilo

### Eventos.java:
- Es un hilo independiente 
- Ejecuta cada *X* milisegundos determinado por la **Pista**
- El evento inicializada con probabilidades
- Se inicia una interaccion termina cuanto el tiempo llega a los establecido por **pista** o se acabo las primeras posiciones
- En cada interacion ejecuta
1. Segun las probabilidades se elige: Ventajas, desventajas y no hacer nada (se vera en la sesion de ventajas y desventajas)


### Main.java
- Es el programa principal al ejecutar 
- Espera los hilos de carros y eventos

### Pista.java
- Tiene variables importantes que seran usadas tanto por eventos y por carros
- Se encarga de crear la cantidad de eventos 
- Se encarga de ejecutar los carros y eventos 

## Ventajas y desventajas
Aun no hay programado
### ventajas
### Desventajas

