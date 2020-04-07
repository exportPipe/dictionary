package dictionary;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;


public class HashDictionary<K, V> implements Dictionary<K, V> {

    private final static int PRIM_CAPACITY = 17;
    private int size;
    private int numberEntries;
    private LinkedList<Entry<K, V>>[] tab;

    public HashDictionary() {
        size = PRIM_CAPACITY;
        numberEntries = 0;
        tab = new LinkedList[PRIM_CAPACITY];
        for (int i = 0; i < PRIM_CAPACITY; ++i) {
            tab[i] = new LinkedList<>();
        }
    }

    @Override
    public V insert(K key, V value) {
        int index = getHash(key);
        // Key exists, return value
        if (search(key) != null) {
            for (int i = 0; i < tab[index].size(); ++i) {
                if (tab[index].get(i).getKey().equals(key)) {
                    return tab[index].get(i).setValue(value);
                }
            }
        }
        if (numberEntries != 0 && size != 0) {
            double loadFactor = numberEntries * 1.0 / size;
            if (loadFactor > 3) {
                boolean primeFound = false;
                for (int i = 0; i < Integer.MAX_VALUE; ++i) {
                    if (isPrime(2 * size + i)) {
                        ensureCapacityReorder(2 * size + i);
                        primeFound = true;
                        break;
                    }
                }
                if (! primeFound) {
                    ensureCapacityReorder(2 * size);
                }
            }
        }
        if (index >= size)
            return null;
        tab[index].add(new Entry<>(key, value));
        numberEntries++;
        return null;
    }

    private void ensureCapacityReorder(int newSize) {
        if (newSize < size) return;
        LinkedList<Entry<K, V>>[] old = tab;
        tab = new LinkedList[newSize];
        for (int i = 0; i < newSize; ++i) {
            tab[i] = new LinkedList<>();
        }
        // reorder
        numberEntries = 0;
        int oldSize = size;
        size = newSize;
        for (int i = 0; i < oldSize; ++i) {
            for (Entry<K, V> e : old[i]) {
                int newIndex = getHash(e.getKey());
                tab[newIndex].add(e);
                numberEntries++;
            }
        }
    }

    private boolean isPrime(int value) {
        if (value <= 2) return (value == 2);
        for (int i = 2; i * i <= value; i++) {
            if (value % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int getHash(K key) {
        int adr = key.hashCode();
        if (adr < 0)
            adr = -adr;
        return adr % size;
    }

    @Override
    public V search(K key) {
        int index = getHash(key);
        if (tab[index] == null)
            return null;
        for (Entry<K, V> e: tab[index]) {
            if (key.equals(e.getKey())) {
                return e.getValue();
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int index = getHash(key);
        if (index >= size) return null;
        if (tab[index].isEmpty()) return null;
        V rv;
        numberEntries--;
        for (int i = 0; i < tab[index].size(); ++i) {
            if (key.equals(tab[index].get(i).getKey())) {
                rv = tab[index].get(i).getValue();
                tab[index].remove(i);
                return rv;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {
            private int arrayIdx = 0;
            private int listIdx = 0;
            @Override
            public boolean hasNext() {
                // more than one list element at table[array index]
                if (!tab[arrayIdx].isEmpty() && listIdx < tab[arrayIdx].size()) {
                    return true;
                }
                // if not, check if following tab lists are not empty
                for (int i = arrayIdx + 1; i < size; ++i) {
                    if (!tab[i].isEmpty()) {
                        return true;
                    }
                }
                // if all empty, no next
                return false;
            }

            @Override
            public Entry<K, V> next() {
                if (!hasNext()) throw new NoSuchElementException();

                // if list has more than one next entry, return next entry in this list
                if (listIdx < tab[arrayIdx].size()) {
                    return tab[arrayIdx].get(listIdx++);
                }
                // if current list reached end, check next list(s)
                int i;
                for (i = ++arrayIdx; i < size; ++i) {
                    if (!tab[i].isEmpty()) {
                        break;
                    } else {
                        ++arrayIdx;
                    }
                }
                listIdx = 1;
                return tab[i].getFirst();
            }
        };
    }
}
