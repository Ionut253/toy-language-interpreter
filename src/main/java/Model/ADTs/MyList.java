package Model.ADTs;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    ArrayList<T> list;

    public MyList(){
        list = new ArrayList<T>();
    }
    @Override
    public void add(T val) {
        list.add(val);
    }

    @Override
    public void remove(T val) {
        list.remove(val);
    }

    @Override
    public boolean contains(T val) {
        return list.contains(val);
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for (T elem: list){
            s.append(elem.toString()).append("\n");
        }
        return s.toString();
    }

    @Override
    public boolean isEmpty(){
        return list.isEmpty();
    }

    public List<T> toList() {
        return list;
    }
}
