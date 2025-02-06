package Model.Types;

import Model.Values.BoolIValue;
import Model.Values.IValue;

public class BoolIType implements IType {

    public boolean equals( Object another ){
        return another instanceof BoolIType;
    }

    public String toString(){
        return "bool";
    }

    public IValue defaultValue(){
        return new BoolIValue(false);
    }

    @Override
    public IType deepCopy() {
        return new BoolIType();
    }
}
