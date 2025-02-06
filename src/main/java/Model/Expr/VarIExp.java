package Model.Expr;

import Model.ADTs.MyHeap;
import Model.ADTs.MyIDictionary;
import Model.Types.IType;
import Model.Values.IValue;
import MyExc.MyException;
import MyExc.UndefinedVariableException;

public class VarIExp implements IExp {

    String id;

    public VarIExp(String i) {
        id = i;
    }

    public IValue eval(MyIDictionary<String, IValue> tbl, MyHeap<Integer, IValue> heap) throws UndefinedVariableException {
        if (tbl.isDefined(id)) {
            return tbl.lookup(id);
        } else {
            throw new UndefinedVariableException("Variable " + id + " is not defined");
        }
    }

    public IExp deepCopy() {
        return new VarIExp(id);
    }

    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }

    public String toString() {
        return id;
    }


}
