package Model.Expr;

import Model.ADTs.MyHeap;
import Model.ADTs.MyIDictionary;
import Model.Types.IType;
import Model.Values.IValue;
import MyExc.MyException;

public interface IExp {
    IValue eval(MyIDictionary<String, IValue>tbl, MyHeap<Integer, IValue> heap) throws MyException;
    IExp deepCopy();
    IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException;
}
