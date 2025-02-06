package Model.Expr;

import Model.ADTs.MyHeap;
import Model.ADTs.MyIDictionary;
import Model.Types.BoolIType;
import Model.Types.IType;
import Model.Values.BoolIValue;
import Model.Values.IValue;
import MyExc.IncompatibleTypeException;
import MyExc.MyException;


public class LogicIExp implements IExp {
    IExp e1;
    IExp e2;
    int op; //1-and, 2-or, 3-not

    public LogicIExp(IExp ex1, IExp ex2, int o){
        e1 = ex1;
        e2 = ex2;
        op = o;
    }

    public IValue eval(MyIDictionary<String, IValue> tbl , MyHeap<Integer, IValue> heap) throws MyException {
        IValue v1, v2;
        v1 = e1.eval(tbl, heap);
        if (v1.getType().equals(new BoolIType())){
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new BoolIType())){
                BoolIValue b1 = (BoolIValue) v1;
                BoolIValue b2 = (BoolIValue) v2;
                boolean n1, n2;
                n1 = b1.getVal();
                n2 = b2.getVal();
                if ( op == 1 ) return new BoolIValue(n1 && n2);
                if ( op == 2 ) return new BoolIValue(n1 || n2);
            }
            else
                throw new IncompatibleTypeException("second operand is not a boolean");
        }
        else
            throw new IncompatibleTypeException("first operand is not a boolean");

        return new BoolIValue(false);
    }

    public IExp deepCopy(){
        return new LogicIExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = e1.typecheck(typeEnv);
        type2 = e2.typecheck(typeEnv);
        if ( type1.equals(new BoolIType())){
            if ( type2.equals(new BoolIType()))
                return new BoolIType();
            else
                throw new MyException("Second operand is not boolean !");
            }
        else
            throw new MyException("First operand is not boolean !");
    }
}
