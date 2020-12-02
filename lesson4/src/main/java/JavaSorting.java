import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class JavaSorting implements Sorting {
    @Override
    public Person[] sortPeople(Person[] listToSort) {
        Comparator<Person> comparePeople = Comparator
                .comparing(Person::getSex)
                .thenComparing(Person::getAge, Comparator.reverseOrder())
                .thenComparing(Person::getName);

        Person[] sorted = Arrays.stream(listToSort).sorted(comparePeople).toArray(Person[]::new);
        return sorted;
    }
}
