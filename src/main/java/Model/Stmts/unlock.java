package Model.Stmts;

import Model.ADTs.LockTable;
import Model.ADTs.MyIDictionary;
import Model.PrgState;
import Model.Types.IType;
import Model.Types.IntIType;
import Model.Types.StringType;
import Model.Values.IntValue;
import MyExc.MyException;

public class unlock implements  IStmt{
    String var;

    public unlock(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        LockTable<Integer, Integer> lockTable = state.getLockTable();
        Integer foundIndex =((IntValue) state.getSymTable().lookup(var)).getVal();
        if (!state.getSymTable().isDefined(var)) {
            throw new MyException("Variable not found in sym table");
        }
        if (lockTable.isDefined(foundIndex)) {
            if (lockTable.lookup(foundIndex) == state.getId()) {
                lockTable.update(foundIndex, -1);
            }
        }
            return null;
    }

    @Override
    public String toString() {
        return "unlock(" + var + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new unlock(var);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookup(var);
        if ( !(typeVar.equals(new IntIType())) )
            throw new MyException("Unlock issue ! The type of the variable is not int! ");
        return typeEnv;
    }

}
