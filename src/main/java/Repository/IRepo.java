package Repository;

import Model.PrgState;
import Model.Stmts.IStmt;
import MyExc.MyException;

import java.util.ArrayList;
import java.util.List;

public interface IRepo {
    void addPrg(PrgState p);
    PrgState getPrg(int index);
    PrgState getCrtPrg();
    ArrayList<PrgState> getPrgList();
    void setPrgList(List<PrgState> list);
    void removePrg(PrgState p);
    void logPrgStateExec(PrgState p) throws MyException;

    List<IStmt> hardcodedStmts();
    void clear();
}
