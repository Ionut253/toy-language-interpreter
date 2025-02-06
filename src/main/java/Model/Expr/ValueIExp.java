package Model.Expr;

import Model.ADTs.MyHeap;
import Model.ADTs.MyIDictionary;
import Model.Types.IType;
import Model.Values.IValue;
import MyExc.MyException;

public class ValueIExp implements IExp {
    IValue e;

    public ValueIExp(IValue v ) { e = v;}

    public IValue eval(MyIDictionary<String, IValue> tbl, MyHeap<Integer, IValue> heap) throws MyException {
        return e;
    }

    public String toString(){
        return e.toString();
    }

    public IExp deepCopy(){
        return new ValueIExp(e);
    }

    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return e.getType();
    }

}
