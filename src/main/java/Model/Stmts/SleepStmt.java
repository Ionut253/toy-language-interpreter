package Model.Stmts;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyStack;
import Model.PrgState;
import Model.Types.IType;
import MyExc.MyException;

public class SleepStmt implements IStmt {
    private final int number;

    public SleepStmt(int number) {
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState state) {
        if (number > 0) {
            MyStack<IStmt> stack = state.getStk();
            stack.push(new SleepStmt(number - 1));
            state.setExecutionStack(stack);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Sleep(" + number + ")" ;
    }

    @Override
    public IStmt deepCopy() {
        return new SleepStmt(number);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }
}
