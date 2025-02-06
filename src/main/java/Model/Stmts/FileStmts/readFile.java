package Model.Stmts.FileStmts;

import Model.ADTs.MyIDictionary;
import Model.Expr.IExp;
import Model.PrgState;
import Model.Stmts.IStmt;
import Model.Types.IType;
import Model.Types.IntIType;
import Model.Types.StringType;
import Model.Values.IValue;
import Model.Values.IntValue;
import MyExc.MyException;

import java.io.BufferedReader;

public class readFile implements IStmt {

    IExp exp;
    String var_name;

    public readFile(IExp e, String var_name) {
        exp = e;
        this.var_name = var_name;
    }

    public PrgState execute(PrgState state) throws MyException {
        if ( !state.getSymTable().isDefined(var_name) )
            throw new MyException("Variable is not defined!");
        if ( !(state.getSymTable().lookup(var_name).getType() instanceof IntIType) )
            throw new MyException("Variable is not an integer!");

        IValue v = exp.eval(state.getSymTable(), state.getHeap());
        if ( ! (v.getType() instanceof StringType) )
            throw new MyException("FileName is not a string!");

        BufferedReader bufferedReader = state.getFileTable().lookup(v.toString());

        if ( bufferedReader == null )
            throw new MyException("File is not opened!");

        try {
            String line = bufferedReader.readLine();
            if ( line == null )
                state.getSymTable().add(var_name, new IntValue(0));
            else
                state.getSymTable().add(var_name, new IntValue(Integer.parseInt(line)));

        } catch (Exception e) {
            throw new MyException("Error reading from file!");
        }
        return null;
    }

    public IStmt deepCopy() {
        return new readFile(exp.deepCopy(), var_name);
    }

    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookup(var_name);
        IType typeExp = exp.typecheck(typeEnv);
        if ( typeVar.equals(new IntIType()) && typeExp.equals(new StringType()) )
            return typeEnv;
        else
            throw new MyException("ReadFile statement: right hand side and left hand side have different types!");
        }

    public String toString() {
        return "readFile(" + exp.toString() + ", " + var_name + ")";
    }
}
