package Model.ADTs;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface MyIDictionary<K, V> {
    V lookup(K key);
    void add(K key, V val);
    boolean isDefined(K key);
    void remove(K key);
    String toString();
    Set<K> keySet();

    void update(K key, V value);

    ConcurrentHashMap<K, V> getContent();
    MyDictionary<K, V> deepCopy();

    V getValue(K var);
}
