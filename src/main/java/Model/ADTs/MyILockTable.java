package Model.ADTs;

import java.util.concurrent.ConcurrentHashMap;

public interface MyILockTable<K, V> {
    void add(K key, V val);
    V lookup(K key);
    void remove(K key);
    boolean isDefined(K key);
    void update(K key, V value);
    String toString();

    ConcurrentHashMap<K, V> get(K key);
}
