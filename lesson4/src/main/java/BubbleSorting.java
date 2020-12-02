import java.util.Collection;

public class BubbleSorting implements Sorting {

    public BubbleSorting() {
    }

    public Person[] sortPeople(Person[] peopleToSort) {
        boolean isSorted = peopleToSort.length < 2;
        while (!isSorted) {
            int swapCount = 0;
            for (int i = 0; i < peopleToSort.length - 1; i++) {
                if (peopleToSort[i].sexComparator(peopleToSort[i + 1]) < 0) {
                    swapPerson(peopleToSort, i);
                    swapCount++;
                } else if (peopleToSort[i].sexComparator(peopleToSort[i + 1]) == 0) {
                    if (peopleToSort[i].getAge()
                            < peopleToSort[i + 1].getAge()) {
                        swapPerson(peopleToSort, i);
                        swapCount++;
                    } else if (peopleToSort[i].getAge() == peopleToSort[i + 1].getAge()) {
                        if (peopleToSort[i].getName().compareTo(peopleToSort[i + 1].getName()) > 0) {
                            swapPerson(peopleToSort, i);
                            swapCount++;
                        }
                    }
                }
            }
            if (swapCount == 0) {
                isSorted = true;
            }
        }
        return peopleToSort;
    }

    private void swapPerson(Person[] peopleToSort, int i) {
        Person tempPerson;
        tempPerson = peopleToSort[i + 1];
        peopleToSort[i + 1] = peopleToSort[i];
        peopleToSort[i] = tempPerson;
    }
}
