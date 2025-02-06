package com.example.gui;

import Controller.Controller;
import Repository.Repo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PrgSelectionWindow extends Application {
    static Controller controller;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainWindow = new FXMLLoader();

        mainWindow.setLocation(getClass().getResource("MWindow.fxml"));
        mainWindow.setControllerFactory(t -> new MainWindowController(controller));

        Parent root = mainWindow.load();
        MainWindowController mainWindowController = mainWindow.getController();

        stage.setTitle("INTERPRETER");
        stage.setScene(new Scene(root));
        stage.show();

        Stage newStage = new Stage();
        FXMLLoader prgSelectionWindow = new FXMLLoader();

        prgSelectionWindow.setLocation(getClass().getResource("PrgSelection.fxml"));
        prgSelectionWindow.setControllerFactory(t -> new PrgSelectionWindowController(controller, mainWindowController));

        Parent programRoot = prgSelectionWindow.load();
        PrgSelectionWindowController prgSelectionWindowController = prgSelectionWindow.getController();

        newStage.setTitle("Select Program");
        newStage.setScene(new Scene(programRoot));
        newStage.show();

    }

    public static void setController(Controller c){
        PrgSelectionWindow.controller = c;
    }

    public void run(String[] args){
        launch(args);
    }

    public static void main(String[] args) {
        PrgSelectionWindow.setController(new Controller(new Repo("log.txt")));
        PrgSelectionWindow view = new PrgSelectionWindow();
        view.run(args);
    }
}