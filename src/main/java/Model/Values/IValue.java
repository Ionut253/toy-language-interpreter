package Model.Values;

import Model.Types.IType;

public interface IValue {
    IType getType();
    boolean equals(Object obj);
}
