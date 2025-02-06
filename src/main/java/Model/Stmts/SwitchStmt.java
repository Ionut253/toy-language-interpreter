package Model.Stmts;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyStack;
import Model.Expr.IExp;
import Model.Expr.LogicIExp;
import Model.Expr.RelIExp;
import Model.PrgState;
import Model.Types.IType;
import MyExc.MyException;

public class SwitchStmt implements  IStmt{
    IExp exp;
    IExp firstExp;
    IExp secondExp;
    IStmt firstStmt;
    IStmt secondStmt;
    IStmt defaultStmt;


    public SwitchStmt(IExp e, IExp fE, IStmt fS,IExp sE, IStmt sS, IStmt dS){
        exp = e;
        firstExp = fE;
        secondExp = sE;
        firstStmt = fS;
        secondStmt = sS;
        defaultStmt = dS;

    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyStack<IStmt> stk = state.getStk();
        stk.push(new IfStmt(new RelIExp(exp, firstExp, "=="), firstStmt, new IfStmt(new RelIExp(exp, firstExp, "=="), secondStmt, defaultStmt)));
        state.setExecutionStack(stk);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(exp, firstExp, firstStmt, secondExp, secondStmt, defaultStmt);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = exp.typecheck(typeEnv);
        IType typeFirstExp = firstExp.typecheck(typeEnv);
        IType typeSecondExp = secondExp.typecheck(typeEnv);
        if (typeExp.equals(typeFirstExp) && typeExp.equals(typeSecondExp)){
            firstStmt.typecheck(typeEnv.deepCopy());
            secondStmt.typecheck(typeEnv.deepCopy());
            defaultStmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException(" The condition of SWITCH has not the same type as the cases!");
    }

    public String toString(){
        return "(switch(" + exp.toString() + ")\n case(" + firstExp.toString() + ") :(" + firstStmt.toString() + ")\n case(" + secondExp.toString() + ") :(" + secondStmt.toString() + ")\n default(" + defaultStmt.toString() + "))";
    }
}
