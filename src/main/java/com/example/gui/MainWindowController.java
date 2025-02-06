package com.example.gui;

import Controller.Controller;
import Model.ADTs.LockTable;
import Model.ADTs.MyDictionary;
import Model.ADTs.MyHeap;
import Model.PrgState;
import Model.Stmts.IStmt;
import Model.Values.IValue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;

public class MainWindowController {
    Controller controller;
    MyHeap<Integer, IValue> heap;
    List<IValue> out;
    MyDictionary<String, BufferedReader> fileTable;
    LockTable<Integer, Integer> lockTable;

    public MainWindowController(Controller c){
        controller = c;
    }


    @FXML
    private Label prgStatesLabel;

    @FXML
    private ListView<String> prgStatesListView;

    @FXML
    private ListView<IStmt> exeStackListView;

    @FXML
    private ListView<String> outListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private TableView<Pair<Integer, IValue>> heapTableView;

    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> heapAddressColumn;

    @FXML
    private TableColumn<Pair<Integer, IValue>, IValue> heapValueColumn;

    @FXML
    private TableView<Pair<String, IValue>> symTableTableView;

    @FXML
    private TableColumn<Pair<String, IValue>, String> symTableVariableColumn;

    @FXML
    private TableColumn<Pair<String, IValue>, IValue> symTableValueColumn;

    @FXML
    private TableView<Pair<Integer, Integer>> lockTableView;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> lockVariableColumn;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> lockValueColumn;

    @FXML
    private Button oneStepButton;

    public void refresh(){
        int index = this.prgStatesListView.getSelectionModel().getSelectedIndex();
        this.prgStatesListView.getItems().clear();
        this.heapTableView.getItems().clear();
        this.symTableTableView.getItems().clear();
        this.outListView.getItems().clear();
        this.fileTableListView.getItems().clear();
        this.exeStackListView.getItems().clear();
        this.lockTableView.getItems().clear();

        this.prgStatesLabel.setText("Program States: " + this.controller.getProgStates().size());
        this.controller.getProgStates().forEach(prg ->
            this.prgStatesListView.getItems().add("Program " + prg.getId()));

        if (!this.controller.getProgStates().isEmpty()) {
            heap = this.controller.getProgStates().getFirst().getHeap();
            out = this.controller.getProgStates().getFirst().getOut().toList();
            fileTable = this.controller.getProgStates().getFirst().getFileTable();
        }
        if (heap != null) {
            heap.toMap().forEach((key, value) -> this.heapTableView.getItems().add(new Pair<>(key, value)));
        }

        if (out != null) {
            outListView.setItems(FXCollections.observableList(out.stream()
                    .map(IValue::toString)
                    .collect(Collectors.toList())));
        }

        if (fileTable != null) {
            fileTableListView.setItems(FXCollections.observableList(new ArrayList<>(fileTable.getContent().keySet())));
        }


        PrgState currentProg;

        try {
            currentProg = this.controller.getProgStates().get(0);

            exeStackListView.setItems(FXCollections.observableList(new ArrayList<>(currentProg.getStk().toList())));
            symTableTableView.setItems(FXCollections.observableList(currentProg.getSymTable().getContent().entrySet().stream()
                    .map(e -> new Pair<>(e.getKey(), e.getValue()))
                    .collect(Collectors.toList())));
            lockTableView.setItems(FXCollections.observableList(currentProg.getLockTable().getContent().entrySet().stream()
                    .map(e -> new Pair<>(e.getKey(), e.getValue()))
                    .collect(Collectors.toList())));

            this.prgStatesListView.onMouseClickedProperty().setValue(mouseEvent -> {
                int selectedIndex = this.prgStatesListView.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    PrgState selectedPrg = this.controller.getProgStates().get(selectedIndex);
                    exeStackListView.setItems(FXCollections.observableList(new ArrayList<>(selectedPrg.getStk().toList())));
                    symTableTableView.setItems(FXCollections.observableList(selectedPrg.getSymTable().getContent().entrySet().stream()
                            .map(e -> new Pair<>(e.getKey(), e.getValue()))
                            .collect(Collectors.toList())));
                    lockTableView.setItems(FXCollections.observableList(selectedPrg.getLockTable().getContent().entrySet().stream()
                            .map(e -> new Pair<>(e.getKey(), e.getValue()))
                            .collect(Collectors.toList())));
                }
            });
        } catch (IndexOutOfBoundsException e){
            return;
        } finally {
            this.prgStatesListView.refresh();
            this.heapTableView.refresh();
            this.symTableTableView.refresh();
            this.outListView.refresh();
            this.fileTableListView.refresh();
            this.exeStackListView.refresh();
            this.lockTableView.refresh();
        }

    }

    public void initialize() {
        this.heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        this.heapValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));
        this.symTableVariableColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        this.symTableValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));
        this.lockVariableColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        this.lockValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        this.oneStepButton.setOnAction(actionEvent -> {
            try {
                this.refresh();
                this.controller.executeOneStep();
                this.refresh();
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.show();
            }
            this.refresh();
        });

    }

}
