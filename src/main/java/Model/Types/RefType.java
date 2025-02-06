package Model.Types;

import Model.Values.IValue;
import Model.Values.RefValue;


public class RefType implements IType{
    IType inner;

    public boolean equals(Object another) {
        if (another instanceof RefType){
            return inner.equals( ((RefType) another).getInner());
        }
        else
            return false;
    }

    public RefType(IType inner){
        this.inner = inner;
    }

    public IType getInner(){ return this.inner;}

    public String toString(){return "Ref(" + inner.toString() + ")";}

    public IValue defaultValue() {
        return new RefValue(0,inner);
    }

    public IType deepCopy() {
        return new RefType(inner.deepCopy());
    }
}
