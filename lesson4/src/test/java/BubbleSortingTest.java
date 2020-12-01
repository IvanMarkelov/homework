import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortingTest {
    private PeopleData peopleData;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        peopleData = new PeopleData(12);
    }

    @org.junit.jupiter.api.Test
    public void test() {
        Random rd = new Random();
        for (Person p:peopleData.getArray()) {
            System.out.println(p.toString());
        }
    }
}