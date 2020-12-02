import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SortingTest {
    private PeopleData peopleData;
    private PeopleData pd;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        peopleData = new PeopleData(13);

        Person[] newArr = {new Person(32, Person.Sex.WOMAN, "Alice"),
                new Person(24, Person.Sex.MAN, "Alex"),
                new Person(16, Person.Sex.MAN, "Axel")};
        pd = new PeopleData(newArr);
    }

    @org.junit.jupiter.api.Test
    public void test() {
        peopleData = new PeopleData(10000);
        for (Person p : peopleData.getArray()) {
            System.out.println(p.toString());
        }

        System.out.println();

        Sorting sorting = new BubbleSorting();
        Sorting sorting2 = new SelectionSorting();
        Sorting sorting3 = new JavaSorting();

        long startTime = System.nanoTime();
        Person[] sortedArr = sorting.sortPeople(peopleData.getArray());
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        long startTime2 = System.nanoTime();
        Person[] sortedArr2 = sorting2.sortPeople(peopleData.getArray());
        long endTime2 = System.nanoTime();
        long duration2 = (endTime2 - startTime2);

        long startTime3 = System.nanoTime();
        Person[] sortedArr3 = sorting3.sortPeople(peopleData.getArray());
        long endTime3 = System.nanoTime();
        long duration3 = (endTime3 - startTime3);

        for (Person p : sortedArr) {
            System.out.println(p.toString());
        }
        System.out.println();

        for (Person p : sortedArr2) {
            System.out.println(p.toString());
        }
        System.out.println();

        for (Person p : sortedArr3) {
            System.out.println(p.toString());
        }

        int numberOfMistakes = 0;

        for (int i = 0; i < peopleData.getArray().length; i++) {
            if (!sortedArr[i].equals(sortedArr2[i]) || !sortedArr2[i].equals(sortedArr3[i])) {
                numberOfMistakes++;
            }
        }

        System.out.println("Number of differences in sorted values " +
                "between three arrays, sorted by 3 different methods: " + numberOfMistakes);

        System.out.println("Bubble sorting of 10000 people took " + (duration/1000000) + " milliseconds.");
        System.out.println("Selection sorting of 10000 people took " + (duration2/1000000) + " milliseconds.");
        System.out.println("Java stream sorting of 10000 people took " + (duration3/1000000) + " milliseconds.");
    }

    @org.junit.jupiter.api.Test
    public void generatePeople() {
        PeopleData pd = new PeopleData(5);
        assertEquals(5, pd.getArray().length);
    }

    @org.junit.jupiter.api.Test
    public void add() {
        PeopleData pd = new PeopleData(2);
        assertEquals(2, pd.getArray().length);
        pd.addPerson(new Person(32, Person.Sex.MAN, "Jack"));
        assertEquals(3, pd.getArray().length);
        assertEquals("Jack", pd.getArray()[2].getName());
    }

    @org.junit.jupiter.api.Test
    public void testBubbleSorting() {
        Sorting bubbleSorting = new BubbleSorting();
        Person[] sorted = bubbleSorting.sortPeople(pd.getArray());
        assertEquals("Alex", sorted[0].getName());
    }

    @org.junit.jupiter.api.Test
    public void testSelectionSorting() {
        Sorting selectionSorting = new SelectionSorting();
        Person[] sorted = selectionSorting.sortPeople(pd.getArray());
        assertEquals("Alex", sorted[0].getName());
    }

    @org.junit.jupiter.api.Test
    public void testJavaSorting() {
        Sorting javaSorting = new JavaSorting();
        Person[] sorted = javaSorting.sortPeople(pd.getArray());
        assertEquals("Alex", sorted[0].getName());
    }

    @org.junit.jupiter.api.Test
    public void add_throwsExceptionWhenPersonAlreadyExists() {
        PeopleData pd = new PeopleData(2);
        pd.addPerson(new Person(32, Person.Sex.MAN, "Jack"));
        assertThrows(IllegalArgumentException.class, () ->
                pd.addPerson(new Person(32, Person.Sex.MAN, "Jack")));
    }

    @org.junit.jupiter.api.Test
    public void createPerson_throwsExceptionWhenAgeIsIncorrect() {
        assertThrows(IllegalArgumentException.class, () ->
                new Person(-5, Person.Sex.MAN, "Jack"));
    }

    @org.junit.jupiter.api.Test
    public void createPeopleData_throwsExceptionWhenNumberOfPeopleIsIncorrect() {
        assertThrows(IllegalArgumentException.class, () ->
                new PeopleData(0));
    }
}