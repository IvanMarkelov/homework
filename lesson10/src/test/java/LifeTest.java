import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LifeTest {
    private Life oneThreadLife;
    private MultithreadedLife multithreadedLife;
    private LifeIO lifeIO;

    @BeforeEach
    void setUp() {
        String path = ".\\src\\main\\resources\\input.txt";
        lifeIO = new LifeIO(path);
        oneThreadLife = new Life(lifeIO);
        multithreadedLife = new MultithreadedLife(lifeIO, 4);
    }

    @Test
    void main() {
        int xd = (39 + (-1)) % 39;
        int rd = (39 + (1)) % 39;
        int bd = (39 + (39)) % 39;
        int gd = (39 + (38)) % 39;
        System.out.println(xd);
        System.out.println(rd);
        System.out.println(bd);
        System.out.println(gd);
        Stopwatch s = Stopwatch.createStarted();
        multithreadedLife.simulateLife(4);
        s.stop();
        String multiThread = s.toString();

        System.out.println("__________");

        s.reset();
        s.start();
        oneThreadLife.simulateLife(4);
        s.stop();
        String one = s.toString();
        System.out.println("One thread speed: " + one);
        System.out.println("Several threads speed: " + multiThread);
    }

    @Test
    void simulateLife() {
    }

    @Test
    void printLifeField() {
    }

    @Test
    void calculateNextGeneration() {
    }

    @Test
    void getNextGenCell() {
    }

    @Test
    void test_lifeParser_parsingSuccessfull() {
        lifeIO.printLifeField();
    }
}