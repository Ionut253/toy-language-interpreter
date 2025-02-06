package Model.Stmts;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyStack;
import Model.PrgState;
import Model.Types.IType;
import MyExc.MyException;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt second;

    public CompStmt(IStmt f, IStmt s){
        first = f;
        second = s;
    }
    public String toString(){
        return first.toString() + ";\n" + second.toString();
    }
    public PrgState execute(PrgState state) throws MyException{
        MyStack<IStmt> stk = state.getStk();

        stk.push(second);
        stk.push(first);

        return null;
    }

    public IStmt deepCopy(){
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }

    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return second.typecheck(first.typecheck(typeEnv));
    }

}
