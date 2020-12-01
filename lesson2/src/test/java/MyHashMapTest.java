import org.junit.*;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import java.util.*;

class MyHashMapTest {

    private MyHashMap myHashMap;

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
      //  myHashMap.remove("Woah");
       // assertFalse(myHashMap.containsKey("Woah"));
    }

    @org.junit.jupiter.api.Test
    void containsValue() {
        assertTrue(myHashMap.containsValue("It works."));
    }

    @org.junit.jupiter.api.Test
    void get() {
        String myNodeValue = (String) myHashMap.get("Woah");
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
        String myNodeValue = (String) myHashMap.get("Woah");
        assertEquals("Nope.", myNodeValue);
    }

    @org.junit.jupiter.api.Test
    void clear() {
        myHashMap.clear();
        assertEquals(0, myHashMap.size());
    }

    @org.junit.jupiter.api.Test
    void keySet() {
        Set newSet = myHashMap.keySet();
        assertEquals(2, newSet.size());
    }

    @org.junit.jupiter.api.Test
    void values() {
        Collection values = myHashMap.values();
        assertEquals(2, values.size());
    }

    @org.junit.jupiter.api.Test
    void entrySet() {
        Set newSet = myHashMap.entrySet();
        assertEquals(2, newSet.size());
    }

    @org.junit.jupiter.api.Test
    void replaceMyNode() {
        myHashMap.replaceMyNode("Woah", "NewDay");
        String myNodeValue = (String) myHashMap.get("Woah");
        assertEquals("NewDay", myNodeValue);
    }

    @org.junit.jupiter.api.Test
    void increaseCapacity() {
        myHashMap.increaseCapacity();
        assertEquals(32, myHashMap.getCapacity());
        String myNodeValue = (String) myHashMap.get("Woah");
        assertEquals("It works.", myNodeValue);
    }

    @org.junit.jupiter.api.Test
    void put_throwsExceptionWhenKeyIsUsed() {
        assertThrows(IllegalArgumentException.class, () ->
            myHashMap.put("Woah", "Road"));
    }

    @org.junit.jupiter.api.Test
    void replaceMyNode_throwsExceptionIfKeyIsNotFoundOrNull() {
        assertThrows(NullPointerException.class, () ->
                myHashMap.replaceMyNode("Boar", "Road"));
        assertThrows(NullPointerException.class, () ->
                myHashMap.replaceMyNode(null, "Road"));
    }

    @org.junit.jupiter.api.Test
    void remove_throwsExceptionIfKeyIsNull() {
        assertThrows(NullPointerException.class, () ->
                myHashMap.replaceMyNode(null, "Road"));
    }
}