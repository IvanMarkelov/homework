import java.util.Collection;

public class SelectionSorting implements Sorting {

    public Person[] sortPeople(Person[] peopleToSort) {

        for (int i = 0; i < peopleToSort.length; i++) {
            Person minimal = peopleToSort[i];
            int indexOfMin = 0;
            for (int j = i + 1; j < peopleToSort.length; j++) {
                if (minimal.getSex().hashCode() > peopleToSort[j].getSex().hashCode()) {
                    minimal = peopleToSort[j];
                    indexOfMin = j;
                } else if (minimal.getSex().hashCode() == peopleToSort[j].getSex().hashCode()) {
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
