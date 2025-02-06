package Model.Types;

import Model.Values.IValue;
import Model.Values.StringValue;

public class StringType implements IType{

    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }

    public String toString() {
        return "string";
    }

    public IValue defaultValue() {
        return new StringValue("");
    }

    public IType deepCopy() {
        return new StringType();
    }
}
