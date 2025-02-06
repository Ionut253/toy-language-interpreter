package Controller;

import Model.ADTs.*;
import Model.PrgState;
import Model.Stmts.IStmt;
import Model.Values.IValue;
import MyExc.MyException;
import Repository.IRepo;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {

    IRepo repo;
    ExecutorService executor;
    boolean displayFlag = true;

    public Controller(IRepo r){
        repo = r;
    }

    public void addProgram(PrgState prg){
        repo.addPrg(prg);
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prgState -> repo.logPrgStateExec(prgState));
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) p::oneStep)
                .toList();
        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
        prgList.addAll(newPrgList);
        prgList.forEach(prgState -> repo.logPrgStateExec(prgState));
        repo.setPrgList(prgList);
    }


    public void executeOneStep() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        if (!prgList.isEmpty()){
            prgList.forEach(prg -> prg.getHeap().safeGC(prg.getHeap().getAddrFromSymTable(prg.getSymTable().getContent().values()), prg.getHeap().getContent()));
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }


    public void allSteps() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while (!prgList.isEmpty()){
            prgList.forEach(prg -> prg.getHeap().safeGC(prg.getHeap().getAddrFromSymTable(prg.getSymTable().getContent().values()), prg.getHeap().getContent()));
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    public void displayState(PrgState state){
        try{
            MyStack<IStmt> stk = state.getStk();
            MyDictionary<String, IValue> tbl = (MyDictionary<String, IValue>) state.getSymTable();
            MyList<IValue> out = state.getOut();
            System.out.println("Program id : " + state.getId());
            System.out.println("Stack execution : ");
            System.out.println(stk);
            System.out.println("Symb table : ");
            System.out.println(tbl);
            System.out.println("Out : ");
            System.out.println(out);
            System.out.println("File Table : ");
            System.out.println(state.getFileTable());
            System.out.println("Heap : ");
            System.out.println(state.getHeap());
            System.out.println("_____________________________");
        } catch (MyException e){
            System.out.println(e.getMessage());
        }
    }

    public List<PrgState> getProgStates(){
        return repo.getPrgList();
    }


    public List<IStmt> hardcoded() {
        return repo.hardcodedStmts();
    }

    public void setPrg(IStmt iStmt) {
        try {
            iStmt.typecheck(new MyDictionary<>());
            repo.clear();
            repo.addPrg(new PrgState(
                    new MyStack<>(),
                    new MyDictionary<>(),
                    new MyList<>(),
                    iStmt,
                    new MyDictionary<>(),
                    new MyHeap<>(),
                    new LockTable<>()
            ));
            repo.logPrgStateExec(repo.getPrgList().getFirst());
        } catch (MyException e) {
            throw new RuntimeException(e);
        }

    }
}
