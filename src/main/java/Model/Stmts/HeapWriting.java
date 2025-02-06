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

public class HeapWriting implements IStmt{
    String var_name;
    IExp exp;

    public HeapWriting(String var_name, IExp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyDictionary<String, IValue> symTbl = state.getSymTable();
        MyHeap<Integer, IValue> heap = state.getHeap();

        if (symTbl.isDefined(var_name)) {
            IValue val = exp.eval(symTbl, heap);
            if (!(symTbl.lookup(var_name).getType() instanceof RefType)) {
                throw new MyException("the type of the variable " + var_name + " is not a reference type");
            } else {
                RefValue ref = (RefValue) symTbl.lookup(var_name);
                int addr = ref.getAddress();
                if (heap.isDefined(addr)) {
                    heap.update(addr, val);
                } else {
                    throw new MyException("the address " + addr + " is not defined in the heap");
                }
            }
        }
        else {
            throw new MyException("the used variable " + var_name + " was not declared before");
        }

        return null;
    }

    public String toString() {
        return "wH(" + var_name + ", " + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar =  typeEnv.lookup(var_name);
        IType typeExp = exp.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new MyException("HEAP WRITING statement : type of " + var_name + " - " + typeVar.toString() + " is not matching " + exp.toString() + " - " + typeExp.toString()+ " !");
    }
}
