package com.event_bar_easv.gui.controllers;

import com.event_bar_easv.be.Event;
import com.event_bar_easv.be.user.AppRole;
import com.event_bar_easv.gui.controllers.abstractController.RootController;
import com.event_bar_easv.gui.models.event.IEventModel;
import com.event_bar_easv.gui.models.user.IUserModel;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DashboardController extends RootController implements Initializable {

    private final IEventModel eventModel;

    private final IUserModel userModel;

    @FXML
    private MenuButton eventsMenuButton;

    @FXML
    private MenuButton customerMenuButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillEvents();
        fillCustomers();
    }

    @Inject
    public DashboardController(IEventModel eventModel, IUserModel userModel) {
        this.eventModel = eventModel;
        this.userModel = userModel;
    }

    private void fillEvents() {
        List<Event> categoryList = eventModel.getAllEvents();
        if (eventsMenuButton.getItems() != null) {
            eventsMenuButton.getItems().clear();
            categoryList.stream()
                    .map(event -> {
                        CheckMenuItem menuItem = new CheckMenuItem();
                        menuItem.setText(event.getEventName());

                        return menuItem;
                    })
                    .forEach(menuItem -> eventsMenuButton.getItems().add(menuItem));
        }
    }

    private void fillCustomers() {
        var allUsers = userModel.getAllUsers();

        var sortedUsers = allUsers.stream()
                .filter(user -> user.getRoles().stream()
                        .map(AppRole::getName)
                        .toList()
                        .contains("user"))
                .toList();

        if (customerMenuButton.getItems() != null) {
            customerMenuButton.getItems().clear();
            sortedUsers.stream()
                    .map(customer -> {
                        CheckMenuItem menuItem = new CheckMenuItem();
                        menuItem.setText(customer.getEmail());

                        return menuItem;
                    })
                    .forEach(menuItem -> customerMenuButton.getItems().add(menuItem));
        }
    }
}
