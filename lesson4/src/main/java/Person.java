import java.util.Objects;

public class Person {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                sex == person.sex &&
                name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name);
    }

    public enum Sex {
        MAN, WOMAN
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
}
