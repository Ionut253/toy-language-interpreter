package Model.Values;

import Model.Types.IType;
import Model.Types.IntIType;

public class IntValue implements IValue {
    int val;
    public IntValue(int v){
        val = v;
    }
    public int getVal(){
        return val;
    }

    public String toString(){
        return Integer.toString(val);
    }

    public IType getType(){
        return new IntIType();
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof IntValue){
            return ((IntValue) obj).getVal() == val;
        }
        return false;
    }
}
