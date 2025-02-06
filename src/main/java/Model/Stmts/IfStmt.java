package Model.Stmts;

import Model.ADTs.MyIDictionary;
import Model.Expr.IExp;
import Model.PrgState;
import Model.Types.BoolIType;
import Model.Types.IType;
import Model.Values.BoolIValue;
import MyExc.MyException;

public class IfStmt implements IStmt {
    IExp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(IExp e, IStmt t, IStmt el) {
        exp = e;
        thenS = t;
        elseS = el;
    }

    public String toString() {
        return "if(" + exp.toString() + ") then(" + thenS.toString() + ") else(" + elseS.toString() + ")";
    }

    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy() );
    }

    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = exp.typecheck(typeEnv);
        if ( typeExp.equals(new BoolIType())){
            thenS.typecheck(typeEnv.deepCopy());
            elseS.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException(" The condition of IF has not the type bool!");
    }

    public PrgState execute(PrgState state) throws MyException {
        if (!exp.eval(state.getSymTable(), state.getHeap()).getType().equals(new BoolIType()))
            {
                throw new MyException("Conditional expression is not of type bool");
            }
        else{
                if (((BoolIValue) exp.eval(state.getSymTable(), state.getHeap())).getVal()) {
                    state.getStk().push(thenS);
                } else {
                    state.getStk().push(elseS);
                }
            }
            return null;
        }
    }
