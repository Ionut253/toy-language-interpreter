package Model.Stmts;

import Model.ADTs.MyIDictionary;
import Model.PrgState;
import Model.Types.IType;
import MyExc.MyException;

public class NopStmt implements IStmt {

    public String toString(){
        return "nop";
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }

    public PrgState execute(PrgState state){
        return null;
    }
}
