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
import java.io.FileReader;

public class openRFile implements IStmt {
    IExp exp;

    public openRFile(IExp e) {
        exp = e;
    }

    public PrgState execute(PrgState state) throws MyException {
        IValue v = exp.eval(state.getSymTable(), state.getHeap());
        if ( ! (v.getType() instanceof StringType) )
            throw new MyException("FileName is not a string!");
        if ( state.getFileTable().isDefined(v.toString()) ){
            throw new MyException("File is already opened!");
        }
        try {
            BufferedReader b = new BufferedReader(new FileReader(v.toString()));
            state.getFileTable().add(v.toString(), b);
        } catch (Exception e) {
            throw new MyException("Error opening file!" + e.getMessage());
        }

        return null;
    }

    public IStmt deepCopy() {
        return new openRFile(exp.deepCopy());
    }

    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    public String toString() {
        return "openRFile(" + exp.toString() + ")";
    }
}
