package com.event_bar_easv.gui.controllers;


import com.google.inject.Inject;
import com.event_bar_easv.bll.helpers.ViewType;
import com.event_bar_easv.gui.controllers.abstractController.RootController;
import com.event_bar_easv.gui.controllers.controllerFactory.IControllerFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;


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


//    /**
//     * void method that invokes scroll pane to clean content first
//     * and then set it back to all categories
//     */
//    private void refreshScrollPane() {
//        if (scroll_pane != null) {
//            scroll_pane.setContent(null);
//            setCategoriesScrollPane(categoryModel.getAllCategories());
//        }
//    }

//    /**
//     * method to set all the categories from List <Category> that uses Linked hashmap to store two buttons that will be then
//     * one by one added into the empty scroll pane
//     * if no categories are provided stack pane is filled with Label that notifies the user about not having any category listed yet
//     * LinkedHashMap is added here due to the reason that whenever new Button is added it is automatically put as the last and keps the order
//     *
//     * @param allCategories list of all categories
//     */
//    private void setCategoriesScrollPane(List<Category> allCategories) {
//        int deleteButtonWidth = 40;
//        int categoryButtonWidth = 183;
//        // This code is creating a new Map object that is populated with the key-value pairs of a given Map,
//        //  and then returning it.
//        LinkedHashMap<Button, Button> scrollPaneContentMap = allCategories
//                .stream()
//                .map(category -> {
//                    Button categoryBtn = new Button(category.getName());
//                    Button deleteBtn = new Button("❌");
//                    categoryBtn.getStyleClass().add("custom-button");
//                    deleteBtn.getStyleClass().add("custom-button");
//                    // Setting on the action for switching views
//                    categoryBtn.setOnAction(event -> {
//                        MovieController parent = (MovieController) tryToLoadView();
//                        parent.setIsCategoryView(category.getId());
//                        switchToView(parent.getView()); // switches into chosen view
//                    });
//                    categoryBtn.setPrefWidth(categoryButtonWidth);
//
//                    deleteBtn.setOnAction(event -> {
//                        int result = tryToDeleteCategory(category.getId());
//                        if (result > 0) {
//                            refreshScrollPane();
//                        } else {
//                            AlertHelper.showDefaultAlert("Could not delete category with id: " + category.getId(), Alert.AlertType.ERROR);
//                        }
//                    });
//                    deleteBtn.setPrefWidth(deleteButtonWidth);
//                    return new AbstractMap.SimpleEntry<>(categoryBtn, deleteBtn);
//                })
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
//                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
//        // (oldValue, newValue) -> oldValue - a "merge function" that is used to resolve
//        // conflicts when two keys are mapped to the same value. In this case, if there is a conflict,
//        // the old value is kept and the new value is discarded.
//
//        // LinkedHashmap::new  is a function that creates a new, empty Map object. In this case, a LinkedHashMap is created.
//        // collect method is called in order to finish up what we opened at the begging stream
//
//        if (scrollPaneContentMap.isEmpty()) {
//            scroll_pane.setContent(new Label("Empty")); // sets content if no buttons are filled
//        } else {
//            VBox vBox = new VBox(); // creates new VBox and HBox in order to go <CategoryTitle> and <DeleteButton>
//            for (Map.Entry<Button, Button> entry : scrollPaneContentMap.entrySet()) {
//                HBox hBox = new HBox(); // creates new HBox
//                hBox.getChildren().add(entry.getKey()); // add  button Key
//                hBox.getChildren().add(entry.getValue()); // add button value
//                vBox.getChildren().add(hBox); // sets the vbox to hold HBox
//            }
//            scroll_pane.setContent(vBox); // finally sets the content into the scroll pane
//        }
//    }


    /**
     * method that tries to load the view
     * @return parent that will be loaded
     */
    private RootController tryToLoadView() {
        try {
            return loadNodesView(ViewType.MOVIES);
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



    /**
     * method for loading fxml file from root controller
     *
     * @param viewType enum specifying needed view
     * @return parent loaded from factory
     */

    private RootController loadNodesView(ViewType viewType) throws IOException {
        return controllerFactory.loadFxmlFile(viewType);
    }

    /**
     * method to clear current app_content and replaces it with new parent
     *
     * @param parent that will be inserted inside the stack pane
     */
    private void switchToView(Parent parent) {
        app_content.getChildren().clear();
        app_content.getChildren().add(parent);
    }


}
