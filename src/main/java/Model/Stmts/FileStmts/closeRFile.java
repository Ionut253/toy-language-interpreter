package Model.Stmts.FileStmts;

import Model.ADTs.MyIDictionary;
import Model.Expr.IExp;
import Model.PrgState;
import Model.Stmts.IStmt;
import Model.Types.IType;
import Model.Types.StringType;
import Model.Values.IValue;
import MyExc.MyException;

import java.io.BufferedReader;

public class closeRFile implements IStmt {
    IExp exp;

    public closeRFile(IExp e) {
        exp = e;
    }

    public PrgState execute(PrgState state) throws MyException {
        IValue v = exp.eval(state.getSymTable(), state.getHeap());
        if ( ! (v.getType() instanceof StringType) )
            throw new MyException("FileName is not a string!");
        if ( ! state.getFileTable().isDefined(v.toString()) )
            throw new MyException("File is not opened!");
        BufferedReader br = state.getFileTable().lookup(v.toString());
        try {
            br.close();
        } catch (Exception e) {
            throw new MyException("Error closing file!");
        }
        state.getFileTable().remove(v.toString());
        return null;
    }

    public IStmt deepCopy() {
        return new closeRFile(exp.deepCopy());
    }

    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    public String toString() {
        return "closeRFile(" + exp.toString() + ")";
    }
}
