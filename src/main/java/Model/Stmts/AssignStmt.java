package Model.Stmts;

import Model.ADTs.MyDictionary;
import Model.ADTs.MyIDictionary;
import Model.Expr.IExp;
import Model.PrgState;
import Model.Types.IType;
import Model.Values.IValue;
import MyExc.IncompatibleTypeException;
import MyExc.MyException;

public class AssignStmt implements IStmt {
    String id;
    IExp exp;

    public AssignStmt(String i, IExp e){
        id = i;
        exp = e;
    }

    public String toString(){
        return id + " = " + exp.toString();
    }

    public PrgState execute (PrgState state) throws MyException {
        MyDictionary<String, IValue> symTbl = state.getSymTable();

        if (symTbl.isDefined(id)) {
            IValue val = exp.eval(symTbl, state.getHeap());
            if ( symTbl.lookup(id).getType().equals(val.getType()) ) {
                symTbl.add(id, val);
            }
            else {
                throw new IncompatibleTypeException("declared type of variable " + id + " and type of the assigned expression do not match");
            }
        }
        else {
            throw new MyException("the used variable " + id + " was not declared before");
        }
        return null;
    }

    public IStmt deepCopy(){
        return new AssignStmt(id, exp.deepCopy());
    }

    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookup(id);
        IType typeExp = exp.typecheck(typeEnv);
        if ( typeVar.equals(typeExp))
            return typeEnv;
        else
            throw new MyException("Assignment : right hand side and left hand side have different types! : " + typeVar.toString() + " = " + typeExp.toString());
    }

}