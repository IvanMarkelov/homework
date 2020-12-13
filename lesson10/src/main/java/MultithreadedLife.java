import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultithreadedLife implements ILife {
    public static boolean[][] field;
    public int numberOfThreads;

    public MultithreadedLife(boolean[][] fieldToSimulate, int numberOfThreads) {
        field = fieldToSimulate;
        this.numberOfThreads = numberOfThreads;
    }

    public boolean[][] simulateLife(int numberOfCycles) {
        if (numberOfCycles < 0) {
            throw new IllegalArgumentException("Number of cycles cannot be less than 0!");
        }
        for (int i = 0; i < numberOfCycles; i++) {
            boolean[][] tempField = new boolean[field.length][field[1].length];
            for (int j = 0; j < field.length; j++) {
                System.arraycopy(field[j], 0, tempField[j], 0, field[1].length);
            }

            ExecutorService executorService = Executors.newFixedThreadPool(this.numberOfThreads);
            Future<?>[] futures = new Future[this.numberOfThreads];

            for (int threadIndex = 0; threadIndex < this.numberOfThreads; threadIndex++) {
                int start = threadIndex;
                Future<?> future = executorService.submit(() ->
                        calculateNextGeneration(start, tempField));
                futures[threadIndex] = future;
            }

            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
/*                int start = threadIndex;
                threads[start] = new Thread(() -> {
                    calculateNextGeneration(start, threadsCount, (boolean[][]) tempField);
                });
            }
            Arrays.stream(threads).forEach(Thread::start);
            Arrays.stream(threads).forEach(t -> {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });*/
        }
        return field;
    }

    //add threads here
    public void calculateNextGeneration(int start, boolean[][] tempField) {
        for (int row = start; row < tempField.length; row += this.numberOfThreads) {
            for (int col = 0; col < tempField[1].length; col++) {
                field[row][col] = getNextGenCell(row, col, tempField);
            }
        }
    }
}