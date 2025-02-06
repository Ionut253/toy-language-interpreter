package Model.ADTs;

public interface MyIList<T> {
        void add(T val);
        void remove(T val);
        boolean contains(T val);
        T get(int index);
        String toString();
        boolean isEmpty();
}
