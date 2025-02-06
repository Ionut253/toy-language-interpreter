package Model.ADTs;

import Model.Values.IValue;
import Model.Values.RefValue;
import com.sun.jdi.Value;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MyHeap<K, V> implements MyIHeap<K, V> {

    ConcurrentHashMap<K, V> heap;
    int freeAddress = 1;

    public MyHeap(){
        heap = new ConcurrentHashMap<>();
    }

    @Override
    public int getFreeAddress() {
        return freeAddress++;
    }

    @Override
    public V lookup(K key) {
        return heap.get(key);
    }

    @Override
    public void add(K key, V val) {
        heap.put(key, val);
    }

    @Override
    public boolean isDefined(K addr) {
        return heap.containsKey(addr);
    }

    @Override
    public void update(K address, V val) {
        heap.put(address, val);
    }

    @Override
    public void setContent(ConcurrentHashMap<Integer, Value> heap) {
        this.heap = (ConcurrentHashMap<K, V>) heap;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (K key: heap.keySet() ){
            s.append(key).append("->").append(heap.get(key)).append("\n");
        }
        return s.toString();
    }

    @Override
    public ConcurrentHashMap<Integer, Value> getContent() {
        return (ConcurrentHashMap<Integer, Value>) heap;
    }

    public Map<Integer, Value> safeGC(List<Integer> symTableAddr, Map<Integer, Value>heap){
        Set<Integer> reachable = computeReachableAddr(symTableAddr, heap);
        return heap.entrySet().stream()
                .filter(e -> reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Set<Integer> computeReachableAddr(List<Integer> symTableAddr, Map<Integer, Value> heap){
        Set<Integer> used = new HashSet<>();
        Queue<Integer> q = new LinkedList<>();

        for (Integer addr : symTableAddr){
            q.add(addr);
            used.add(addr);
        }

        while (!q.isEmpty()){
            Integer current = q.poll();
            IValue val = (IValue) heap.get(current);
            if (val instanceof RefValue){
                RefValue v = (RefValue) val;
                Integer addr = v.getAddr();
                if (!used.contains(addr)){
                    q.add(addr);
                    used.add(addr);
                }
            }
        }
        return used;

    }

    public List<Integer>getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    public ConcurrentHashMap<K, V> toMap() {
        return heap;
    }
}
