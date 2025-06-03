import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ParallelSum {
    private static final int SIZE = 1_000_000;
    private static final int MIN = 1;
    private static final int MAX = 10_000;
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        int[] array = generateArray();

        long startTime = System.nanoTime();
        long sequentialSum = sequentialSum(array);
        long sequentialTime = System.nanoTime() - startTime;

        System.out.printf("Suma secuencial: %,d (Tiempo: %.3f s)%n",
                sequentialSum, sequentialTime / 1e9);

        int[] threadCounts = { 1, 2, 4, 8, 16, 32 };
        System.out.println("\nResultados paralelos:");
        System.out.println("Hilos | Tiempo (s) | Speedup | Eficiencia");

        for (int threads : threadCounts) {
            startTime = System.nanoTime();
            long parallelSum = parallelSum(array, threads);
            long parallelTime = System.nanoTime() - startTime;

            double speedup = (double) sequentialTime / parallelTime;
            double efficiency = speedup / threads;

            System.out.printf("%5d | %10.3f | %7.2f | %9.2f%n",
                    threads, parallelTime / 1e9, speedup, efficiency);

            if (parallelSum != sequentialSum) {
                System.err.println("Error: La suma paralela no coincide con la secuencial");
            }
        }
    }

    private static int[] generateArray() {
        int[] array = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            array[i] = MIN + random.nextInt(MAX - MIN + 1);
        }
        return array;
    }

    private static long sequentialSum(int[] array) {
        long sum = 0;
        for (int num : array) {
            sum += num;
        }
        return sum;
    }

    private static long parallelSum(int[] array, int numThreads) throws InterruptedException {
        AtomicLong totalSum = new AtomicLong(0);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        int chunkSize = array.length / numThreads;

        for (int i = 0; i < numThreads; i++) {
            final int start = i * chunkSize;
            final int end = (i == numThreads - 1) ? array.length : (i + 1) * chunkSize;

            executor.submit(() -> {
                long localSum = 0;
                for (int j = start; j < end; j++) {
                    localSum += array[j];
                }
                totalSum.addAndGet(localSum);
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        return totalSum.get();
    }
}