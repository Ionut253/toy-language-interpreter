package Model.Expr;

import Model.ADTs.MyHeap;
import Model.ADTs.MyIDictionary;
import Model.Types.BoolIType;
import Model.Types.IType;
import Model.Types.IntIType;
import Model.Values.BoolIValue;
import Model.Values.IValue;
import Model.Values.IntValue;
import MyExc.MyException;

public class RelIExp implements IExp{
    IExp exp1;
    IExp exp2;
    String op;

    public RelIExp(IExp e1, IExp e2, String o){
        exp1 = e1;
        exp2 = e2;
        op = o;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyHeap<Integer, IValue> heap) throws MyException {
        IValue v1, v2;
        v1 = exp1.eval(tbl, heap);
        v2 = exp2.eval(tbl, heap);
        if ( v1.getType().equals(new IntIType())) {
            if (v2.getType().equals(new IntIType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op.equals("<")) return new BoolIValue(n1 < n2);
                if (op.equals("<=")) return new BoolIValue(n1 <= n2);
                if (op.equals("==")) return new BoolIValue(n1 == n2);
                if (op.equals("!=")) return new BoolIValue(n1 != n2);
                if (op.equals(">")) return new BoolIValue(n1 > n2);
                if (op.equals(">=")) return new BoolIValue(n1 >= n2);
            }
            else
                throw new MyException("Second operand is not an integer");
        }
        else
            throw new MyException("First operand is not an integer");
        return null;
    }

    public String toString(){
        return exp1.toString() + " " + op + " " + exp2.toString();
    }

    @Override
    public IExp deepCopy() {
        return new RelIExp(exp1.deepCopy(), exp2.deepCopy(), op);
    }


    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType type1, type2;
        type1 = exp1.typecheck(typeEnv);
        type2 = exp2.typecheck(typeEnv);
        if (type1.equals(new IntIType())){
            if (type2.equals(new IntIType()))
                return new BoolIType();
            else
                throw new MyException("Second operand is not an integer!");
        }
        else
            throw new MyException("First operand is not an integer!");
    }
}
