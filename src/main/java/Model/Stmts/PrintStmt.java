package Model.Stmts;


import Model.ADTs.MyIDictionary;
import Model.Expr.IExp;
import Model.PrgState;
import Model.Types.IType;
import MyExc.MyException;

public class PrintStmt implements IStmt {
    IExp exp;

    public PrintStmt(IExp e){
        exp = e;
    }

    public IExp getExp(){
        return exp;
    }

    public String toString(){
        return "print(" + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    public PrgState execute(PrgState state ){
        state.getOut().add(exp.eval(state.getSymTable(), state.getHeap()));
        return null;
    }

}
