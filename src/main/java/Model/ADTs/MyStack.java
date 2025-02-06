package Model.ADTs;

import MyExc.EmptyExecutionStackException;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack;

    public MyStack(){
        stack = new Stack<T>();
    }

    @Override
    public T pop() throws EmptyExecutionStackException {
        if (stack.isEmpty())
            throw new EmptyExecutionStackException("Execution stack is empty!");
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public MyStack<T> getStk() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for (T elem: stack){
            s.append(elem.toString()).append("\n");
        }
        return s.toString();
    }

    public List<T> toList() {
        return stack.stream().toList();
    }

    public T getTop() {
        return stack.peek();
    }
}
