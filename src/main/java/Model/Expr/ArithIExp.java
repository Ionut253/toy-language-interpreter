package Model.Expr;

import Model.ADTs.MyHeap;
import Model.ADTs.MyIDictionary;
import Model.Types.IType;
import Model.Types.IntIType;
import Model.Values.IValue;
import Model.Values.IntValue;
import MyExc.DivisionByZeroException;
import MyExc.IncompatibleTypeException;
import MyExc.MyException;

public class ArithIExp implements IExp {
    IExp e1;
    IExp e2;
    int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithIExp(IExp ex1, IExp ex2, int o){
        e1 = ex1;
        e2 = ex2;
        op = o;
    }

    public IValue eval(MyIDictionary<String, IValue> tbl, MyHeap<Integer, IValue> heap) throws MyException {
        IValue v1, v2;
        v1 = e1.eval(tbl, heap);
        if (v1.getType().equals(new IntIType())){
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new IntIType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if ( op == 1 ) return new IntValue(n1+n2);
                if ( op == 2 ) return new IntValue(n1-n2);
                if ( op == 3 ) return new IntValue(n1*n2);
                if ( op == 4 ) {
                    if ( n2 == 0 ) throw new DivisionByZeroException("division by zero");
                    return new IntValue(n1/n2);
                }
            }
            else
                throw new IncompatibleTypeException("second operand is not an integer");
        }
        else
            throw new IncompatibleTypeException("first operand is not an integer");

        return new IntValue(0);
    }

    public String toString(){
        if (op == 1)
            return e1.toString() + " + " + e2.toString();
        else if (op == 2)
            return e1.toString() + " - " + e2.toString();
        else if (op == 3)
            return e1.toString() + " * " + e2.toString();
        else if (op == 4)
            return e1.toString() + " / " + e2.toString();
        return "Null";
    }

    public IExp deepCopy(){
        return new ArithIExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);
        if (type1.equals(new IntIType())){
            if (type2.equals(new IntIType()))
                return new IntIType();
            else
                throw new MyException("Second operand is not an integer!");
        }
        else
            throw new MyException("First operand is not an integer!");
    }
}
