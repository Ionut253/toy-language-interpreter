package Model.Stmts;

import Model.ADTs.MyIDictionary;
import Model.PrgState;
import Model.Types.IType;
import MyExc.MyException;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    String toString();
    IStmt deepCopy();
    MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException;
}
