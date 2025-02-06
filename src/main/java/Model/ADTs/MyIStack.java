package Model.ADTs;

import MyExc.EmptyExecutionStackException;

public interface MyIStack<T> {

    T pop() throws EmptyExecutionStackException;
    void push(T v);
    MyStack<T> getStk();
    boolean isEmpty();

}
