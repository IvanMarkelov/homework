import java.util.Collection;

public class BubbleSorting implements Sorting {

    public BubbleSorting() {
    }

    public Person[] sortPeople(Person[] peopleToSort) {
        boolean isSorted = false;
        while (!isSorted) {
            int swapCount = 0;
            for (int i = 0; i < peopleToSort.length-1; i++) {
                if (peopleToSort[i].getSex().hashCode()
                        > peopleToSort[i+1].getSex().hashCode()) {
                    swapPerson(peopleToSort, i);
                    swapCount++;
                } else if (peopleToSort[i].getSex().hashCode() == peopleToSort[i+1].getSex().hashCode()){
                    if (peopleToSort[i].getAge()
                            < peopleToSort[i+1].getAge()) {
                        swapPerson(peopleToSort, i);
                        swapCount++;
                    } else if (peopleToSort[i].getAge() == peopleToSort[i+1].getAge()){
                        if (peopleToSort[i].getName().compareTo(peopleToSort[i+1].getName()) > 0) {
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
        tempPerson = peopleToSort[i +1];
        peopleToSort[i +1] = peopleToSort[i];
        peopleToSort[i] = tempPerson;
    }
}
