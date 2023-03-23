package com.event_bar_easv.gui.controllers;

import com.event_bar_easv.be.Event;
import com.event_bar_easv.be.Ticket;
import com.event_bar_easv.be.user.AppRole;
import com.event_bar_easv.be.user.AppUser;
import com.event_bar_easv.bll.utilities.AlertHelper;
import com.event_bar_easv.bll.utilities.pdf.IPdfGenerator;
import com.event_bar_easv.gui.controllers.abstractController.RootController;
import com.event_bar_easv.gui.models.event.IEventModel;
import com.event_bar_easv.gui.models.user.IUserModel;
import com.google.inject.Inject;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DashboardController extends RootController implements Initializable {

    private final IEventModel eventModel;

    private final IUserModel userModel;

    private final IPdfGenerator pdfGenerator;

    @FXML
    private Button saveTicket;

    @FXML
    private Button sendTicket;
    @FXML
    private Button viewTicket;

    @FXML
    private ProgressIndicator progressLoad;

    @FXML
    private MenuButton ticketType;

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
    public DashboardController(IEventModel eventModel, IUserModel userModel,IPdfGenerator pdfGenerator) {
        this.eventModel = eventModel;
        this.userModel = userModel;
        this.pdfGenerator = pdfGenerator;
    }

    private void fillEvents() {
        List<Event> categoryList = eventModel.getAllEvents();
        if (eventsMenuButton.getItems() != null) {
            eventsMenuButton.getItems().clear();
            categoryList.stream()
                    .map(event -> {
                        CheckMenuItem menuItem = new CheckMenuItem();
                        menuItem.setText(event.getEventName());

                        // Set an EventHandler for each menuItem
                        menuItem.setOnAction(eventHandler -> {
                            eventsMenuButton.setText(menuItem.getText());
                            fillTicketTypesForEvent(event.getEventId());
                        });

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
                        // Set an EventHandler for each menuItem
                        menuItem.setOnAction(eventHandler -> {
                            customerMenuButton.setText(menuItem.getText());
                        });

                        return menuItem;
                    })
                    .forEach(menuItem -> customerMenuButton.getItems().add(menuItem));
        }
    }



    private void fillTicketTypesForEvent(int id) {
        // needs to fill here with all ticket types set by admin
        Event categoryList = eventModel.getEventById(id);
        if (ticketType.getItems() != null) {
            ticketType.getItems().clear();
            categoryList.getTicketTypes().stream()
                    .map(event -> {
                        CheckMenuItem menuItem = new CheckMenuItem();
                        menuItem.setText(event.getType());

                        menuItem.setOnAction(eventHandler -> {
                            ticketType.setText(menuItem.getText());
                        });

                        return menuItem;
                    })
                    .forEach(menuItem -> ticketType.getItems().add(menuItem));
        }
    }

    @FXML
    private void exportTicket(ActionEvent actionEvent) {

        if (validateInputs()) {
            System.out.println("Exporting ticket");

            progressLoad.setVisible(true);

            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    // Simulate a delay of 3 seconds
                    Thread.sleep(3000);
                    pdfGenerator.generatePdf("test","test");
                    return null;
                }
            };

            task.setOnSucceeded(event -> {
                viewTicket.setDisable(false);
                saveTicket.setDisable(false);
                sendTicket.setDisable(false);
                progressLoad.setVisible(false);

            });

            task.progressProperty().addListener((observable, oldValue, newValue) -> {
                progressLoad.setProgress(newValue.doubleValue());
            });

            progressLoad.progressProperty().bind(task.progressProperty());
            new Thread(task).start();
        }
    }

    private boolean innerValidateInputs() {
       //  ticketType,eventsMenuButton,customerMenuButton

        Optional<CheckMenuItem> selectedType = ticketType.getItems().stream()
                .filter(item -> item instanceof CheckMenuItem)
                .map(CheckMenuItem.class::cast)
                .filter(CheckMenuItem::isSelected)
                .findFirst();

        Optional<CheckMenuItem> selectedItem = eventsMenuButton.getItems().stream()
                .filter(item -> item instanceof CheckMenuItem)
                .map(CheckMenuItem.class::cast)
                .filter(CheckMenuItem::isSelected)
                .findFirst();

        Optional<CheckMenuItem> selectedCustomer = customerMenuButton.getItems().stream()
                .filter(item -> item instanceof CheckMenuItem)
                .map(CheckMenuItem.class::cast)
                .filter(CheckMenuItem::isSelected)
                .findFirst();

        if(selectedType.isEmpty() || selectedItem.isEmpty() || selectedCustomer.isEmpty()){
            return false;
        }

        return true;
    }
    private boolean validateInputs() {
        boolean isValidated = false;
        if (!innerValidateInputs()) {
            AlertHelper.showDefaultAlert("Please fill all the field! You get the drill", Alert.AlertType.ERROR);
        } else {
            isValidated = true;
        }
        return isValidated;
    }

    @FXML
    private void sendTicketViaEmail(ActionEvent actionEvent) {

    }

    @FXML
    private void openViewTicket(ActionEvent actionEvent) {
        try {
            // Get the URL to the PDF file
            URL pdfFile = getClass().getResource("/myDocumen.pdf");

            // Open the PDF file using the default desktop application
            Desktop.getDesktop().open(new File(pdfFile.toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
