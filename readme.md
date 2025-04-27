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
1. Segun las probabilidades se elige: Ventajas, desventajas y no hacer nada (se vera en la sesion de ventajas y desventajas) que viene de **pista**
2. Ejecuta segun segun lo que toco


### Main.java
- Es el programa principal al ejecutar 
- Espera los hilos de carros y eventos

### Pista.java
- Tiene variables importantes que seran usadas tanto por eventos y por carros
- Se encarga de crear la cantidad de eventos 
- Se encarga de ejecutar los carros y eventos 
- Se crea ventajas y desventajas

## Ventajas y desventajas
Aun no hay programado
### Desventajas
1. **Rebote** = `[-15, -7]` m de distancia  
2. **Retroceso** = `[-25, -10]` m de distancia  
3. **Deslizamiento** = `[-10, -3]` m de distancia  
4. **Bache** = `[-3, -1]` m/s de velocidad  
5. **Obstaculo** = `[-5, 0]` m/s de velocidad  
6. **Viento(-)** = `[-4, -1]` m/s de velocidad  
7. **MotorFallido** = `[-2, -1]` m/s² de aceleración  
8. **Fuga** = `[-4, -1]` L de gasolina  
9. **GolpeMortal** = `[-3, 0]` L de gasolina y `[-2, 0]` m/s² de aceleración  
10. **SuperViento(-)** = `[-10, 0]` m de distancia y `[-4, 0]` m/s de velocidad  
11. **GolpeTotal** = `[-5, 0]` L de gasolina, `[-2, 0]` m/s² de aceleración, `[-4, -1]` m/s de velocidad  


### Ventajas
1. **Atajo** = `[+8, +15]` m de distancia  
2. **Impulso** = `[+5, +12]` m de distancia  
3. **SuperAtajo** = `[+12, +25]` m de distancia  
4. **Turbo** = `[+1, +3]` m/s de velocidad  
5. **Viento(+)** = `[+1, +4]` m/s de velocidad  
6. **PistaLisa** = `[+2, +5]` m/s de velocidad  
7. **MotorMejorado** = `[+1, +2]` m/s² de aceleración  
8. **Recarga** = `[+4, +8]` L de gasolina  
9. **SuperMejora** = `[+3, +5]` L de gasolina y `[+2, +6]` m/s de velocidad  
10. **SuperViento(+)** = `[+5, +12]` m de distancia y `[+2, +4]` m/s de velocidad  
11. **MejoraDefinitiva** = `[+5, +10]` L de gasolina, `[+2, +8]` m/s de velocidad, `[+1, +2]` m/s² de aceleración  

### Carros 
1. **Flash**, a = 2, v = 0, gasolina = 18  
2. **Bolt**, a = 3, v = 0, gasolina = 16  
3. **Steady**, a = 1, v = 1, gasolina = 22  
4. **Cruiser**, a = 2, v = 1, gasolina = 20  
5. **Rocket**, a = 3, v = 2, gasolina = 15  
6. **Phantom**, a = 4, v = 0, gasolina = 14  
7. **Titan**, a = 1, v = 2, gasolina = 25  
8. **Vortex**, a = 3, v = 1, gasolina = 17  
9. **Ranger**, a = 2, v = 0, gasolina = 19  
10. **Blazer**, a = 4, v = 1, gasolina = 13 

### Carreras
1. <tipo>, c = <cantidad de eventos>, [] = <rango de probabilidad negativo>, [] = <rango de probabilidad positiva>, [] = <rango de probabilidad de no hacer nada>, meta = <distancia para ganar la carrera>, t = <tiempo maximo de carrera>, d = <en caso de que no tenga combustible la velocida de desaceleracion>, maxVel = <maxVelocidad antes que explote el carro>