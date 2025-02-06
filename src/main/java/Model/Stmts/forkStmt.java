package Model.Stmts;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyStack;
import Model.PrgState;
import Model.Types.IType;
import MyExc.MyException;

public class forkStmt implements IStmt{

    IStmt stmt;

    public forkStmt(IStmt stmt){
        this.stmt = stmt;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        return new PrgState(new MyStack<>(), state.getSymTable().deepCopy(),
                        state.getOut(), stmt, state.getFileTable(), state.getHeap(), state.getLockTable());
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return stmt.typecheck(typeEnv);
    }

    public String toString(){
        return "fork(" + stmt.toString() + ")";
    }
}
