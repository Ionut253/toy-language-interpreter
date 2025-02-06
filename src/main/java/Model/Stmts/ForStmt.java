package Model.Stmts;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyStack;
import Model.Expr.*;
import Model.PrgState;
import Model.Types.IType;
import Model.Types.IntIType;
import Model.Types.StringType;
import Model.Values.IntValue;
import MyExc.MyException;

public class ForStmt implements IStmt{
    IExp init;
    IExp cond;
    IExp incr;
    IStmt stmt;

    public ForStmt(IExp i, IExp c, IExp in, IStmt s){
        init = i;
        cond = c;
        incr = in;
        stmt = s;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyStack<IStmt> stk = state.getStk();
        IStmt newStmt = new CompStmt(new VarDeclStmt("v", new IntIType()),
                new CompStmt(new AssignStmt("v", init), new WhileStmt(new RelIExp(new VarIExp("v"), cond, "<"), new CompStmt(stmt, new AssignStmt("v" ,incr)))));
        stk.push(newStmt);
            return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(init.deepCopy(), cond.deepCopy(), incr.deepCopy(), stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeInit = init.typecheck(typeEnv);
        IType typeCond = cond.typecheck(typeEnv);
        IType typeIncr = incr.typecheck(typeEnv);
        if (typeInit.equals(new IntIType()) && typeCond.equals(new IntIType()) && typeIncr.equals(new IntIType())){
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException(" The condition of FOR has not the type int!");
    }

    public String toString(){
        return "for( v = " + init.toString() + "; v < " + cond.toString() + "; v = " + incr.toString() + ")\n{" + stmt.toString() + "}";
    }
}
