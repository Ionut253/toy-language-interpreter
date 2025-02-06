package Repository;

import Model.ADTs.MyDictionary;
import Model.Expr.*;
import Model.PrgState;
import Model.Stmts.*;
import Model.Stmts.FileStmts.closeRFile;
import Model.Stmts.FileStmts.openRFile;
import Model.Stmts.FileStmts.readFile;
import Model.Types.BoolIType;
import Model.Types.IntIType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolIValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import MyExc.MyException;

import javax.crypto.CipherInputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo{

    ArrayList<PrgState> repository;
    String logFilePath;
    int currPrg = 0;

    public Repo(String logFilePath){
        repository = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    public void addPrg(PrgState p){
        repository.add(p);
        currPrg++;
    }

    public PrgState getPrg(int i) {
        return repository.get(i);
    }

    public PrgState getCrtPrg(){
        return repository.getFirst();
    }

    public ArrayList<PrgState> getPrgList(){
        return repository;
    }

    public void setPrgList(List<PrgState> list){
        repository = (ArrayList<PrgState>) list;
    }

    public void removePrg(PrgState p){
        repository.remove(p);
        currPrg--;
    }

    public void logPrgStateExec(PrgState prg) throws MyException {
        if ( logFilePath != null ){
            try {
                PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
                logFile.println("Program ID: " + prg.getId());
                logFile.println("ExeStack: ");
                logFile.println(prg.getStk().toString());
                logFile.println("SymTable: ");
                logFile.println(prg.getSymTable().toString());
                logFile.println("Out: " + prg.getOut().toString());
                logFile.println("FileTable: ");
                for (Object k : prg.getFileTable().keySet()) {
                    logFile.println(k.toString());
                }
                logFile.println("Heap: ");
                logFile.println(prg.getHeap().toString());
                logFile.println("-------------------------------------------");
                logFile.println("LockTable: ");
                logFile.println(prg.getLockTable().toString());
                logFile.println("-------------------------------------------");
                logFile.close();
            } catch (IOException e) {
                throw new MyException("Error writing to log file" + e.getMessage());
            }
    }
    }

    @Override
    public List<IStmt> hardcodedStmts() {
        List<IStmt> stmts = new ArrayList<>();


        IStmt ex1 = new CompStmt(
                new VarDeclStmt("v", new IntIType()),
                new CompStmt(
                        new AssignStmt("v", new ValueIExp(new IntValue(2))),
                        new PrintStmt(new VarIExp("v"))
                )
        );
            stmts.add(ex1);

        IStmt ex2 = new CompStmt(
                new VarDeclStmt("a", new IntIType()),
                new CompStmt(new VarDeclStmt("b", new IntIType()),
                        new CompStmt(new AssignStmt("a", new ArithIExp(new ValueIExp(new IntValue(2)), new
                                ArithIExp(new ValueIExp(new IntValue(3)), new ValueIExp(new IntValue(5)), 3), 1)),
                                new CompStmt(new AssignStmt("b", new ArithIExp(new VarIExp("a"), new
                                        ValueIExp(new IntValue(1)), 1)), new PrintStmt(new VarIExp("b"))))));

            stmts.add(ex2);

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolIType()),
                new CompStmt(new VarDeclStmt("v", new IntIType()),
                        new CompStmt(new AssignStmt("a", new ValueIExp(new BoolIValue(true))),
                                new CompStmt(new IfStmt(new VarIExp("a"), new AssignStmt("v", new ValueIExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueIExp(new IntValue(3)))), new PrintStmt(new
                                        VarIExp("v"))))));

            stmts.add(ex3);
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf",
                        new ValueIExp(new StringValue("test.in"))),
                        new CompStmt(new openRFile(new VarIExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntIType()),
                                        new CompStmt(new readFile(new VarIExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarIExp("varc")),
                                                        new CompStmt(new readFile(new VarIExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarIExp("varc")),
                                                                        new closeRFile(new VarIExp("varf"))))))))));
            stmts.add(ex4);

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new IntIType()),
                new CompStmt(new AssignStmt("v", new ValueIExp(new IntValue(3))),
                        new CompStmt(new VarDeclStmt("x", new IntIType()),
                                new CompStmt(new AssignStmt("x", new ValueIExp(new IntValue(2))),
                                        new CompStmt(new VarDeclStmt("y", new IntIType()),
                                                new CompStmt(new AssignStmt("y", new RelIExp(new VarIExp("v"), new VarIExp("x"), ">")),
                                                        new PrintStmt(new VarIExp("y"))))))));

        stmts.add(ex5);
        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntIType())),
                new CompStmt(new NewStmt("v", new ValueIExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new HeapReading(new VarIExp("v"))),
                                new CompStmt(new HeapWriting("v", new ValueIExp(new IntValue(30))),
                                        new PrintStmt(new ArithIExp(new HeapReading(new VarIExp("v")), new ValueIExp(new IntValue(5)), 1))))));
            stmts.add(ex6);

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntIType())),
                new CompStmt(new NewStmt("v", new ValueIExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntIType()))),
                                new CompStmt(new NewStmt("a", new VarIExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueIExp(new IntValue(30))),
                                                new PrintStmt(new HeapReading(new HeapReading(new VarIExp("a")))))))));
            stmts.add(ex7);

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new IntIType()),
                new CompStmt(new AssignStmt("v", new ValueIExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelIExp(new VarIExp("v"), new ValueIExp(new IntValue(80)), ">"),
                                new CompStmt(new PrintStmt(new VarIExp("v")),
                                        new AssignStmt("v", new ArithIExp(new VarIExp("v"),
                                                new ValueIExp(new IntValue(1)), 2)))), new PrintStmt(new VarIExp("v")))));
            stmts.add(ex8);

        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntIType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntIType())),
                        new CompStmt(new AssignStmt("v", new ValueIExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueIExp(new IntValue(22))),
                                        new CompStmt(new forkStmt(new CompStmt(new HeapWriting("a",
                                                new ValueIExp(new IntValue(30))), new CompStmt(new AssignStmt("v",
                                                new ValueIExp(new IntValue(32))), new CompStmt(new PrintStmt(new VarIExp("v")),
                                                new PrintStmt(new HeapReading(new VarIExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarIExp("v")),
                                                        new PrintStmt(new HeapReading(new VarIExp("a")))))))));
            stmts.add(ex9);

        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntIType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntIType())),
                new CompStmt(new AssignStmt("v", new ValueIExp(new IntValue(10))),
                        new CompStmt(new NewStmt("a", new ValueIExp(new IntValue(22))),
                                new CompStmt(new forkStmt(new CompStmt(new HeapWriting("a",
                                        new ValueIExp(new IntValue(30))), new CompStmt(new AssignStmt("v",
                                        new ValueIExp(new IntValue(32))), new CompStmt(new PrintStmt(new VarIExp("v")),
                                        new PrintStmt(new HeapReading(new VarIExp("a"))))))),
                                        new CompStmt(new forkStmt(new CompStmt(new HeapWriting("a",
                                                new ValueIExp(new IntValue(30))), new CompStmt(new AssignStmt("v",
                                                new ValueIExp(new IntValue(32))), new CompStmt(new PrintStmt(new VarIExp("v")),
                                                new PrintStmt(new HeapReading(new VarIExp("a"))))))),
                                                        new PrintStmt(new HeapReading(new VarIExp("a")))))))));

        stmts.add(ex10);

        IStmt ex11 = new CompStmt(new VarDeclStmt("a", new IntIType()),
                new CompStmt( new VarDeclStmt("b", new IntIType()),
                new CompStmt( new AssignStmt("a", new ValueIExp(new IntValue(2))),
                        new CompStmt( new AssignStmt("b", new ValueIExp(new IntValue(5))),
                                new CompStmt(new IfStmt(new RelIExp(new VarIExp("a"), new VarIExp("b"), "<"),
                                        new PrintStmt(new ValueIExp(new IntValue(100))),
                                                new PrintStmt(new ValueIExp(new IntValue(200)))),
                                        new CompStmt(new SleepStmt(5), new PrintStmt(new ArithIExp(new VarIExp("a")
                                                , new VarIExp("b"), 1))))))));
        stmts.add(ex11);

        IStmt ex12 = new CompStmt(new VarDeclStmt("a", new IntIType()),
                new CompStmt( new VarDeclStmt("b", new IntIType()),
                        new CompStmt(new VarDeclStmt("c", new IntIType()),
                                new CompStmt( new AssignStmt("a", new ValueIExp(new IntValue(1))),
                                        new CompStmt( new AssignStmt("b", new ValueIExp(new IntValue(2))),
                                                new CompStmt( new AssignStmt("c", new ValueIExp(new IntValue(5))),
                                                new CompStmt(new SwitchStmt(new ArithIExp(new VarIExp("a"),
                                                        new ValueIExp(new IntValue(10)), 3), new ArithIExp(new VarIExp("b"), new VarIExp("c"), 3), new CompStmt(new PrintStmt(new VarIExp("a")),
                                                                new PrintStmt(new VarIExp("b"))),
                                                        new ValueIExp(new IntValue(10)), new CompStmt(new PrintStmt(new ValueIExp(new IntValue(100))),
                                                                new PrintStmt(new ValueIExp(new IntValue(200)))),
                                                        new PrintStmt(new ValueIExp(new IntValue(300)))),
                                                        new PrintStmt(new ValueIExp(new IntValue(300))))))))));

        stmts.add(ex12);

        IStmt ex13 =new CompStmt(new VarDeclStmt("v", new IntIType()) ,new CompStmt(new VarDeclStmt("a", new RefType(new IntIType())),
                new CompStmt(new NewStmt("a", new ValueIExp(new IntValue(20))),
                        new CompStmt(new ForStmt(new ValueIExp(new IntValue(0)), new ValueIExp(new IntValue(3)),
                                new ArithIExp(new VarIExp("v"), new ValueIExp(new IntValue(1)), 1),
                                new forkStmt(
                                        new CompStmt(new PrintStmt(new VarIExp("v")),
                                                new AssignStmt("v", new ArithIExp(new VarIExp("v"),
                                                        new HeapReading(new VarIExp("a")), 3))))),
                                                new PrintStmt(new HeapReading(new VarIExp("a")))))));

        stmts.add(ex13);

        IStmt ex14 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntIType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntIType())),
                new CompStmt(new VarDeclStmt("x", new IntIType()),
                new CompStmt(new VarDeclStmt("q", new IntIType()),
                new CompStmt(new NewStmt("v1", new ValueIExp(new IntValue(20))),
                new CompStmt(new NewStmt("v2", new ValueIExp(new IntValue(30))),
                new CompStmt(new newLock("x"),
                new CompStmt(new forkStmt(new CompStmt(new forkStmt(new CompStmt(new lock("x"),
                new CompStmt(new HeapWriting("v1", new ArithIExp(new HeapReading(new VarIExp("v1")), new ValueIExp(new IntValue(1)), 2)),
                        new unlock("x")))),
                new CompStmt(new lock("x"), new CompStmt(new HeapWriting("v1", new ArithIExp(new HeapReading(new VarIExp("v1")), new ValueIExp(new IntValue(10)), 3)),
                        new unlock("x"))))),
                new CompStmt(new newLock("q"), new CompStmt(new forkStmt(new CompStmt(new forkStmt(new CompStmt(new lock("q"),
                        new CompStmt(new HeapWriting("v2", new ArithIExp(new HeapReading(new VarIExp("v2")), new ValueIExp(new IntValue(5)), 1)),
                        new unlock("q")))),
                new CompStmt(new lock("q"),
                        new CompStmt(new HeapWriting("v2", new ArithIExp(new HeapReading(new VarIExp("v2")), new ValueIExp(new IntValue(10)), 3)),
                                new unlock("q"))))),
                new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(),
                        new CompStmt(new lock("x"), new CompStmt(new PrintStmt(new HeapReading(new VarIExp("v1"))),
                                new CompStmt(new unlock("x"), new CompStmt(new lock("q"),
                                        new CompStmt(new PrintStmt(new HeapReading(new VarIExp("v2"))),
                                                new unlock("q"))))))))))))))))))));

        stmts.add(ex14);
        return stmts;

    }


    public void clear(){
        repository.clear();
    }
}
