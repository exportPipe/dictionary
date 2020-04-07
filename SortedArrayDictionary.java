package dictionary;

import java.util.Arrays;
import java.util.Iterator;

public class SortedArrayDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    private static final int DEF_CAPACITY = 17;
    private int size;
    private Entry<K, V>[] data;

    // Constructor generating new instance
    public SortedArrayDictionary() {
        size = 0;
        data = new Entry[DEF_CAPACITY];
    }
    @Override
    public V insert(K key, V value) {
        int i = searchKey(key);

        // updating value of existing entry
        if (i != -1) {
            V rv = data[i].getValue();
            data[i].setValue(value);
            return rv;
        }

        // new entry
        if (data.length == size) {
            ensureCapacity();
        }
        int j = size-1;
        while (j >= 0 && key.compareTo(data[j].getKey()) < 0) {
            data[j + 1] = data[j];
            j--;
        }
        data[j+1] = new Entry<K, V>(key, value);
        size++;
        return null;
    }

    private void ensureCapacity() {
        data = Arrays.copyOf(data, size*2);
    }

    // binary search
    private int searchKey(K key) {
        int li = 0;
        int re = size - 1;

        while (re >= li) {
            int m = (li + re) / 2;
            if (key.compareTo(data[m].getKey()) < 0)
                re = m - 1;
            else if (key.compareTo(data[m].getKey()) > 0)
                li = m + 1;
            else
                return m;
        }
        return -1; // nicht gefunden
    }

    @Override
    public V search(K key) {
        int i = searchKey(key);
        if (i >= 0)
            return data[i].getValue();
        return null;
    }


    @Override
    public V remove(K key) {
        // searching for entry
        int i = searchKey(key);
        if (i == -1)
            return null; // not found

        // found, saving old value for return
        V rv = data[i].getValue();
        // putting every entry one index back, thus deleting
        for (int j = i; j < size - 1; j++) {
            data[j] = data[j+1];
        }
        data[--size] = null;
        return rv;
    }

    @Override
    public int size() {
        return size;
    }
    @Override
    public Iterator<Dictionary.Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {
            private int current = 0;
            @Override
            public boolean hasNext() {
                return current < size && data[current] != null;
            }

            @Override
            public Entry<K, V> next() {
                return data[current++];
            }
        };
    }
}
