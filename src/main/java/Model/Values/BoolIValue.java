package Model.Values;

import Model.Types.BoolIType;
import Model.Types.IType;

public class BoolIValue implements IValue {
    boolean val;
    public BoolIValue(boolean v){
        val = v;
    }
    public boolean getVal(){
        return val;
    }

    public String toString(){
        return Boolean.toString(val);
    }

    public IType getType(){
        return new BoolIType();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof BoolIValue){
            return ((BoolIValue) obj).getVal() == val;
        }
        return false;
    }

}
