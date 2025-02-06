package Model.Values;

import Model.Types.IType;
import Model.Types.StringType;

public class StringValue implements IValue {
    String val;

    public StringValue(String v) {
        val = v;
    }

    public String getVal() {
        return val;
    }

    public String toString() {
        return val;
    }

    public IValue deepCopy() {
        return new StringValue(val);
    }

    public IType getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StringValue){
            return ((StringValue) obj).getVal().equals(val);
        }
        return false;
    }

}
