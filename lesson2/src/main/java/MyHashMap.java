import java.util.*;

public class MyHashMap<K, V> implements Map<K, V> {

    private final int INITIAL_CAPACITY = 16;
    private final float LOAD_FACTOR = 0.75F;
    private MyNode<K, V>[] arr;
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

    public int getCapacity() {
        return capacity;
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

        public void setNextNode(MyNode<K, V> node) {
            this.nextNode = node;
        }

        public MyNode<K, V> getNextNode() {
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
            for (MyNode<K, V> myNode : this.arr) {
                if (myNode != null) {
                    if (myNode.value.equals(value)) {
                        MyNode<K, V> tempNode = myNode;
                        while (tempNode.nextNode != null) {
                            //strange behavior if (tempNode.key == value) {
                            if (tempNode.value.equals(value)) {
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
        if (hash == 0) {
            return this.arr[hash].value;
        }
        if (!this.arr[hash].key.equals(key)) {
            MyNode<K, V> tempNode = this.arr[hash];
            while (tempNode.nextNode != null) {
                if (tempNode.key.equals(key)) {
                    return tempNode.value;
                }
                tempNode = tempNode.nextNode;
            }
            return null;
        } else {
            return this.arr[hash].value;
        }
    }

    public V replace(K key, V value) {
        int hash = getIndex(key, this.capacity);
        V oldValue;
        if (this.arr[hash] == null) {
            throw new NullPointerException("The node with such key doesn't exist!");
        }
        if (hash == 0) {
            oldValue = this.arr[hash].value;
            this.arr[hash].value = value;
            return oldValue;
        }
        if (!this.arr[hash].key.equals(key)) {
            MyNode<K, V> tempNode = this.arr[hash];
            while (tempNode.nextNode != null) {
                if (tempNode.key.equals(key)) {
                    oldValue = tempNode.value;
                    tempNode.setValue(value);
                    return oldValue;
                }
                tempNode = tempNode.nextNode;
            }
            throw new NullPointerException("The node with such key doesn't exist!");
        } else {
            oldValue = this.arr[hash].value;
            this.arr[hash].setValue(value);
            return oldValue;
        }
    }

    public V put(K key, V value) {
        updateIfFull();
        if (this.containsKey(key)) {
            V oldVal = get(key);
            this.replace(key, value);
            return oldVal;
        }
        MyNode<K, V> newNode = new MyNode<>(key, value);
        int hash = getIndex(newNode.key, this.capacity);
        if (this.arr[hash] != null) {
            MyNode<K, V> tempNode = this.arr[hash];
            while (tempNode.nextNode != null) {
                tempNode = tempNode.nextNode;
            }
            tempNode.setNextNode(newNode);
        } else {
            this.arr[hash] = newNode;
        }
        size++;
        return null;
    }

    private void testMethod() {
           Map<Integer, Integer> d = new HashMap<>();
           d.put(12, 34);
           d.remove(12);
           d.putAll(d);
           d.remove(2);
    }

    public V remove(Object key) {
        int hash = getIndex(key, this.capacity);
        if (key == null) {
            if (this.arr[hash] == null) {
                return null;
            } else {
                V oldVal = get(key);
                this.arr[0] = null;
                return oldVal;
            }
        }
        MyNode<K, V> nodeToRemove;
        MyNode<K, V> currentNode = this.arr[hash];
        while (currentNode != null) {
            if (currentNode.key.equals(key)) {
                nodeToRemove = currentNode;
                this.arr[hash] = currentNode.getNextNode();
                this.size--;
                return nodeToRemove.value;
            }
            if (currentNode.getNextNode() != null) {
                if (currentNode.getNextNode().key.equals(key)) {
                    nodeToRemove = currentNode.getNextNode();
                    MyNode<K, V> newNextNode = currentNode.getNextNode().getNextNode();
                    currentNode.setNextNode(newNextNode);
                    this.size--;
                    return nodeToRemove.value;
                } else {
                    currentNode = currentNode.getNextNode();
                }
            }
        }
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        MyHashMap<K, V> myMap = (MyHashMap<K, V>) map;
        if (myMap.arr != null && myMap.size > 0) {
            for (int i = 0; i < myMap.arr.length; i++) {
                if (myMap.arr[i] != null) {
                    MyNode<K, V> currentNode = myMap.arr[i];
                    while (currentNode != null) {
                        this.put(currentNode.key,
                                    currentNode.value);
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
        Collection<MyNode<K, V>> fullCollection = this.importHashMapToCollection();
        for (MyNode<K, V> node : fullCollection) {
            set.add(node.key);
        }
        return set;
    }

    public Collection values() {
        Collection<V> collection = new ArrayList<>();
        Collection<MyNode<K, V>> fullCollection = this.importHashMapToCollection();
        for (MyNode<K, V> node : fullCollection) {
            collection.add(node.value);
        }
        return collection;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        Collection<MyNode<K, V>> fullCollection = this.importHashMapToCollection();
        for (MyNode<K, V> node : fullCollection) {
            set.add(node);
        }
        return set;
    }

    private int getIndex(Object key, int capacity) {
        int index = 0;
        if (key == null) {
            return index;
        }
        index = key.hashCode() % capacity;
        if (index == 0) {
            index = (key.hashCode() + 1) % capacity;
        }
        return index;
    }

    private Collection<MyNode<K, V>> importHashMapToCollection() {
        Collection<MyNode<K, V>> collection = new ArrayList<>();
        if (this.arr != null && this.size > 0) {
            for (MyNode<K, V> node : this.arr) {
                if (node != null) {
                    MyNode<K, V> currentNode = node;
                    while (currentNode != null) {
                        MyNode<K, V> tempNode = currentNode;
                        tempNode.setNextNode(null);
                        collection.add(tempNode);
                        currentNode = currentNode.getNextNode();
                    }
                }
            }
        }
        return collection;
    }

    private boolean checkIfFull() {
        float currentLoadFactor = (float) this.size / this.capacity;
        return currentLoadFactor >= LOAD_FACTOR;
    }

    public void increaseCapacity() {
        MyHashMap<K, V> newMap = new MyHashMap<>(this.capacity * 2);
        if (this.arr != null && this.size > 0) {
            for (MyNode<K, V> kvMyNode : this.arr) {
                if (kvMyNode != null) {
                    MyNode<K, V> tempNode = kvMyNode;
                    while (tempNode != null) {
                        newMap.put(tempNode.key, tempNode.value);
                        tempNode = tempNode.getNextNode();
                    }
                }
            }
            this.arr = newMap.arr;
            this.size = newMap.size;
            this.capacity = newMap.capacity;
        }
    }

    private void updateIfFull() {
        if (checkIfFull()) {
            increaseCapacity();
        }
    }
}
