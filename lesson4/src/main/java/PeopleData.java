import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class PeopleData {
    private Person[] arr;
    private Random rd;

    public PeopleData(int numberOfPeople) {
        this.rd = new Random();
        if (numberOfPeople > 0) {
            this.arr = generatePeople(numberOfPeople);
        } else {
            throw new IllegalArgumentException("The array has to have more than one person.");
        }
    }

    public PeopleData(Person[] listOfPeople) {
        this.rd = new Random();
        if (listOfPeople.length > 0) {
            this.arr = listOfPeople;
        } else {
            throw new IllegalArgumentException("The array has to have more than one person.");
        }
    }

    public Person[] getArray() {
        return arr;
    }

    public void setArr(Person[] newArr) {
        this.arr = newArr;
    }

    public boolean containsPerson(Person personToCheck) {
        for (Person p : this.arr) {
            if (personToCheck.getName() == p.getName()
                    && personToCheck.getAge() == p.getAge()) {
                return true;
            }
        }
        return false;
    }

    public void addPerson(Person person) {
        if (!containsPerson(person)) {
            Person[] copy = Arrays.copyOf(arr, arr.length + 1);
            copy[arr.length] = person;
            this.arr = copy;
        } else {
            throw new IllegalArgumentException("This person already exists in the list!");
        }

    }

    private Person[] generatePeople(int numberOfPeople) {
        Person[] arr = new Person[numberOfPeople];
        for (int i = 0; i < numberOfPeople; i++) {
            Person p = new Person(generateRandomAge(),
                    generateRandomSex(),
                    generateRandomName());
            arr[i] = p;
        }
        return arr;
    }

    private Person.Sex generateRandomSex() {
        return rd.nextBoolean() ? Person.Sex.MAN : Person.Sex.WOMAN;
    }

    private int generateRandomAge() {
        return rd.nextInt(101);
    }

    private String generateRandomName() {
        int leftLimit = 97;
        int rightLimit = 122;
        StringBuilder sb = new StringBuilder();
        int nameLength = rd.nextInt(35) + 10;
        int spaceIndex = rd.nextInt(nameLength - 5) + 2;
        for (int i = 0; i < nameLength; i++) {
            if (i != spaceIndex) {
                int randomLimitedInt =
                        leftLimit + (int) (rd.nextFloat() * (rightLimit - leftLimit + 1));
                sb.append((char) randomLimitedInt);
            } else {
                sb.append(' ');
            }
        }
        return sb.toString();
    }
}
