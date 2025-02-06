package Model.ADTs;

import com.sun.jdi.Value;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface MyIHeap<K, V> {
    int getFreeAddress();
    V lookup(K key);
    void add(K key, V val);
    boolean isDefined(K addr);
    void update(K address, V val);
    void setContent(ConcurrentHashMap<Integer, Value> heap);

    ConcurrentHashMap<Integer, Value> getContent();
}
