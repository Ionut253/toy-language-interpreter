package Model.Stmts;

import Model.ADTs.MyDictionary;
import Model.ADTs.MyIDictionary;
import Model.Expr.IExp;
import Model.PrgState;
import Model.Types.BoolIType;
import Model.Types.IType;
import Model.Values.BoolIValue;
import Model.Values.IValue;
import MyExc.MyException;

public class WhileStmt implements IStmt{
    IExp exp;
    IStmt stmt;
    public WhileStmt(IExp e, IStmt s){
        exp = e;
        stmt = s;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyDictionary<String, IValue> symTbl = state.getSymTable();
        IValue val = exp.eval(symTbl, state.getHeap());
        if (!(val.getType() instanceof BoolIType))
            throw new MyException("Expression is not a boolean value");
        if (!((BoolIValue) val).getVal()) {
            return null;
        } else {
            state.getStk().push(this);
            state.getStk().push(stmt);
        }
        return null;
    }
        @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }

    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return stmt.typecheck(typeEnv);
    }

    public String toString(){
        return "( while (" + exp.toString() + ") \n" + stmt.toString() + " )";
    }
}
