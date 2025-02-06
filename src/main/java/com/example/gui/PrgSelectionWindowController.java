package com.example.gui;

import Controller.Controller;
import Model.Stmts.IStmt;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;



public class PrgSelectionWindowController {
    Controller controller;
    MainWindowController mainWindowController;

    public PrgSelectionWindowController(Controller c, MainWindowController mwc){
        controller = c;
        mainWindowController = mwc;
    }

    @FXML
    private ListView<IStmt> prgStatesListView;

    @FXML
    private Button selectPrg;

    @FXML
    public void initialize() {
        prgStatesListView.setItems(FXCollections.observableList(controller.hardcoded()));
        selectPrg.setOnAction(actionEvent -> {
            try{
            int index = prgStatesListView.getSelectionModel().getSelectedIndex();
            this.controller.setPrg(controller.hardcoded().get(index));
            this.mainWindowController.refresh();
            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.show();
            }
        });
        this.mainWindowController.refresh();
    }
}