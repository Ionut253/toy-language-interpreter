package Model.ADTs;


import com.sun.jdi.Value;

import java.security.Key;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {

    ConcurrentHashMap<K, V> dict;

    public MyDictionary(){
        dict = new ConcurrentHashMap<>();
    }


    @Override
    public V lookup(K key) {
        return dict.get(key);
    }

    @Override
    public void add(K key, V val) {
        dict.put(key, val);
    }

    @Override
    public boolean isDefined(K key) {
        return dict.containsKey(key);
    }

    @Override
    public void remove(K key){
        dict.remove(key);
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for (K key: dict.keySet() ){
            s.append(key).append("->").append(dict.get(key)).append("\n");
        }
        return s.toString();
    }

    @Override
    public Set<K> keySet(){
        return dict.keySet();
    }

    @Override
    public void update(K key, V value) {
        dict.put(key, value);
    }

    @Override
    public ConcurrentHashMap<K, V> getContent() {
        return dict;
    }

    @Override
    public MyDictionary<K, V> deepCopy() {
        MyDictionary<K, V> copy = new MyDictionary<>();
        for (K key: dict.keySet()){
            copy.add(key, dict.get(key));
        }
        return copy;
    }

    @Override
    public V getValue(K var) {
        return dict.get(var);
    }

    public ConcurrentHashMap<String, Value> toMap() {
        return (ConcurrentHashMap<String, Value>) dict;
    }
}