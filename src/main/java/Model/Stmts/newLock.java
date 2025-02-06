package Model.Stmts;

import Model.ADTs.LockTable;
import Model.ADTs.MyIDictionary;
import Model.PrgState;
import Model.Types.IType;
import Model.Types.IntIType;
import Model.Values.IntValue;
import MyExc.MyException;

public class newLock implements IStmt{

    String var;

    public newLock(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        LockTable<Integer, Integer> lockTable = state.getLockTable();
        int freeAddress = lockTable.getFreeLocation();
        lockTable.add(freeAddress, -1);
        if (state.getSymTable().isDefined(var))
            state.getSymTable().update(var, new IntValue(freeAddress));
        else
            state.getSymTable().add(var, new IntValue(freeAddress));
        return null;

    }

    @Override
    public IStmt deepCopy() {
        return new newLock(var);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookup(var);
        if ( !(typeVar.equals(new IntIType())) )
            throw new MyException("NewLock issue ! The type of the variable is not int! ");
        return typeEnv;
    }

    public String toString(){
        return "newLock(" + var + ")";
    }
}
