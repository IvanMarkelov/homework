import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

class MyHashMapTest {

    private MyHashMap<String, String> myHashMap;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        this.myHashMap = new MyHashMap<>();
        this.myHashMap.put("Woah", "It works.");
        this.myHashMap.put("Doah", "It still works.");
    }

    @org.junit.jupiter.api.Test
    void size() {
        assertEquals(2, myHashMap.size());
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        assertFalse(myHashMap.isEmpty());
        myHashMap.clear();
        assertTrue(myHashMap.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void containsKey() {
        assertTrue(myHashMap.containsKey("Doah"));
        assertTrue(myHashMap.containsKey("Woah"));
    }

    @org.junit.jupiter.api.Test
    void containsValue() {
        assertTrue(myHashMap.containsValue("It works."));
        assertTrue(myHashMap.containsValue(new String("It still works.")));
    }

    @org.junit.jupiter.api.Test
    void get() {
        String myNodeValue = myHashMap.get("Woah");
        assertEquals("It works.", myNodeValue);
    }

    @org.junit.jupiter.api.Test
    void put() {
        myHashMap.put("Red", "Road");
        assertTrue(myHashMap.containsKey("Red"));
        assertEquals(3, myHashMap.size());
    }

    @org.junit.jupiter.api.Test
    void remove() {
        myHashMap.remove("Woah");
        assertFalse(myHashMap.containsKey("Woah"));
    }

    @org.junit.jupiter.api.Test
    void putAll() {
        MyHashMap<String, String> newHashMap = new MyHashMap<>();
        newHashMap.put("Woah", "Nope.");
        newHashMap.put("Broah", "Something.");
        myHashMap.putAll(newHashMap);
        assertEquals(3, myHashMap.size());
        String myNodeValue = myHashMap.get("Woah");
        assertEquals("Nope.", myNodeValue);
    }

    @org.junit.jupiter.api.Test
    void clear() {
        myHashMap.clear();
        assertEquals(0, myHashMap.size());
    }

    @org.junit.jupiter.api.Test
    void keySet() {
        Set<String> newSet = myHashMap.keySet();
        assertEquals(2, newSet.size());
    }

    @org.junit.jupiter.api.Test
    void values() {
        Collection<String> values = myHashMap.values();
        assertEquals(2, values.size());
    }

    @org.junit.jupiter.api.Test
    void entrySet() {
        Set<Map.Entry<String, String>> newSet = myHashMap.entrySet();
        assertEquals(2, newSet.size());
    }

    @org.junit.jupiter.api.Test
    void replaceMyNode() {
        myHashMap.replace("Woah", "NewDay");
        String myNodeValue = myHashMap.get("Woah");
        assertEquals("NewDay", myNodeValue);
    }

    @org.junit.jupiter.api.Test
    void increaseCapacity() {
        myHashMap.increaseCapacity();
        assertEquals(32, myHashMap.getCapacity());
        String myNodeValue = myHashMap.get("Woah");
        assertEquals("It works.", myNodeValue);
    }

    @org.junit.jupiter.api.Test
    void put_WhenKeyAlreadyExist() {
        myHashMap.put("Woah", "Road");
        assertEquals("Road", myHashMap.get("Woah"));
    }

    @org.junit.jupiter.api.Test
    void put_WhenKeyIsNull() {
        MyHashMap<String, String> test = new MyHashMap<>();
        String zer = "zer";
        test.put(null, zer);
        assertEquals(zer, test.get(null));
    }

    @org.junit.jupiter.api.Test
    void replaceMyNode_throwsExceptionIfKeyIsNotFoundOrNull() {
        assertThrows(NullPointerException.class, () ->
                myHashMap.replace("Boar", "Road"));
        assertThrows(NullPointerException.class, () ->
                myHashMap.replace(null, "Road"));
    }

    @org.junit.jupiter.api.Test
    void remove_throwsExceptionIfKeyIsNull() {
        assertThrows(NullPointerException.class, () ->
                myHashMap.replace(null, "Road"));
    }

    @org.junit.jupiter.api.Test
    void containsValue_newStringInstance() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "TEST");

        assertTrue(map.containsValue(new String("TEST")));
        assertTrue(myHashMap.containsValue(new String("It works.")));
    }

    @org.junit.jupiter.api.Test
    void get_newStringInstance() {
        String myNodeValue = myHashMap.get(new String("Woah"));

        assertEquals("It works.", myNodeValue);
    }
}