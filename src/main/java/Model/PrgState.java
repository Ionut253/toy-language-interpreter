package Model;

import Model.ADTs.*;
import Model.Stmts.IStmt;
import Model.Values.IValue;
import MyExc.MyException;

import java.io.BufferedReader;

public class PrgState {
    private MyStack<IStmt> exeStack;
    private final MyDictionary<String, IValue> symTable;
    private final MyList<IValue> out;
    private final IStmt originalProgram;
    private final MyDictionary<String, BufferedReader> fileTable;
    private final MyHeap<Integer, IValue> heap;
    private final Integer id;
    private static Integer globalId = 1;
    private static LockTable<Integer, Integer> lockTable = new LockTable<>();
    public PrgState(MyStack<IStmt> stk, MyDictionary<String, IValue> symtbl, MyList<IValue> ot, IStmt prg, MyDictionary<String, BufferedReader> fileTbl, MyHeap<Integer, IValue> hp, LockTable<Integer, Integer> lockTBL){
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        originalProgram = prg;
        fileTable = fileTbl;
        heap = hp;
        id = getNextId();
        lockTable = lockTBL;
        stk.push(prg);
    }

    public LockTable<Integer, Integer> getLockTable() {
        return lockTable;
    }

    public MyStack<IStmt> getStk(){
        return exeStack;
    }

    public MyDictionary<String, IValue> getSymTable(){
        return symTable;
    }

    public MyList<IValue> getOut(){
        return out;
    }

    public IStmt getStmt(){
        return originalProgram;
    }

    public MyDictionary<String, BufferedReader> getFileTable(){
        return fileTable;
    }

    public MyHeap<Integer, IValue> getHeap() {
        return heap;
    }

    public String toString(){
        return  "Program id : " + id.toString() + "\n" + exeStack.toString() + "\n" + symTable.toString() + "\n" +  out.toString() + "\n" + fileTable.toString() + "\n" + heap.toString() + "\n";
    }

    public boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if ( exeStack.isEmpty() )
            throw new MyException("prgstate stack is empty!");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public static synchronized Integer getNextId(){
        return globalId++;
    }

    public int getId(){
        return id;
    }

    public void setExecutionStack(MyStack<IStmt> stack) {
        exeStack = stack;
    }

    public void setSymTable(MyIDictionary<String, IValue> newSymbolTable) {
    }
}
