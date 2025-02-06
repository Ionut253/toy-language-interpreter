package Model.Expr;

import Model.ADTs.MyHeap;
import Model.ADTs.MyIDictionary;
import Model.Types.IType;
import Model.Types.RefType;
import Model.Values.IValue;
import Model.Values.RefValue;
import MyExc.MyException;


public class HeapReading implements IExp{
    IExp exp;

    public HeapReading(IExp e){
        exp = e;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyHeap<Integer, IValue> heap) throws MyException {
        IValue val = exp.eval(tbl, heap);
        if (! (val.getType() instanceof RefType)){
            throw new MyException("Type of the expression is not a reference type");
        }
        else{
            RefValue ref = (RefValue) val;
            int addr = ref.getAddress();
            if (heap.isDefined(addr)){
                return heap.lookup(addr);
            }
            else{
                throw new MyException("Address is not defined in the heap");
            }
        }
    }

    public String toString(){
        return "rH(" + exp.toString() + ")";
    }


    @Override
    public IExp deepCopy() {
        return new HeapReading(exp.deepCopy());
    }

    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typ = exp.typecheck(typeEnv);
        if ( typ instanceof RefType ){
            RefType reft = (RefType) typ;
            return reft.getInner();
        }
        else
            throw new MyException("The RH argument is not a REF TYPE!" );
    }
}
