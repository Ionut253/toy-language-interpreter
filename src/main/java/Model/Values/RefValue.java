package Model.Values;

import Model.Types.IType;
import Model.Types.RefType;

public class RefValue implements IValue{
    int address;
    IType locationType;

    public RefValue(int a, IType l){
        address = a;
        locationType = l;
    }

    public int getAddr() {return address;}
    public IType getType() {return new RefType(locationType);}
    public IType getLocationType() {return locationType;}

    public String toString(){
        return "(" + Integer.toString(address) + ", " + locationType.toString() + ")";
    }

    public int getAddress() {
        return address;
    }
}
