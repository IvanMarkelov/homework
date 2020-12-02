import java.util.Collection;

public class SelectionSorting implements Sorting {

    public Person[] sortPeople(Person[] peopleToSort) {
        if (peopleToSort.length < 2) {
            return peopleToSort;
        }

        for (int i = 0; i < peopleToSort.length; i++) {
            Person minimal = peopleToSort[i];
            int indexOfMin = i;
            for (int j = i + 1; j < peopleToSort.length; j++) {
                if (minimal.sexComparator(peopleToSort[j]) < 0) {
                    minimal = peopleToSort[j];
                    indexOfMin = j;
                } else if (minimal.sexComparator(peopleToSort[j]) == 0) {
                    if (minimal.getAge() < peopleToSort[j].getAge()) {
                        minimal = peopleToSort[j];
                        indexOfMin = j;
                    } else if (minimal.getAge() == peopleToSort[j].getAge()) {
                        if (minimal.getName().compareTo(peopleToSort[j].getName()) > 0) {
                            minimal = peopleToSort[j];
                            indexOfMin = j;
                        }
                    }
                }
            }
            if (i == indexOfMin) {
                break;
            }
            peopleToSort[indexOfMin] = peopleToSort[i];
            peopleToSort[i] = minimal;
        }
        return peopleToSort;
    }
}
