package com.event_bar_easv.gui.controllers;


import com.google.inject.Inject;
import com.event_bar_easv.bll.helpers.ViewType;
import com.event_bar_easv.gui.controllers.abstractController.RootController;
import com.event_bar_easv.gui.controllers.controllerFactory.IControllerFactory;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Base controller navigation with switchable content
 * Serves as a base for the app
 * injects the root controller
 */
public class BaseController extends RootController implements Initializable {

    @FXML
    private ScrollPane scroll_pane;
    @FXML
    private StackPane app_content;

    private final IControllerFactory controllerFactory;

    @Inject
    public BaseController(IControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }



    //region PAGE SWITCHING EVENTS
    @FXML
    private void handleEventPageSwitch()  {
        runInParallel(ViewType.EVENTS);
    }

    @FXML
    private void handleUsersPageSwitch()  {
        runInParallel(ViewType.USERS);
    }

    @FXML
    private void handleSpecialTicketsPageSwitch()  {
        runInParallel(ViewType.SPECIAL_TICKETS);
    }
    //endregion


    private void runInParallel(ViewType type){
        final RootController[] parent = {null};
        Task<Void> loadDataTask = new Task<>() {
            @Override
            protected Void call() throws IOException {
                parent[0] = loadNodesView(type);
                return null;
            }
        };
        loadDataTask.setOnSucceeded(event -> {
            switchToView(parent[0].getView());
        });
        new Thread(loadDataTask).start();
    }




    //region VIEW INTERNAL HANDEL

    private RootController tryToLoadView() {
        try {
            return loadNodesView(ViewType.EVENTS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * private method for showing new stages whenever its need
     *
     * @param parent root that will be set
     * @param title  title for new stage
     */
    private void show(Parent parent, String title) {
        Stage stage = new Stage();
        Scene scene = new Scene(parent);

        stage.initOwner(getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(title);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


    private RootController loadNodesView(ViewType viewType) throws IOException {
        return controllerFactory.loadFxmlFile(viewType);
    }

    private void switchToView(Parent parent) {
        app_content.getChildren().clear();
        app_content.getChildren().add(parent);
    }
    //endregion


}
