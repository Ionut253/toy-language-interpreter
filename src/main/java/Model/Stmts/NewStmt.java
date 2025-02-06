package Model.Stmts;
import Model.ADTs.MyDictionary;
import Model.ADTs.MyHeap;
import Model.ADTs.MyIDictionary;
import Model.Expr.IExp;
import Model.PrgState;
import Model.Types.IType;
import Model.Types.RefType;
import Model.Values.IValue;
import Model.Values.RefValue;
import MyExc.MyException;

public class NewStmt implements  IStmt {

    String var_name;
    IExp exp;

    public NewStmt(String n, IExp e) {
        var_name = n;
        exp = e;
    }

    public PrgState execute(PrgState state) throws MyException {
        MyDictionary<String, IValue> symTBL = state.getSymTable();

        if (symTBL.isDefined(var_name)) {
            if (!(symTBL.lookup(var_name).getType() instanceof RefType)) {
                throw new MyException("Type of the variable is not a reference type");
            } else {
                IValue val = exp.eval(symTBL, state.getHeap());
                if (!(val.getType().equals(((RefValue) symTBL.lookup(var_name)).getLocationType()))) {
                    throw new MyException("Type of the expression is not the same as the type of the reference");
                } else {
                    MyHeap<Integer, IValue> heap = state.getHeap();
                    int addr = heap.getFreeAddress();
                    heap.add(addr, val);
                    symTBL.update(var_name, new RefValue(addr, val.getType()));
                }
            }
        } else {
            throw new MyException("Variable is not defined");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(var_name, exp.deepCopy());
    }

    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.lookup(var_name);
        IType typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new MyException("NEW statement : right hand side and left hand side have different types!" + typeVar.toString() + " = " + typeExp.toString());
    }

    public String toString() {
        return "new(" + var_name + ", " + exp.toString() + ")";
    }
}

