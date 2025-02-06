package Model.Types;


import Model.Values.IValue;
import Model.Values.IntValue;

public class IntIType implements IType {
    public boolean equals( Object another ){
        return another instanceof IntIType;
    }

    public String toString(){
        return "int";
    }

    public IValue defaultValue(){
        return new IntValue(0);
    }

    @Override
    public IType deepCopy(){
        return new IntIType();
    }
}
