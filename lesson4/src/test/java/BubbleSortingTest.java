import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortingTest {
    private PeopleData peopleData;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        peopleData = new PeopleData(3);
    }

    @org.junit.jupiter.api.Test
    public void test() {
        Random rd = new Random();
        for (Person p:peopleData.getArray()) {
            System.out.println(p.toString());
        }

        System.out.println();

        Sorting sorting = new BubbleSorting();
        Sorting sorting2 = new SelectionSorting();
        Person[] sortedArr = sorting.sortPeople(peopleData.getArray());
        Person[] sortedArr2 = sorting2.sortPeople(peopleData.getArray());

        for (Person p:sortedArr) {
            System.out.println(p.toString());
        }

        System.out.println();

        for (Person p:sortedArr2) {
            System.out.println(p.toString());
        }
    }
}