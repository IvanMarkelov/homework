import java.util.Comparator;
import java.util.Objects;

public class Person {
    public enum Sex {
        MAN,
        WOMAN
    }

    private int age;
    private Sex sex;
    private String name;

    public Person(int age, Sex sex, String name) {
        if (age < 0 || age > 100) {
            throw new IllegalArgumentException("The age is incorrect!");
        } else {
            this.age = age;
        }
        this.sex = sex;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return this.getAge() + " years old "
                + this.getName() + ", "
                + this.getSex();
    }

    public int sexComparator(Person person) {
        if (this.getSex() == Sex.MAN && person.getSex() == Sex.WOMAN) {
            return 1;
        } else if (this.getSex() == Sex.WOMAN && person.getSex() == Sex.MAN) {
            return -1;
        } else {
            return 0;
        }
    }
}
