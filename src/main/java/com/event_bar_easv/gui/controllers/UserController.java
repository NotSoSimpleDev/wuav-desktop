package com.event_bar_easv.gui.controllers;

import com.event_bar_easv.be.AppRole;
import com.event_bar_easv.be.AppUser;
import com.event_bar_easv.gui.controllers.abstractController.RootController;
import com.event_bar_easv.gui.models.user.IUserModel;
import com.google.inject.Inject;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UserController extends RootController implements Initializable {


    @FXML
    private TableColumn<AppUser,Integer> sysId;
    @FXML
    private TableColumn<AppUser,String>  sysName;
    @FXML
    private TableColumn<AppUser,String>  sysEmail;
    @FXML
    private TableColumn<AppUser,String>  sysHash;
    @FXML
    private TableColumn<AppUser,String>  sysRoles;
    @FXML
    private TableColumn<AppUser,Boolean>  sysActive;
    @FXML
    private TableView<AppUser> sysTable;

    private final IUserModel userModel;

    @Inject
    public UserController(IUserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         fillTableWithData();
        System.out.println(userModel.getAllUsers());
    }





    private void fillTableWithData() {

        // ->
        sysId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId())); // set movie title
        sysName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        sysEmail.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEmail()));
        sysHash.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPasswordHash()));

        sysRoles.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoles().stream()
                .map(AppRole::getName)
                .collect(Collectors.joining(","))
        ));

        sysActive.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isActivated()));


        trySetTableWithSysUsers();
    }


    private void trySetTableWithSysUsers() {
     sysTable.setItems(userModel.getAllUsers());
    }
}
