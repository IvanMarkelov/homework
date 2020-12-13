import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LifeTest {
    private Life oneThreadLife;
    private MultithreadedLife multithreadedLife;
    private LifeIO lifeIO;
    private boolean[][] lifeField;
    private int numOfCycles;
    private int numberOfThreads;
    private String smallFieldPath;

    @BeforeEach
    void setUp() {
        numOfCycles = 1;
        numberOfThreads = 4;

        smallFieldPath = ".\\src\\main\\resources\\input.txt";
        lifeIO = new LifeIO();
        lifeField = lifeIO.lifeReader(smallFieldPath);

        oneThreadLife = new Life(lifeField);
        multithreadedLife = new MultithreadedLife(lifeField, numberOfThreads);
    }

    @Test
    void timeTest() {
        String path = ".\\src\\main\\resources\\input-large.txt";
        boolean[][] largeField = lifeIO.lifeReader(path);
        oneThreadLife = new Life(largeField);
        multithreadedLife = new MultithreadedLife(largeField, numberOfThreads);

        Stopwatch s = Stopwatch.createStarted();
        oneThreadLife.simulateLife(numOfCycles);
        s.stop();
        String oneThreadSpeed = s.toString();
        s.reset();

        s.start();
        multithreadedLife.simulateLife(numOfCycles);
        s.stop();
        String multiThreadSpeed = s.toString();

        System.out.println("Speed of one thread: " + oneThreadSpeed);
        System.out.println("Speed of " + numberOfThreads + " multi threads: " + multiThreadSpeed);
    }

    @Test
    void simulateLife() {
        lifeIO.lifeConsolePrinter(oneThreadLife.field);
        boolean[][] field = oneThreadLife.simulateLife(4);
        lifeIO.lifeConsolePrinter(field);

        assertTrue(field[4][1]);
        assertTrue(field[5][2]);
        assertTrue(field[5][3]);
        assertTrue(field[4][3]);
        assertTrue(field[3][3]);
        assertFalse(field[4][2]);

        for (int i = 0; i < 20; i++) {
            boolean[][] fieldOne = oneThreadLife.simulateLife(i);
            boolean[][] fieldMulti = multithreadedLife.simulateLife(i);

            assertTrue(lifeIO.compareFields(fieldOne, fieldMulti));
        }
    }

    @Test
    void calculateNextGeneration() {
        boolean[][] newGenerationField = oneThreadLife.calculateNextGeneration(lifeField);

        assertTrue(newGenerationField[4][1]);
        assertTrue(newGenerationField[4][2]);
        assertTrue(newGenerationField[2][1]);
        assertTrue(newGenerationField[3][3]);
        assertTrue(newGenerationField[3][2]);
        assertFalse(newGenerationField[3][0]);
    }

    @Test
    void randomFieldGenerator_writesTheFile() {
        boolean[][] bigField = lifeIO.lifeFieldGenerator(10, 12);
        String outputPath = ".\\src\\main\\resources\\output-generated.txt";
        lifeIO.lifeWriter(bigField, outputPath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(outputPath));
            String line;
            int numOfRows = 0;
            int numOfColumns = 0;
            while ((line = br.readLine()) != null) {
                if (numOfRows == 0) {
                    numOfColumns = line.split(", ").length;
                }
                numOfRows++;
            }
            br.close();

            assertEquals(10, numOfRows);
            assertEquals(12, numOfColumns);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test_lifeReader_readingSuccessful() {
        boolean[][] read = lifeIO.lifeReader(smallFieldPath);

        assertTrue(read[3][0]);
        assertTrue(read[4][1]);
        assertTrue(read[4][2]);
        assertTrue(read[3][2]);
        assertTrue(read[2][2]);
        assertFalse(read[3][1]);
    }
}