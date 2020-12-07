import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

enum En {
    One, Two
}

class MockClass {
    public String name;
    public int number;
    public float fl;
    public boolean cond;
    public En en;
    public Object object;

    public MockClass(String name, int number, float fl, boolean cond, En en, Object object) {
        this.name = name;
        this.number = number;
        this.fl = fl;
        this.cond = cond;
        this.en = en;
        this.object = object;
    }
}

class MainTest {
    public MockClass testClass;
    public HashMap<String, Integer> hashMap;
    public Set<String> toCleanUp;
    Set<String> toPrint;

    @org.junit.jupiter.api.BeforeEach
    void startup() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("String");
        arr.add("Test");
        testClass = new MockClass("Test", 250, 35, true, En.Two, arr);

        hashMap = new HashMap<>();
        hashMap.put("name", 21);
        hashMap.put("number", 22);
        hashMap.put("fl", 23);
        hashMap.put("cond", 24);
        hashMap.put("en", 25);
        hashMap.put("object", 26);

        toCleanUp = new TreeSet<>();
        toCleanUp.add("name");
        toCleanUp.add("fl");
        toCleanUp.add("cond");
        toCleanUp.add("object");
        toCleanUp.add("en");

        toPrint = new TreeSet<>();
        toPrint.add("number");
    }

    @org.junit.jupiter.api.Test
    void cleanup_object() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Main.cleanup(testClass, toCleanUp, toPrint);

        assertEquals(null, testClass.name);
        assertEquals(250, testClass.number);
        assertEquals(0, testClass.fl);
    }

    @org.junit.jupiter.api.Test
    void cleanup_map() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Main.cleanup(hashMap, toCleanUp, toPrint);

        assertFalse(hashMap.containsKey("name"));
        assertTrue(hashMap.containsKey("number"));
    }

    @org.junit.jupiter.api.Test
    void cleanup_object_throwsExceptionIfFieldToCleanUpIsIncorrect() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        toCleanUp = new TreeSet<>();
        toCleanUp.add("name");
        toCleanUp.add("fl");
        toCleanUp.add("don't have this field/key to remove");

        assertEquals("Test", testClass.name);
        assertThrows(IllegalArgumentException.class, () ->
                Main.cleanup(testClass, toCleanUp, toPrint));
    }

    @org.junit.jupiter.api.Test
    void cleanup_object_throwsExceptionIfFieldToPrintIsIncorrect() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        toCleanUp = new TreeSet<>();
        toPrint = new TreeSet<>();
        toPrint.add("cond");
        toPrint.add("number");
        toPrint.add("don't have this field/key to print");

        assertEquals("Test", testClass.name);
        assertThrows(IllegalArgumentException.class, () ->
                Main.cleanup(testClass, toCleanUp, toPrint));
    }

    @org.junit.jupiter.api.Test
    void cleanup_map_throwsExceptionIfFieldToCleanUpIsIncorrect() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        toCleanUp = new TreeSet<>();
        toCleanUp.add("name");
        toCleanUp.add("fl");
        toCleanUp.add("don't have this field/key to remove");
        toPrint = new TreeSet<>();

        assertTrue(hashMap.containsKey("name"));
        assertTrue(hashMap.containsKey("number"));
        assertThrows(IllegalArgumentException.class, () ->
                Main.cleanup(hashMap, toCleanUp, toPrint));
    }

    @org.junit.jupiter.api.Test
    void cleanup_map_throwsExceptionIfFieldToPrintIsIncorrect() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        toPrint = new TreeSet<>();
        toPrint.add("cond");
        toPrint.add("number");
        toPrint.add("don't have this field/key to print");
        toCleanUp = new TreeSet<>();

        assertTrue(hashMap.containsKey("name"));
        assertTrue(hashMap.containsKey("number"));
        assertThrows(IllegalArgumentException.class, () ->
                Main.cleanup(hashMap, toCleanUp, toPrint));
    }
}