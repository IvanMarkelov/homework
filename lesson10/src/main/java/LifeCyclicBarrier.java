import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;

//TODO
public class LifeCyclicBarrier implements ILife {
    public LifeIO lifeIO;
    public static boolean[][] field;
    public int numberOfThreads;

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public LifeCyclicBarrier(LifeIO lifeIO, int numberOfThreads) {
        this.lifeIO = lifeIO;
        field = lifeIO.dataParsed;
        this.numberOfThreads = numberOfThreads;
    }

    public boolean[][] simulateLife(int numberOfCycles) {
        if (numberOfCycles < 0) {
            throw new IllegalArgumentException("Number of cycles cannot be less than 0!");
        }

        CyclicBarrier cyclicBarrier = new CyclicBarrier(numberOfThreads);

        for (int i = 0; i < numberOfCycles; i++) {
            boolean[][] tempField = new boolean[field.length][field[1].length];
            for (int j = 0; j < field.length; j++) {
                System.arraycopy(field[j], 0, tempField[j], 0, field[1].length);
            }

            Thread[] threads = new Thread[this.numberOfThreads];

            for (int threadIndex = 0; threadIndex < this.numberOfThreads; threadIndex++) {
                int start = threadIndex;
                threads[start] = new Thread(() -> {
                    calculateNextGeneration(start, (boolean[][]) tempField);
                });
            }

            Arrays.stream(threads).forEach(Thread::start);
            Arrays.stream(threads).forEach(t -> {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
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
