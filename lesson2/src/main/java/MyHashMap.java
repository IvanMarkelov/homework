import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {

    private final int INITIAL_CAPACITY = 16;
    private final float LOAD_FACTOR = 0.75F;
    private MyNode[] arr;
    private int capacity;
    private int size;

    public MyHashMap() {
        this.arr = new MyNode[INITIAL_CAPACITY];
        this.capacity = INITIAL_CAPACITY;
        this.size = 0;
    }
    
    public MyHashMap(int capacity) {
        this.arr = new MyNode[capacity];
        this.capacity = capacity;
        this.size = 0;
    }

    public MyNode[] getArr() {
        return arr;
    }

    public void setArr(MyNode[] arr) {
        this.arr = arr;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    class MyNode<K, V> implements Map.Entry<K, V> {

        private final K key;
        private V value;
        private MyNode<K, V> nextNode;

        public MyNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.nextNode = null;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }

        public void setNextNode(MyNode node) {
            this.nextNode = node;
        }

        public MyNode getNextNode() {
            return this.nextNode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyNode<?, ?> myNode = (MyNode<?, ?>) o;
            return key.equals(myNode.key) &&
                    Objects.equals(value, myNode.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }

    //  class MyLinkedList implements List

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    public boolean containsValue(Object value) {
        if (this.arr != null && this.size > 0) {
            for (MyNode myNode : this.arr) {
                if (myNode != null) {
                    if (myNode.getValue() != value) {
                        MyNode tempNode = myNode;
                        while (tempNode.nextNode != null) {
                            if (tempNode.getKey() == value) {
                                return true;
                            }
                            tempNode = tempNode.nextNode;
                        }
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public V get(Object key) {
        int hash = getIndex(key, this.capacity);
        if (this.arr[hash] == null) {
            return null;
        }
        if (this.arr[hash].getKey() != key) {
            MyNode tempNode = this.arr[hash];
            while (tempNode.nextNode != null) {
                if (tempNode.getKey() == key) {
                    return (V) tempNode.getValue();
                }
                tempNode = tempNode.nextNode;
            }
            return null;
        } else {
            return (V) this.arr[hash].getValue();
        }
    }

    public V replaceMyNode(K key, V value) {
        int hash = getIndex(key, this.capacity);
        V oldValue;
        if (this.arr[hash] == null || key == null) {
            throw new NullPointerException("The node with such key doesn't exist!");
        }
        if (this.arr[hash].getKey() != key) {
            MyNode tempNode = this.arr[hash];
            while (tempNode.nextNode != null) {
                if (tempNode.getKey() == key) {
                    oldValue = (V) tempNode.getValue();
                    tempNode.setValue(value);
                    return oldValue;
                }
                tempNode = tempNode.nextNode;
            }
            throw new NullPointerException("The node with such key doesn't exist!");
        } else {
            oldValue = (V) this.arr[hash];
            this.arr[hash].setValue(value);
            return oldValue;
        }
    }

    public V put(K key, V value) {
        updateIfFull();
        if (this.containsKey(key)) {
            throw new IllegalArgumentException("This key already exists!");
        }
        MyNode newNode = new MyNode(key, value);
        int hash = getIndex(newNode.getKey(), this.capacity);
        if (this.arr[hash] != null) {
            MyNode tempNode = this.arr[hash];
            while (tempNode.nextNode != null) {
                tempNode = tempNode.nextNode;
            }
            tempNode.setNextNode(newNode);
        } else {
            this.arr[hash] = newNode;
        }
        size++;
        return (V) newNode.getValue();
    }

    public V remove(Object key) {
        if (key == null) {
            throw new NullPointerException("The key cannot be null!");
        }
        int hash = getIndex(key, this.capacity);
        MyNode nodeToRemove;
        MyNode currentNode = this.arr[hash];
        while (currentNode != null) {
            if (currentNode.getKey() == key) {
                nodeToRemove = currentNode;
                this.arr[hash] = currentNode.getNextNode();
                this.size--;
                return (V) nodeToRemove;
            }
            if (currentNode.getNextNode() != null) {
                if (currentNode.getNextNode().getKey() == key) {
                    nodeToRemove = currentNode.getNextNode();
                    MyNode newNextNode = currentNode.getNextNode().getNextNode();
                    currentNode.setNextNode(newNextNode);
                    this.size--;
                    return (V) nodeToRemove;
                } else {
                    currentNode = currentNode.getNextNode();
                }
            }
        }
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        MyHashMap myMap = (MyHashMap) map;
        if (myMap.arr != null && myMap.size > 0) {
            for (int i = 0; i < myMap.arr.length; i++) {
                if (myMap.arr[i] != null) {
                    MyNode currentNode = myMap.arr[i];
                    while (currentNode != null) {
                        if (this.containsKey(currentNode.getKey())) {
                            this.replaceMyNode((K) currentNode.getKey(),
                                    (V) currentNode.getValue());
                        } else {
                            this.put((K) currentNode.getKey(),
                                    (V) currentNode.getValue());
                        }
                        currentNode = currentNode.getNextNode();
                    }
                }
            }
        }
    }

    public void clear() {
        this.arr = new MyNode[INITIAL_CAPACITY];
        this.size = 0;
    }

    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        Collection<MyNode> fullCollection = this.importHashMapToCollection();
        for (MyNode node : fullCollection) {
            set.add((K) node.getKey());
        }
        return set;
    }

    public Collection values() {
        Collection<V> collection = new ArrayList<>();
        Collection<MyNode> fullCollection = this.importHashMapToCollection();
        for (MyNode node : fullCollection) {
            collection.add((V) node.getValue());
        }
        return collection;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        Collection<MyNode> fullCollection = this.importHashMapToCollection();
        for (MyNode node : fullCollection) {
            set.add(node);
        }
        return set;
    }

    public int getIndex(Object key, int capacity) {
        return key.hashCode() % capacity;
    }

    public Collection importHashMapToCollection() {
        Collection<V> collection = new ArrayList<>();
        if (this.arr != null && this.size > 0) {
            for (int i = 0; i < this.arr.length; i++) {
                if (this.arr[i] != null) {
                    MyNode currentNode = this.arr[i];
                    while (currentNode != null) {
                        MyNode tempNode = currentNode;
                        tempNode.setNextNode(null);
                        collection.add((V) tempNode);
                        currentNode = currentNode.getNextNode();
                    }
                }
            }
        }
        return collection;
    }

    public boolean checkIfFull() {
        float currentLoadFactor = (float) this.size / this.capacity;
        return currentLoadFactor >= LOAD_FACTOR;
    }

    public void increaseCapacity() {
        MyHashMap<K, V> newMap = new MyHashMap<K, V>(this.capacity * 2);
        if (this.arr != null && this.size > 0) {
            for (int i = 0; i < this.arr.length; i++) {
                if (this.arr[i] != null) {
                    MyNode tempNode = this.arr[i];
                    while (tempNode != null) {
                        newMap.put((K) tempNode.getKey(), (V) tempNode.getValue());
                        tempNode = tempNode.getNextNode();
                    }
                }
            }
            this.arr = newMap.arr;
            this.size = newMap.size();
            this.capacity = newMap.capacity;
        }
    }

    public void updateIfFull() {
        if (checkIfFull()) {
            increaseCapacity();
        }
    }
}
