# Suma Paralela de Arreglos

Este proyecto implementa la suma de un arreglo de 1,000,000 elementos de forma secuencial y paralela.

## Compilación y Ejecución
```bash
javac ParallelSum.java
java ParallelSum
```
## Ejemplo de Salida

```bash
Suma secuencial: 5,000,357,493 (Tiempo: 0.004 s)

Resultados paralelos:
Hilos | Tiempo (s) | Speedup | Eficiencia
    1 |      0.018 |    0.21 |      0.21
    2 |      0.007 |    0.58 |      0.29
    4 |      0.002 |    1.62 |      0.40
    8 |      0.003 |    1.25 |      0.16
   16 |      0.006 |    0.63 |      0.04
   32 |      0.013 |    0.30 |      0.01
```

# Autor
```bash
@name: me@ranygermosen.com
@pucmm_id: 1013-4707
```