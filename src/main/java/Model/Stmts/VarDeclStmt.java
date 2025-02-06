package Model.Stmts;

import Model.ADTs.MyIDictionary;
import Model.PrgState;
import Model.Types.IType;
import Model.Values.IValue;
import MyExc.MyException;

public class VarDeclStmt implements IStmt {
    String name;
    IType type;

    public VarDeclStmt(String n, IType t){
        name = n;
        type = t;
    }

    public String toString(){
        return type.toString() + " " + name;
    }

    public IStmt deepCopy() {
        return new VarDeclStmt(name, type.deepCopy());
    }

    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        typeEnv.add(name, type);
        return typeEnv;
    }

    public PrgState execute(PrgState state ) throws MyException{
        MyIDictionary<String, IValue> symTbl = state.getSymTable();
        if (symTbl.isDefined(name)) {
            throw new MyException("Variable " + name + " is already declared");
        }
        else {
            symTbl.add(name, type.defaultValue());
        }
        return null;
    }


}
