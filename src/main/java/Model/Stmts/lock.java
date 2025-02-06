package Model.Stmts;

import Model.ADTs.LockTable;
import Model.ADTs.MyIDictionary;
import Model.PrgState;
import Model.Types.IType;
import Model.Types.IntIType;
import Model.Values.IValue;
import Model.Values.IntValue;
import MyExc.MyException;

public class lock implements IStmt {
    String var;

    public lock(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        LockTable<Integer, Integer> lockTable = state.getLockTable();
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        if (!symTable.isDefined(var)) {
            throw new MyException("Variable not found in sym table");
        }
        Integer foundIndex = ((IntValue)symTable.lookup(var)).getVal();
        if (lockTable.isDefined(foundIndex)) {
            if (lockTable.lookup(foundIndex) == -1) {
                lockTable.update(foundIndex, state.getId());
            } else {
                state.getStk().push(this);
            }
        } else {
            throw new MyException("Variable not found in lock table");
        }
        return null;
    }

    public String toString() {
        return "lock(" + var + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new lock(var);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookup(var);
        if ( !(typeVar.equals(new IntIType())) )
            throw new MyException("Lock issue ! The type of the variable is not int! ");
        return typeEnv;
    }
}
